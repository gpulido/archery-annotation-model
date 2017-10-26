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

package es.gpulido.annotationmodel.category;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by GGPT on 2/22/2017.
 */

public class Discipline extends RealmObject implements IHasPrimarykey {

    @PrimaryKey
    private String UUID;
    @Index
    private @Disciplines String name;

    public Discipline(){

    }

    public Discipline(@Disciplines String name)
    {
        this.UUID = CategoryDataModule.UUIDGenerator.generate().toString();
        this.name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public @Disciplines String getName() {
        return name;
    }

    public void setName(@Disciplines String name) {
        this.name = name;
    }

    public static void createDefaultData(Realm realm)
    {

            Timber.i("Creating Disciplines");
            Discipline discipline = new Discipline(OUTDOOR);
            realm.copyToRealm(discipline);

            discipline = new Discipline(INDOOR);
            realm.copyToRealm(discipline);

            discipline = new Discipline(FIELD);
            realm.copyToRealm(discipline);

            discipline = new Discipline(PARA);
            realm.copyToRealm(discipline);

            discipline = new Discipline(RUN);
            realm.copyToRealm(discipline);

            discipline = new Discipline(CLOUT);
            realm.copyToRealm(discipline);

            discipline = new Discipline(FLIGHT);
            realm.copyToRealm(discipline);

            discipline = new Discipline(SKI);
            realm.copyToRealm(discipline);

            discipline = new Discipline(THREED);
            realm.copyToRealm(discipline);

    }

    @StringDef({OUTDOOR, INDOOR, FIELD, PARA, RUN, CLOUT, FLIGHT, SKI, THREED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Disciplines{}
    public static final String OUTDOOR = "Outdoor";
    public static final String INDOOR = "Indoor";
    public static final String FIELD = "Field";
    public static final String PARA = "Para";
    public static final String RUN = "Run";
    public static final String CLOUT = "Clout";
    public static final String FLIGHT = "Flight";
    public static final String SKI = "Ski";
    public static final String THREED = "3D";
}

