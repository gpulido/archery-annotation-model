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

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.Date;
import java.util.List;

import es.gpulido.annotationmodel.Interfaces.ICanBeDeleted;
import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.archer.Club;
import es.gpulido.annotationmodel.category.Division;
import es.gpulido.annotationmodel.competition.Competition;
import es.gpulido.annotationmodel.archer.ShotField;
import es.gpulido.annotationmodel.competition.Template;
import es.gpulido.annotationmodel.helper.ModelTypes;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 19/06/14.
 */
@SuppressWarnings("unused")
public class Tournament extends RealmObject implements ICanBeDeleted, IHasPrimarykey {

    //private java.util.Date Date;
    @PrimaryKey
    private String UUID;

    private String name;
    private String description;
    private @ModelTypes.TournamentTypes String tournamentType;

    private Date dateTime;
    private Date endDateTime;
    private Club hostingClub; //the club where the tournament is taking place
    private ShotField field;

    private Template mailTemplate;

    private RealmList<Annotator> annotators;
    private RealmList<Competition> competitions;

    @LinkingObjects("tournament")
    public final RealmResults<Round> rounds = null;
    @LinkingObjects("tournament")
    public final RealmResults<Parapet> parapets = null;
    @LinkingObjects("tournament")
    public final RealmResults<Contender> contenders = null;

    private boolean isGenerated;

    @Deprecated
    private boolean isShared;


    private boolean isInscriptionClosed;
    private boolean deleted;
    private String imageResourceName;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public RealmList<Competition> getCompetitions() {
        return competitions;
    }

    public void setCompetitions(RealmList<Competition> competitions) {
        this.competitions = competitions;
    }


    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public ShotField getField() {
        return field;
    }

    public void setField(ShotField field) {
        this.field = field;
    }

    public boolean getIsGenerated() {
        return isGenerated;
    }

    public void setIsGenerated(boolean isGenerated) {
        this.isGenerated = isGenerated;
    }

    public @ModelTypes.TournamentTypes String getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(@ModelTypes.TournamentTypes String tournamentType) {
        this.tournamentType = tournamentType;
    }

    public Date getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
        this.endDateTime = endDateTime;
    }

    @Deprecated
    public boolean getIsShared() {
        return isShared;
    }

    public void setIsShared(boolean shared) {
        isShared = shared;
    }

    public boolean getIsInscriptionClosed() {
        return isInscriptionClosed;
    }

    public void setIsInscriptionClosed(boolean inscriptionClosed) {
        isInscriptionClosed = inscriptionClosed;
    }

    public Club getHostingClub() {
        return hostingClub;
    }

    public void setHostingClub(Club hostingClub) {
        this.hostingClub = hostingClub;
    }


    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public void delete(Realm realm)
    {
        realm.beginTransaction();
        setDeleted(true);
        realm.commitTransaction();
    }

    @Override
    public void undoDelete(Realm realm) {
        realm.beginTransaction();
        setDeleted(false);
        realm.commitTransaction();
    }

    @Deprecated
    public void publish(Realm realm, boolean publish)
    {
        realm.beginTransaction();
        setIsShared(publish);
        realm.commitTransaction();
    }

    public RealmList<Annotator> getAnnotators() {
        return annotators;
    }

    public void setAnnotators(RealmList<Annotator> annotators) {
        this.annotators = annotators;
    }

    public List<Division> getTournamentDivisions()
    {
        return Stream.of(competitions).map(c-> c.getCategory().getDivision()).distinct().collect(Collectors.toList());
    }

    public void refreshClassification()
    {
        if (!getTournamentType().equals(ModelTypes.T_COMPETITION)) return;
        Stream.of(getCompetitions()).forEach(competition -> refreshCompetitionClassification(competition.getUUID()));
    }

    public void refreshCompetitionClassification(String competitionUUID) {

        if (!getTournamentType().equals(ModelTypes.T_COMPETITION)) return;

        RealmResults<Contender> contenders2 = contenders.where()
                .equalTo("deleted", false)
                .equalTo("competition.UUID", competitionUUID)
                .findAll();//"totalDefinition.isMainTotal.doubleFieldValue");
        List<Contender> contenderSorted = Stream.of(contenders2).sorted(Contender.totalsComparator).collect(Collectors.toList());
        for(int i = 0; i<contenderSorted.size(); i++)
        {
            Contender c1 = contenderSorted.get(i);
            if (i == 0)
            {
                c1.setClassificationOrder(i + 1);
            }
            else
            {
                Contender c2 = contenderSorted.get(i- 1);
                if (Contender.totalsComparator.compare(c1,c2) == 0)
                {
                    c1.setClassificationOrder(c2.getClassificationOrder());
                }
                else
                    c1.setClassificationOrder(i + 1);
            }

        }

    }


    public Template getMailTemplate() {
        return mailTemplate;
    }

    public void setMailTemplate(Template mailTemplate) {
        this.mailTemplate = mailTemplate;
    }

    public String getImageResourceName() {
        return imageResourceName;
    }

    public void setImageResourceName(String imageResourceName) {
        this.imageResourceName = imageResourceName;
    }
}
