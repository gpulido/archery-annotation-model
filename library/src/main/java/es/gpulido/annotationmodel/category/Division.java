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

public class Division extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;
    @Index
    private @Divisions String name;
    private String bowResourceName;

    public Division(){

    }

    public Division(@Divisions String name)
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

    public @Divisions String getName() {
        return name;
    }

    public void setName(@Divisions String name) {
        this.name = name;
    }

    public static void createDefaultData(Realm realm)
    {
        Timber.i("Creating Divisions");
        Division division = new Division(RECURVE_BOW);
        division.setBowResourceName("bow_recurve");
        realm.copyToRealm(division);
        division = new Division(COMPOUND_BOW);
        division.setBowResourceName("bow_compound");
        realm.copyToRealm(division);
        division = new Division(STANDARD_BOW);
        realm.copyToRealm(division);
        division = new Division(BARE_BOW);
        realm.copyToRealm(division);
        division = new Division(INSTICTIVE_BOW);
        division.setBowResourceName("bow_traditional");
        realm.copyToRealm(division);
        division = new Division(LONGBOW);
        division.setBowResourceName("bow_longbow");
        realm.copyToRealm(division);
        division = new Division(AMERICAN_LONGBOW);
        realm.copyToRealm(division);
        division = new Division(ENGLISH_LONGBOW);
        realm.copyToRealm(division);
        division = new Division(FOOT_BOW);
        realm.copyToRealm(division);

    }

    public String getBowResourceName() {
        return bowResourceName;
    }

    public void setBowResourceName(String bowResourceName) {
        this.bowResourceName = bowResourceName;
    }

    public static Division getByName(Realm realm, @Divisions String division) {
        return realm.where(Division.class).equalTo("name", division).findFirst();
    }

    @StringDef({RECURVE_BOW, COMPOUND_BOW, STANDARD_BOW, BARE_BOW, INSTICTIVE_BOW, LONGBOW, AMERICAN_LONGBOW, ENGLISH_LONGBOW, FOOT_BOW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Divisions{}
    public static final String RECURVE_BOW = "Recurve Bow";
    public static final String COMPOUND_BOW = "Compound Bow";
    public static final String STANDARD_BOW = "Standard Bow";
    public static final String BARE_BOW = "Bare Bow";
    public static final String INSTICTIVE_BOW = "Instinctive Bow";
    public static final String LONGBOW = "Longbow";
    public static final String AMERICAN_LONGBOW = "American Longbow";
    public static final String ENGLISH_LONGBOW = "English Longbow";
    public static final String FOOT_BOW = "Foot Bow";
}
