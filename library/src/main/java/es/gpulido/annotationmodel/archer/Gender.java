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

package es.gpulido.annotationmodel.archer;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by gpt on 13/11/14.
 */
public class Gender extends RealmObject{

    @PrimaryKey
    private String name;

    public Gender()
    {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Gender getByName(Realm realm, String name) {
        return realm.where(Gender.class).equalTo("name", name).findFirst();
    }

    @StringDef({MALE, FEMALE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Genders{}

    public static final String MALE = "Hombre";
    public static final String FEMALE = "Mujer";

    public static void createGenderDefaultData(Realm realm) {
        Timber.i("creating Gender Data");
        realm.createObject(Gender.class, MALE);
        realm.createObject(Gender.class, FEMALE);

    }
}
