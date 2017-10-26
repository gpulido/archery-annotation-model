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

package es.gpulido.annotationmodel.annotation;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.helper.RealmHelper;
import es.gpulido.annotationmodel.management.ManagementDataModule;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 3/07/16.
 */

@SuppressWarnings("unused")
/**
 * Each of the annotators that are being registered on each tournament.
 * The annotator is local if it match the device uuid where it is running;
 */
public class Annotator extends RealmObject implements IHasPrimarykey {

    @PrimaryKey
    private String UUID;
    private String name;

    @Override
    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public static Annotator CreateLocalAnnotator(Realm realm, String name)
    {
        Annotator annotator = new Annotator();
        annotator.setUUID(ManagementDataModule.getActiveServerAnnotatorUUID());
        annotator.setName(name);
        realm.beginTransaction();
        annotator = realm.copyToRealmOrUpdate(annotator);
        realm.commitTransaction();
        return annotator;

    }

    public boolean isLocalAnnotator() {
        return ManagementDataModule.getActiveServerAnnotatorUUID().equals(UUID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
