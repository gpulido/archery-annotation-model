/*
 * Copyright (C) 2017 Gabriel Pulido
 *
 * This file is part of Archery Annotation.
 *
 *  Archery Annotation is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 2
 *  as published by the Free Software Foundation.
 *
 *  Archery Annotation is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 */

package es.gpulido.annotationmodel.annotation;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.annimon.stream.Stream;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.util.Comparator;
import java.util.Date;

import es.gpulido.annotationmodel.Interfaces.ICanBeDeleted;
import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.archer.Archer;
import es.gpulido.annotationmodel.archer.Club;
import es.gpulido.annotationmodel.competition.Competition;
import es.gpulido.annotationmodel.helper.RealmHelper;
import es.gpulido.annotationmodel.target.Target;
import es.gpulido.annotationmodel.helper.AnnotationHelper;
import es.gpulido.annotationmodel.helper.ModelTypes;
import es.gpulido.annotationmodel.helper.Utils;
import es.gpulido.annotationmodel.training.BowConfiguration;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by gpt on 13/11/14.
 */
@SuppressWarnings("unused, SpellCheckingInspection")
public class Contender extends RealmObject implements ICanBeDeleted, IHasPrimarykey, Comparable<Contender> {


    public static final String ACTIVE = "Active";
    public static final String DNS = "Did not Start";
    public static final String DNF = "Did not Finish";
    public static final String DSQ = "Disqualified";

    @PrimaryKey
    private String UUID;
    private boolean deleted;
    private Tournament tournament;
    @LinkingObjects("contender")
    public final RealmResults<Annotation> annotations = null;
    @LinkingObjects("contender")
    public final RealmResults<ContenderTotalValue> totals = null;

    private Competition competition;
    private Date initCompetitionDate;
    private String name;
    private Letter letter;
    private int order;

    //True if the archer needs to fill the whole parapet
    private boolean needsFullShotSlot;
    //True if another archer can't shot on the same place on another turn (archers with discapacities for example)
    private boolean invalidateSecondTurn;


    private int classificationOrder;
    //ACTIVE DNS DNF DSQ
    private String state;
    private Archer archer;
    private Club club;
    private boolean isContenderCompleted;


    private String signature;
    private boolean isSigned;
    private boolean isMailSent;

    //The archer that is making the contender annotation
    private Archer annotator;


    //Training data. As Realm doesn't allow inheritance this has to be here
    private BowConfiguration bowConfiguration;


    public void asignContenderToLetter(Realm realm, Letter letter) {

        Timber.i("AsignContender: %s %s%s", getName(), letter.getParapet().getOrder(), letter.getLetterSymbol());
        if (getLetter() != null) {
            getLetter().setContender(null);
        }
        setLetter(letter);
        setOrder(letter.getParapet().getOrder() * 100 + Utils.getAlphabetPosition(letter.getLetterSymbol().charAt(0)));
        letter.setContender(this);
        GenerateContenderAnnotations(realm);
    }

    //TODO: move to a Realm helper
    public static Realm.Transaction asignContenderToLetter(String contenderUUID, String letterUUID) {
        return realm -> {
            //clear new letter contender asigment
            Letter letter = RealmHelper.getByPrimaryKey(realm, Letter.class, letterUUID);
            letter.clearContender(realm);

            Contender contender = RealmHelper.getByPrimaryKey(realm, Contender.class, contenderUUID);
            //clear old contender asigment if any
            if (contender.getLetter() != null)
                contender.getLetter().clearContender(realm);
            //asign new letter to contender
            contender.asignContenderToLetter(realm, letter);
        };
    }

    public void removeContenderAnnotations(Realm realm)
    {
        RealmResults<ContenderTotalValue> deletedContenderTotalValues = realm.where(ContenderTotalValue.class).equalTo("contender.UUID", getUUID()).findAll();
        RealmResults<AnnotationValue> deletedAnnotationValues = realm.where(AnnotationValue.class).equalTo("annotation.contender.UUID", getUUID()).findAll();
        RealmResults<AnnotationTotalValue> deletedAnnotationTotalValues = realm.where(AnnotationTotalValue.class).equalTo("annotation.contender.UUID", getUUID()).findAll();
        RealmResults<Annotation> deletedAnnotations = realm.where(Annotation.class).equalTo("contender.UUID", getUUID()).findAll();
        deletedContenderTotalValues.deleteAllFromRealm();
        deletedAnnotationTotalValues.deleteAllFromRealm();
        deletedAnnotationValues.deleteAllFromRealm();
        deletedAnnotations.deleteAllFromRealm();

    }
    public void GenerateContenderAnnotations(Realm realm)
    {
        GenerateContenderAnnotations(realm, false);
    }

    public void GenerateContenderAnnotations(Realm realm, boolean forceCreation) {
        if (annotations.size() > 0 && !forceCreation)
            return;
        int roundOrder = 0;
        for (Round round : getTournament().rounds) {
            Target target = getCompetition().getSerie().getSeriesRounds().get(roundOrder).getTarget();
            for (End end : round.entries) {
                AnnotationHelper.CreateAnnotation(realm, end, this, getTournament(), target);
            }
            roundOrder++;

        }
        CreateContenderTotals(realm);
    }

    public void CreateContenderTotals(Realm realm)
    {
        RealmResults<AnnotationTotalValue> totalValues = realm.where(AnnotationTotalValue.class)
                .equalTo("annotation.contender.UUID", getUUID())
                .sort("totalDefinition.order")
                .findAll();
        Stream.of(totalValues)
                .groupBy(AnnotationTotalValue::getTotalDefinition)
                .forEach(kv -> {
                    ContenderTotalValue value = realm.createObject(ContenderTotalValue.class, java.util.UUID.randomUUID().toString());
                    value.setTotalDefinition(kv.getKey());
                    value.setContender(this);
                    value.setDoubleFieldValue(0);
                    value.getAnnotationTotalValues().addAll(kv.getValue());
                });
    }

    public static String getSortFieldByCompetition(@ModelTypes.TournamentTypes String tournamentType) {
        switch (tournamentType) {
            case ModelTypes.T_NONE:
            case ModelTypes.T_COMPETITION:
                return "competition.name";
            case ModelTypes.T_TRAINING:
                return "order";
            case ModelTypes.T_SUMMER_RANKING:
            case ModelTypes.T_WINTER_RANKING:
                return "initCompetitionDate";

        }
        return "";
    }

    public BowConfiguration getBowConfiguration() {
        return bowConfiguration;
    }

    public void setBowConfiguration(BowConfiguration bowConfiguration) {
        this.bowConfiguration = bowConfiguration;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Letter getLetter() {
        return letter;
    }

    public void setLetter(Letter letter) {
        this.letter = letter;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }


    public Archer getArcher() {
        return archer;
    }

    public void setArcher(Archer archer) {
        this.archer = archer;
    }

    public Date getInitCompetitionDate() {
        return initCompetitionDate;
    }

    public void setInitCompetitionDate(Date initCompetitionDate) {
        this.initCompetitionDate = initCompetitionDate;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean getIsContenderCompleted() {
        return isContenderCompleted;
    }

    public void setIsContenderCompleted(boolean isContenderCompleted) {
        if (isContenderCompleted != this.isContenderCompleted)
            this.isContenderCompleted = isContenderCompleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getClassificationOrder() {
        return classificationOrder;
    }

    public void setClassificationOrder(int classificationOrder) {
        if (classificationOrder != this.classificationOrder)
            this.classificationOrder = classificationOrder;
    }

    public void updateIsCompleted() {
        setIsContenderCompleted(annotations.where().equalTo("isCompleted", false).count() == 0);
    }

    @Override
    public void delete(Realm realm) {
        String localUUID = UUID;
        realm.executeTransactionAsync(realm1 -> RealmHelper.getByPrimaryKey(realm1, Contender.class, localUUID).setDeleted(true));
    }

    @Override
    public void undoDelete(Realm realm) {
        String localUUID = UUID;
        realm.executeTransactionAsync(realm1 -> RealmHelper.getByPrimaryKey(realm1, Contender.class, localUUID).setDeleted(false));
    }

    /***
     * Changes the state of the contender deleting all data
     * @param state new state of the contender
     */
    @SuppressLint("NewApi")
    public void changeState(@ContenderStatus String state) {
        if (getState().equals(state)) return;
        final String contenderUUID = getUUID();
        try (Realm realm = RealmHelper.getActiveInstance()) {
            realm.executeTransactionAsync(realm1 -> {
                Contender asyncContender = RealmHelper.getByPrimaryKey(realm1, Contender.class, contenderUUID);
                asyncContender.setState(state);
                if (state.equals(Contender.ACTIVE))
                    Stream.of(asyncContender.annotations).forEach(Annotation::updateIsCompleted);
                else
                    Stream.of(asyncContender.annotations).forEach(a -> a.setIsCompleted(true));
            });
        }
    }

    @Override
    public int compareTo(@NonNull Contender contender) {
        return Integer.valueOf(contender.getOrder()).compareTo(getOrder());
    }

    public String getClubImageResourceName() {
        if (getClub() != null && getClub().getImageResourceName() != null)
            return getClub().getImageResourceName();
        return null;

    }

    public int getContenderMainTotal() {
        if (totals.size() == 0) return 0;
        return (int)totals.where().equalTo("totalDefinition.isMainTotal", true).findFirst().getDoubleFieldValue();
    }

    public int contenderTotalByIndex(int index) {
        if (totals.size() <= index ) return 0;
        return (int)totals.where().sort("totalDefinition.order").findAll().get(index).getDoubleFieldValue();
    }

    public String contenderTotalDescriptionByIndex(int index) {
        if (totals.size() <= index ) return "";
        return totals.where().sort("totalDefinition.order").findAll().get(index).getTotalDefinition().getShortDescription();
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Archer getAnnotator() {
        return annotator;
    }

    public void setAnnotator(Archer annotator) {
        this.annotator = annotator;
    }

    public boolean isSigned() {
        return isSigned;
    }

    public void setSigned(boolean signed) {
        isSigned = signed;
    }

    public void signContender(Realm realm) {
        String contenderUUID = getUUID();
        realm.executeTransactionAsync(realm1 -> RealmHelper.getByPrimaryKey(realm1, Contender.class, contenderUUID).setSigned(true));
    }

    public void signContender(Realm realm, String signature) {
        String contenderUUID = getUUID();
        realm.executeTransaction(realm1 ->
        {
            Contender contender = RealmHelper.getByPrimaryKey(realm1, Contender.class, contenderUUID);
            contender.setSigned(true);
            contender.setSignature(signature);
        });
    }

    public boolean isMailSent() {
        return isMailSent;
    }

    public void setMailSent(boolean mailSent) {
        isMailSent = mailSent;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public boolean isNeedsFullShotSlot() {
        return needsFullShotSlot;
    }

    public void setNeedsFullShotSlot(boolean needsFullShotSlot) {
        this.needsFullShotSlot = needsFullShotSlot;
    }

    public boolean isInvalidateSecondTurn() {
        return invalidateSecondTurn;
    }

    public void setInvalidateSecondTurn(boolean invalidateSecondTurn) {
        this.invalidateSecondTurn = invalidateSecondTurn;
    }


    @Retention(SOURCE)
    @StringDef({
            ACTIVE,
            DNS,
            DNF,
            DSQ
    })
    public @interface ContenderStatus {
    }


    static Contender.TotalsComparatorAscending totalsComparator = new Contender.TotalsComparatorAscending();
    /**
     * A simple Comparator to sort the items ascending.
     */
    public static class CompetitionComparatorAscending implements Comparator<Contender>, Serializable {
        @Override
        public int compare(Contender o1, Contender o2) {
            if (o1.getCompetition().compareTo(o2.competition) != 0)
                return -o1.getCompetition().compareTo(o2.competition);
            return Integer.compare(o1.getClassificationOrder(), o2.getClassificationOrder());
            //return totalsComparator.compare(o1, o2);

        }

    }


    /**
     * A simple Comparator to sort the items ascending.
     */
    public static class TotalsComparatorAscending implements Comparator<Contender>, Serializable {
        @Override
        public int compare(Contender o1, Contender o2) {

            if (!o1.getState().equals(Contender.ACTIVE) && o2.getState().equals(Contender.ACTIVE))
                return 1;
            if (o1.getState().equals(Contender.ACTIVE) && !o2.getState().equals(Contender.ACTIVE))
                return -1;

            RealmResults<ContenderTotalValue> c1Totals = o1.totals.sort("totalDefinition.classificationOrder");
            RealmResults<ContenderTotalValue> c2Totals = o2.totals.sort("totalDefinition.classificationOrder");
            if (c1Totals.size() > c2Totals.size()) return -1;
            if (c1Totals.size() < c2Totals.size()) return 1;
            int comparation = Stream.zip(Stream.of(c1Totals), Stream.of(c2Totals), (value1, value2) ->
            {
                Double o1Value = value1.getDoubleFieldValue()*value1.getTotalDefinition().getClassificationSign();
                Double o2Value = value2.getDoubleFieldValue()*value2.getTotalDefinition().getClassificationSign();
                return -o1Value.compareTo(o2Value);

            }).filter(i -> i!= 0).findFirst().orElse(0);

            return comparation;

        }

    }

    @SuppressLint("NewApi")
    public static String CopyContenderToRealm(Contender contender) {
        try (Realm realm = RealmHelper.getActiveInstance()) {
            realm.beginTransaction();
            contender.setTournament(RealmHelper.getByPrimaryKey(realm,Tournament.class,contender.getTournament().getUUID()));
            Letter letter = RealmHelper.getByPrimaryKey(realm, Letter.class, contender.getLetter().getUUID());
            contender.setCompetition(RealmHelper.getByPrimaryKey(realm,Competition.class,contender.getCompetition().getUUID()));
            contender.setLetter(null);
            Club club = realm.where(Club.class).equalTo("UUID", contender.getClub().getUUID())
                    .or()
                    .equalTo("name", contender.getClub().getName())
                    .findFirst();
            if (club != null) {
                contender.setClub(club);
            }
            Contender realmContender = realm.copyToRealmOrUpdate(contender);
            contender.getTournament().contenders.add(realmContender);
            realmContender.asignContenderToLetter(realm, letter);
            realm.commitTransaction();
            return realmContender.getUUID();
        }
    }


}
