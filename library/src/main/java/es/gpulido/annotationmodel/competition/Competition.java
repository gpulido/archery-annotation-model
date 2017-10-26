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

package es.gpulido.annotationmodel.competition;

import android.support.annotation.NonNull;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.category.Category;
import es.gpulido.annotationmodel.serie.Serie;
import es.gpulido.annotationmodel.target.FieldType;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by gpt on 13/11/14.
 */
@SuppressWarnings("unused")
public class Competition extends RealmObject implements Comparable<Competition>, IHasPrimarykey{

    @PrimaryKey
    private String UUID;

    private String name;
    private String description;
    private Category category;
    private Serie serie;

    private CompetitionCategory competitionCategory;
    private RealmList<Award> awards;

    public Competition()
    {}

    public Competition(String name, String description, Category category, Serie serie )
    {
        this.UUID = CompetitionDataModule.UUIDGenerator.generate().toString();
        this.name = name;
        this.description = description;
    }


    public static void createDefaultData(Realm realm) {
        createMeetingNone(realm);
    }

    private static void createMeetingNone(Realm realm) {
        Timber.i("Creating Meeting None");
        //region Competitions None
        Meeting meeting = realm.createObject(Meeting.class, CompetitionDataModule.UUIDGenerator.generate().toString());
        meeting.setName("Ninguna");

    }



    public static Competition createStandardCompetition(Realm realm,
                                                       String name,
                                                       String description,
                                                       @Category.Categories String category,
                                                       String serieName,
                                                       @CompetitionCategory.Competitions String competitionCategory)
    {

        return createStandardCompetition(realm, name, description, Category.getByName(realm, category), Serie.getByName(realm, serieName), competitionCategory);

    }

    public static Competition createStandardCompetition(Realm realm,
                                                        String name,
                                                        String description,
                                                        Category category,
                                                        Serie serie,
                                                        @CompetitionCategory.Competitions String competitionCategory)
    {

        Timber.i("Creating Competition %s", name);
        Competition competition = realm.createObject(Competition.class, CompetitionDataModule.UUIDGenerator.generate().toString());
        competition.setName(name);
        competition.setSerie(serie);
        competition.setCategory(category);
        competition.setDescription(description);
        competition.setCompetitionCategory(CompetitionCategory.getByName(realm, competitionCategory));
        return competition;

    }






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


    public CompetitionCategory getCompetitionCategory() {
        return competitionCategory;
    }

    public void setCompetitionCategory(CompetitionCategory competitionCategory) {
        this.competitionCategory = competitionCategory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public RealmList<Award> getAwards() {
        return awards;
    }

    public void setAwards(RealmList<Award> awards) {
        this.awards = awards;
    }

    @Override
    public int compareTo(@NonNull Competition competition) {
        //TODO: review the comparation
        return competition.getName().compareTo(getName());
    }



    /**
     * Check if the provided competition is compatible with the current one.
     * Two competitions are compatible if their rounds targets are the same, or they are on the same
     * distance and their size allows them to share parapet
     * TODO: fill the logic
     * @param competition The competition to check
     * @return true if the given competition is compatible with this one
     */
    public boolean isCompatibleWith(Competition competition)
    {
        //TODO:
        return true;
    }


    public FieldType getFirstFace() {
        return getSerie().getSeriesRounds().first().getTarget().getBaseFace();
    }

}
