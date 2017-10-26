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

package es.gpulido.annotationmodel.target;

import android.support.annotation.Nullable;

import es.gpulido.annotationmodel.category.Distance;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 13/11/14.
 */
@SuppressWarnings("unused")
public class Target extends RealmObject{
    @PrimaryKey
    private String name;
    private String description;
    private Distance distance;
    private RealmList<FieldDefinition> fieldDefinitions;
    private RealmList<TotalDefinition> totalDefinitions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public RealmList<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }

    public void setFieldDefinitions(RealmList<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
    }

    public RealmList<TotalDefinition> getTotalDefinitions() {
        return totalDefinitions;
    }

    public void setTotalDefinitions(RealmList<TotalDefinition> totalDefinitions) {
        this.totalDefinitions = totalDefinitions;
    }

    public @Nullable FieldType getBaseFace() {
        FieldDefinition fd = getFieldDefinitions().where().equalTo("fieldType.childType", FieldType.FACE).findFirst();
        if (fd != null)
            return (fd.getFieldType());
        return null;
    }

    public static Target getbyName(Realm realm, String target) {
        return realm.where(Target.class).equalTo("name", target).findFirst();
    }
    //private int maxNumFacesParapet;

}
