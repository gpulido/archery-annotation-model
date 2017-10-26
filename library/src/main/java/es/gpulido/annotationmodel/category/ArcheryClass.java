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
 * Created by gpt on 22/02/17.
 */

public class ArcheryClass extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;
    @Index
    private @ArcheryClasses String name;

    private ClassGender gender;
    private Level level;


    public ArcheryClass(){

    }
    public ArcheryClass(String name, Level level, ClassGender gender)
    {
        this.UUID = CategoryDataModule.UUIDGenerator.generate().toString();
        this.name = name;
        this.gender = gender;
        this.level = level;
    }

    @StringDef({CHILD_MIX, CADET_WOMEN, CADET_MEN, CADET_MEN, JUNIOR_WOMEN, JUNIOR_MEN, SENIOR_WOMEN, SENIOR_MEN, SENIOR_MIX, MASTER_WOMEN, MASTER_MEN, NOVEL_MIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ArcheryClasses{}
    public static final String CHILD_MIX = "Child Mix";
    public static final String CADET_WOMEN = "Cadet Women";
    public static final String CADET_MEN = "Cadet Men";
    public static final String JUNIOR_WOMEN = "Junior Women";
    public static final String JUNIOR_MEN = "Junior Men";;
    public static final String SENIOR_WOMEN = "Women";
    public static final String SENIOR_MEN = "Men";
    public static final String SENIOR_MIX = "Mix";
    public static final String MASTER_WOMEN = "Master Women";
    public static final String MASTER_MEN = "Master Men";
    public static final String NOVEL_MIX = "Novel";


    public static void createDefaultData(Realm realm)
    {
        Timber.i("Creating ArcheryClass");
        createArcheryClass(realm, CHILD_MIX, Level.CHILD, ClassGender.MIX);
        createArcheryClass(realm, CADET_WOMEN, Level.CADET, ClassGender.WOMEN);
        createArcheryClass(realm, CADET_MEN, Level.CADET, ClassGender.MEN);
        createArcheryClass(realm, JUNIOR_WOMEN, Level.JUNIOR, ClassGender.WOMEN);
        createArcheryClass(realm, JUNIOR_MEN, Level.JUNIOR, ClassGender.MEN);
        createArcheryClass(realm, SENIOR_WOMEN, Level.SENIOR, ClassGender.WOMEN);
        createArcheryClass(realm, SENIOR_MEN, Level.SENIOR, ClassGender.MEN);
        createArcheryClass(realm, SENIOR_MIX, Level.SENIOR, ClassGender.MIX);
        createArcheryClass(realm, MASTER_WOMEN, Level.MASTER, ClassGender.WOMEN);
        createArcheryClass(realm, MASTER_MEN, Level.MASTER, ClassGender.MEN);
        createArcheryClass(realm, NOVEL_MIX, Level.NOVEL, ClassGender.MIX);
    }

    private static void createArcheryClass(Realm realm,
                                      @ArcheryClasses String name,
                                      @Level.Levels String level,
                                      @ClassGender.ClassGenders String gender) {
        Timber.i("Creating ArcheryClass %s", name);
        ArcheryClass archeryClass = realm.createObject(ArcheryClass.class, CategoryDataModule.UUIDGenerator.generate().toString());
        archeryClass.setName(name);
        archeryClass.setLevel(realm.where(Level.class).equalTo("name", level).findFirst());
        archeryClass.setGender(realm.where(ClassGender.class).equalTo("name", gender).findFirst());
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public @ArcheryClasses String getName() {
        return name;
    }

    public void setName(@ArcheryClasses String name) {
        this.name = name;
    }

    public ClassGender getGender() {
        return gender;
    }

    public void setGender(ClassGender gender) {
        this.gender = gender;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
