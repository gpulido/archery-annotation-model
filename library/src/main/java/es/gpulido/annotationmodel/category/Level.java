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

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by GGPT on 2/22/2017.
 */

public class Level extends RealmObject{

    @PrimaryKey
    private String UUID;
    @Index
    private @Levels String name;

    public Level()
    {}

    public Level(@Levels String name)
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

    public @Levels String getName() {
        return name;
    }

    public void setName(@Levels String name) {
        this.name = name;
    }


    public static void createDefaultData(Realm realm)
    {
        Timber.i("Creating Levels");
        Level level = new Level(JUNIOR);
        realm.copyToRealm(level);

        level = new Level(CADET);
        realm.copyToRealm(level);

        level = new Level(CHILD);
        realm.copyToRealm(level);

        level = new Level(SENIOR);
        realm.copyToRealm(level);

        level = new Level(MASTER);
        realm.copyToRealm(level);

        level = new Level(NOVEL);
        realm.copyToRealm(level);

    }


    @StringDef({JUNIOR, CADET, CHILD, SENIOR, MASTER, NOVEL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Levels{}
    public static final String JUNIOR = "Junior";
    public static final String CADET = "Cadet";
    public static final String CHILD = "Child"; //Infantil
    public static final String SENIOR = "Senior";
    public static final String MASTER = "Master";
    public static final String NOVEL = "Novel";

}
