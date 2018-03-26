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

package es.gpulido.annotationmodel.helper;

import android.annotation.SuppressLint;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import es.gpulido.annotationmodel.annotation.Annotation;
import es.gpulido.annotationmodel.annotation.AnnotationTotalValue;
import es.gpulido.annotationmodel.annotation.AnnotationValue;
import es.gpulido.annotationmodel.annotation.Contender;
import es.gpulido.annotationmodel.annotation.Tournament;
import es.gpulido.annotationmodel.archer.Archer;
import es.gpulido.annotationmodel.competition.Award;
import es.gpulido.annotationmodel.competition.Competition;
import es.gpulido.annotationmodel.target.TotalDefinition;
import es.gpulido.annotationmodel.training.BowConfiguration;
import io.realm.OrderedRealmCollectionSnapshot;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by ggpt on 1/12/2015.
 */
public class ContenderHelper {


    public static List<Calendar> contenderUsedDaysOnCompetition(String tournamentUUID, String archerUUID, String competitionUUID)
    {
        try (Realm realm = RealmHelper.getActiveInstance()) {
            return Stream.of(realm.where(Contender.class)
                    .equalTo("tournament.UUID", tournamentUUID)
                    .equalTo("archer.UUID", archerUUID)
                    .equalTo("competition.UUID", competitionUUID)
                    .equalTo("deleted", Boolean.FALSE).findAll())
                    .map(c -> {
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(c.getInitCompetitionDate());
                        return cal;

                    }).collect(Collectors.toList());
        }
    }

    public static String CreateNewTournamentContender(Realm realm, Tournament tournament, Archer archer, Competition competition) {
        boolean inTrasaction = realm.isInTransaction();
        if (!inTrasaction)
            realm.beginTransaction();
        Contender contender = realm.createObject(Contender.class, UUID.randomUUID().toString());
        contender.setDeleted(false);
        contender.setState(Contender.ACTIVE);
        contender.setTournament(tournament);
        contender.setArcher(archer);
        contender.setName(archer.getName() + " " + archer.getSurname());
        contender.setClub(archer.getClub());
        contender.setCompetition(competition);
        if (!inTrasaction)
            realm.commitTransaction();
        return contender.getUUID();

    }

    public static boolean CanCreateRanking(String tournamentUUID, String archerUUID, String competitionUUID, Date competitionDate) {

        try (Realm realm = RealmHelper.getActiveInstance()) {
            //check if the archer already has the same ranking.
            return realm.where(Contender.class)
                    .equalTo("tournament.UUID", tournamentUUID)
                    .equalTo("archer.UUID", archerUUID)
                    .equalTo("competition.UUID", competitionUUID)
                    .equalTo("initCompetitionDate", competitionDate)
                    .equalTo("deleted", Boolean.FALSE)
                    .count() == 0;
        }
    }

    public static String CreateNewTraining(Realm realm, Tournament tournament, BowConfiguration bowConfiguration, Competition competition, Date competitionDate) {
        boolean inTrasaction = realm.isInTransaction();
        if (!inTrasaction)
            realm.beginTransaction();
        Contender contender = realm.createObject(Contender.class, UUID.randomUUID().toString());
        contender.setDeleted(false);
        contender.setState(Contender.ACTIVE);
        contender.setTournament(tournament);
        contender.setBowConfiguration(bowConfiguration);
        contender.setName(bowConfiguration.getName());
        contender.setCompetition(competition);
        contender.setInitCompetitionDate(competitionDate);

        contender.GenerateContenderAnnotations(realm);
        if (!inTrasaction)
            realm.commitTransaction();
        return contender.getUUID();
    }


    public static String CreateNewRanking(Realm realm,
                                          Tournament tournament,
                                          Archer archer,
                                          Competition competition,
                                          Date competitionDate,
                                          Archer annotator) {
        boolean inTrasaction = realm.isInTransaction();
        if (!inTrasaction)
            realm.beginTransaction();
        Contender contender = realm.createObject(Contender.class, UUID.randomUUID().toString());
        contender.setDeleted(false);
        contender.setState(Contender.ACTIVE);
        contender.setTournament(tournament);
        contender.setArcher(archer);
        contender.setName(archer.getName() + " " + archer.getSurname());
        contender.setClub(archer.getClub());
        contender.setCompetition(competition);
        contender.setInitCompetitionDate(competitionDate);
        contender.setOrder(realm.where(Contender.class).max("order").intValue() +1);
        contender.setAnnotator(annotator);

        contender.GenerateContenderAnnotations(realm);
        if (!inTrasaction)
            realm.commitTransaction();
        return contender.getUUID();
    }

    public static String modifyRanking(Realm realm,
                                       String contenderUUID,
                                       Competition competition,
                                       Date competitionDate,
                                       Archer annotator) {
        boolean inTrasaction = realm.isInTransaction();
        Contender contender = RealmHelper.getByPrimaryKey(realm, Contender.class, contenderUUID);
        boolean migrateAnnotations = !competition.getUUID().equals(contender.getCompetition().getUUID());
        if (!inTrasaction)
            realm.beginTransaction();
        contender.setDeleted(false);
        contender.setState(Contender.ACTIVE);
        contender.setCompetition(competition);
        contender.setInitCompetitionDate(competitionDate);
        contender.setAnnotator(annotator);
        if (migrateAnnotations) {
            contender.totals.deleteAllFromRealm();
            OrderedRealmCollectionSnapshot<Annotation> oldAnnotations = contender.annotations.createSnapshot();
            Stream.of(oldAnnotations).forEach(annotation -> annotation.annotationTotalValues.deleteAllFromRealm());
            contender.GenerateContenderAnnotations(realm, true);

            for (Annotation oldAnnotation : oldAnnotations) {
                oldAnnotation.setContender(null);
                Annotation newAnnotation = contender.annotations
                                            .where()
                                            .equalTo("end.order", oldAnnotation.getEnd().getOrder())
                                            .findFirst();
                for (AnnotationValue av : oldAnnotation.annotationValues) {
                    AnnotationValue newValue = newAnnotation.annotationValues.where().equalTo("fieldDefinition.order", av.getFieldDefinition().getOrder()).findFirst();
                    if (newValue != null)
                        newValue.SetAnnotationValue(av.getStringFieldValue());
                }
            }
            for (Annotation oldAnnotation : oldAnnotations) {
                Annotation an = RealmHelper.getByPrimaryKey(realm, Annotation.class, oldAnnotation.getUUID());
                an.setContender(null);
                oldAnnotation.deleteRelatedObjectsFromRealm();
                an.deleteFromRealm();
            }
        }
        if (!inTrasaction)
            realm.commitTransaction();
        return contender.getUUID();
    }

    @Deprecated
    public static Map<TotalDefinition, Double> GetTotalsForContender(Realm realm, String contenderUUID) {
        RealmQuery<AnnotationTotalValue> contenderAnnotationsQuery =
                realm.where(AnnotationTotalValue.class)
                        .equalTo("annotation.contender.UUID", contenderUUID);
        return GroupTotalsForAnnotationsValue(contenderAnnotationsQuery.findAll());
    }


    @SuppressLint("NewApi")
    public static Map<TotalDefinition, Double> GetTotalsByRoundAndContender(String roundUUID, String contenderUUID) {
        try (Realm realm = RealmHelper.getActiveInstance()) {
            RealmResults<AnnotationTotalValue> contenderAnnotations =
                    realm.where(AnnotationTotalValue.class)
                            .equalTo("annotation.contender.UUID", contenderUUID)
                            .equalTo("annotation.end.round.UUID", roundUUID)
                            .findAll();
            return GroupTotalsForAnnotationsValue(contenderAnnotations);
        }

    }

    public static Map<TotalDefinition, Double> GetAccumulatedTotalsByEndOrderAndContender(Realm realm, int endOrder, String roundUUID, String contenderUUID) {

        RealmResults<AnnotationTotalValue> contenderAnnotations =
                realm.where(AnnotationTotalValue.class)
                        .equalTo("annotation.contender.UUID", contenderUUID)
                        .equalTo("annotation.end.round.UUID", roundUUID)
                        .lessThanOrEqualTo("annotation.end.order", endOrder)
                        .findAll();

        return GroupTotalsForAnnotationsValue(contenderAnnotations);

    }


    public static Map<TotalDefinition, Double> GetTotalsByEndAndContender(Realm realm, String endUUID, String contenderUUID) {
        RealmResults<AnnotationTotalValue> contenderAnnotations =
                realm.where(AnnotationTotalValue.class)
                        .equalTo("annotation.contender.UUID", contenderUUID)
                        .equalTo("annotation.end.UUID", endUUID)
                        .findAll();
        return GroupTotalsForAnnotationsValue(contenderAnnotations);
    }

    private static Map<TotalDefinition, Double> GroupTotalsForAnnotationsValue(RealmResults<AnnotationTotalValue> annotationsValue) {
        return Stream.of(annotationsValue)
                .groupBy(AnnotationTotalValue::getTotalDefinition)
                .collect(Collectors.toMap(Map.Entry::getKey, value2 ->
                {
                    if (value2.getKey().getAggregationExpression().equals(TotalDefinition.SUM))
                        return Stream.of(value2.getValue())
                                .collect(Collectors.summingDouble(AnnotationTotalValue::getDoubleFieldValue));

                    else {
                        return Stream.of(value2.getValue())
                                .collect(Collectors.averagingDouble(AnnotationTotalValue::getDoubleFieldValue));
                    }
                }));

    }

    public static List<Award> GetAwardForContender(Realm realm, String contenderUUID) {

        Contender contender = RealmHelper.getByPrimaryKey(realm, Contender.class, contenderUUID);
        Map<TotalDefinition, Double> totals = GetTotalsForContender(realm, contenderUUID);

        return Stream.of(totals).map(total ->

                contender.getCompetition()
                        .getAwards()
                        .where()
                        .equalTo("totalNameString", total.getKey().getName())
                        .lessThanOrEqualTo("minTotalValue", total.getValue().intValue())
                        .sort("minTotalValue")
                        .findAll()

        ).filter(r -> r.size() > 0).map(RealmResults::last).collect(Collectors.toList());

    }

    public static int getMainTotalByRoundAndContender(String roundUUID, String contenderUUID) {
        return GetMainTotalFromTotals(GetTotalsByRoundAndContender(roundUUID, contenderUUID));
    }

    public static int GetMainTotalFromTotals(Map<TotalDefinition, Double> contenderTotals) {
        return Stream.of(contenderTotals).filter(value -> value.getKey().isMainTotal()).collect(Collectors.summingDouble(Map.Entry::getValue)).intValue();
    }

    public static Map<TotalDefinition, Double> ExcludeMainTotalFromTotals(Map<TotalDefinition, Double> contenderTotals) {
        return Stream.of(contenderTotals).filter(value -> !value.getKey().isMainTotal()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }



}
