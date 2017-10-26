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

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 13/11/14.
 */
@SuppressWarnings("unused")
public class FieldDefinition extends RealmObject implements IHasPrimarykey{
    @PrimaryKey
    private String UUID;
    @Index
    private String name;

    private String description;
    private String shortDescription;
    private FieldType fieldType;
//    private Target target;


    private boolean canHaveTargetPosition;

    private int order;
    private String valueExpression;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

//    public Target getTarget() {
//        return target;
//    }
//
//    public void setTarget(Target target) {
//        this.target = target;
//    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public boolean isCanHaveTargetPosition() {
        return canHaveTargetPosition;
    }

    public void setCanHaveTargetPosition(boolean canHaveTargetPosition) {
        this.canHaveTargetPosition = canHaveTargetPosition;
    }


    public static FieldDefinition findOrCreate(Realm realm,
                                               String name,
                                               String description,
                                               FieldType fieldType,
                                               int order)
    {
        FieldDefinition newField =realm.where(FieldDefinition.class).equalTo("name", name).equalTo("fieldType.UUID", fieldType.getUUID()).findFirst();
        if (newField != null) return newField;

        newField = realm.createObject(FieldDefinition.class, TargetDataModule.UUIDGenerator.generate().toString());
        newField.setName(name);
        newField.setDescription(description);
        newField.setShortDescription("");
        newField.setFieldType(fieldType);
        newField.setOrder(order);
        newField.setCanHaveTargetPosition(true);
        return newField;

    }



}
