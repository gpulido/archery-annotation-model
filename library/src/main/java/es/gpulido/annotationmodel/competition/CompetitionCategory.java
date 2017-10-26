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

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by gpt on 13/11/14.
 */
@SuppressWarnings("unused")
public class CompetitionCategory extends RealmObject {

    @PrimaryKey
    private String UUID;
    private @Competitions String name;

    public CompetitionCategory()
    {}

    public CompetitionCategory(String name)
    {
        this.UUID = CompetitionDataModule.UUIDGenerator.generate().toString();
        this.name = name;

    }

    public static CompetitionCategory getByName(Realm realm, @Competitions String name) {
        return realm.where(CompetitionCategory.class).equalTo("name", name).findFirst();
    }

    public static void createDefaultData(Realm realm)
    {
        Timber.i("Creating Categories");
        List<CompetitionCategory> competitionCategories =
                Arrays.asList(new CompetitionCategory(COMPETITION),
                              new CompetitionCategory(RANKING_SUMMER),
                              new CompetitionCategory(COMPETITION),
                              new CompetitionCategory(RANKING_SUMMER),
                              new CompetitionCategory(RANKING_WINTER),
                              new CompetitionCategory(TRAINING));
        realm.insert(competitionCategories);
    }

    @StringDef({COMPETITION, RANKING_SUMMER, RANKING_WINTER, TRAINING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Competitions{}

    public static final String COMPETITION = "Competicion";
    public static final String RANKING_SUMMER = "Ranking Verano";
    public static final String RANKING_WINTER = "Ranking Invierno";
    public static final String TRAINING = "Entrenamiento";



    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public @Competitions String getName() {
        return name;
    }

    public void setName(@Competitions String name) {
        this.name = name;
    }
}
