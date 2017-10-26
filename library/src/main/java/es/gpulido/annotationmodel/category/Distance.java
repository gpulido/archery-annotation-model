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
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Represents each of the Distances where a target could be positioned
 */
public class Distance extends RealmObject{

    @PrimaryKey
    private String name;

    private int meters;


    @StringDef({EIGHT_M,
            EIGHTEEN_M,
            TWENTY_FIVE_M,
            THIRTY_M,
            FORTY_M,
            FIFTY_M,
            SIXTY_M,
            SEVENTY_M,
            NINETY_M
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Distances {}
    public static final String EIGHT_M = "8m";
    public static final String EIGHTEEN_M = "18m";
    public static final String TWENTY_FIVE_M = "25m";
    public static final String THIRTY_M = "30m";
    public static final String FORTY_M = "40m";
    public static final String FIFTY_M = "50m";
    public static final String SIXTY_M = "60m";
    public static final String SEVENTY_M = "70m";
    public static final String NINETY_M = "90m";

    public static Distance getByName(Realm realm, @Distances String name)
    {
        return realm.where(Distance.class).equalTo("name", name).findFirst();
    }

    public static void createDefaultData(Realm realm)
    {
        Timber.d("Init Distances");
        Distance distance = realm.createObject(Distance.class, EIGHT_M);
        distance.setMeters(8);

        distance = realm.createObject(Distance.class, EIGHTEEN_M);
        distance.setMeters(18);

        distance = realm.createObject(Distance.class, TWENTY_FIVE_M);
        distance.setMeters(25);

        distance = realm.createObject(Distance.class, THIRTY_M);
        distance.setMeters(30);

        distance = realm.createObject(Distance.class, FORTY_M);
        distance.setMeters(40);

        distance = realm.createObject(Distance.class, FIFTY_M);
        distance.setMeters(50);

        distance = realm.createObject(Distance.class, SIXTY_M);
        distance.setMeters(60);

        distance = realm.createObject(Distance.class, SEVENTY_M);
        distance.setMeters(70);

        distance = realm.createObject(Distance.class, NINETY_M);
        distance.setMeters(90);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMeters() {
        return meters;
    }

    public void setMeters(int meters) {
        this.meters = meters;
    }
}
