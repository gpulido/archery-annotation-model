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

package es.gpulido.annotationmodel.serie;

import es.gpulido.annotationmodel.category.CategoryDataModule;
import es.gpulido.annotationmodel.category.Distance;
import es.gpulido.annotationmodel.competition.CompetitionDataModule;
import es.gpulido.annotationmodel.target.FaceHelper;
import es.gpulido.annotationmodel.target.Target;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by GGPT on 2/23/2017.
 */

public class SerieRound extends RealmObject {

    @PrimaryKey
    private String UUID;
    @Index
    private String name;

    private Target target;
    private int numEntries;

    public SerieRound()
    {}

    public SerieRound(String name, Target target, int numEntries)
    {
        this.UUID = CategoryDataModule.UUIDGenerator.generate().toString();
        this.name = name;
        this.target = target;
        this.numEntries = numEntries;
    }


    public static String generateSerieRoundName(String target, int numEntries)
    {
        return target + "_" + numEntries;
    }

    public static SerieRound findOrCreate(Realm realm,
                                         @Distance.Distances String distance,
                                         @FaceHelper.Faces String face,
                                         int numArrows,
                                         int numEntries) {

        return findOrCreate(realm, face + "_" + numArrows + "_"+ distance, numEntries);
    }



    public static SerieRound findOrCreate(Realm realm,
                                         String target,
                                         int numEntries)
    {
        Timber.i("Searching SerieRound %s", target + "_" + generateSerieRoundName(target, numEntries));

        SerieRound serieRound = realm.where(SerieRound.class).equalTo("name", generateSerieRoundName(target, numEntries)).findFirst();
        if (serieRound != null) return serieRound;

        serieRound = realm.createObject(SerieRound.class, CompetitionDataModule.UUIDGenerator.generate().toString());
        serieRound.setName(generateSerieRoundName(target, numEntries));
        serieRound.setTarget(Target.getbyName(realm,target));
        serieRound.setNumEntries(numEntries);
        return serieRound;
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

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public int getNumEntries() {
        return numEntries;
    }

    public void setNumEntries(int numEntries) {
        this.numEntries = numEntries;
    }




}
