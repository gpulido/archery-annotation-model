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

public class ClassGender extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;
    @Index
    private @ClassGenders
    String name;

    public ClassGender(){

    }

    public ClassGender(@ClassGenders String name)
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

    public @ClassGenders
    String getName() {
        return name;
    }

    public void setName(@ClassGenders String name) {
        this.name = name;
    }

    public static void createDefaultData(Realm realm) {

        Timber.i("Creating Class Genders");
        ClassGender gender = new ClassGender(WOMEN);
        realm.copyToRealm(gender);

        gender = new ClassGender(MEN);
        realm.copyToRealm(gender);

        gender = new ClassGender(MIX);
        realm.copyToRealm(gender);

    }

    @StringDef({WOMEN, MEN, MIX})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ClassGenders {}
    public static final String WOMEN = "Women";
    public static final String MEN = "Men";
    public static final String MIX = "Mix";
}
