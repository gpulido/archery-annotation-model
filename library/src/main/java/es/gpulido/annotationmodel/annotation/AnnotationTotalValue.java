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

import java.util.Map;

import es.gpulido.annotationmodel.Interfaces.ITotalValue;
import es.gpulido.annotationmodel.target.TotalDefinition;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 13/11/14.
 */
public class AnnotationTotalValue extends RealmObject  implements ITotalValue {
    @PrimaryKey
    private String UUID;

    private Annotation annotation;

    private TotalDefinition totalDefinition;

    private double doubleFieldValue;

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public TotalDefinition getTotalDefinition() {
        return totalDefinition;
    }

    public void setTotalDefinition(TotalDefinition totalDefinition) {
        this.totalDefinition = totalDefinition;
    }


    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public double getDoubleFieldValue() {
        return doubleFieldValue;
    }

    public void setDoubleFieldValue(double doubleFieldValue) {
        this.doubleFieldValue = doubleFieldValue;
    }

    public void calculateTotalValue( Map<String, Long> extClosure) {
        Double newValue = getTotalDefinition().calculateValue(extClosure);
        if (getDoubleFieldValue()!= newValue) {
            setDoubleFieldValue(newValue);
        }
        annotation.getContender().totals.where().equalTo("totalDefinition.UUID", totalDefinition.getUUID()).findFirst().updateTotal();
    }
}
