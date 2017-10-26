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

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.Map;

import es.gpulido.annotationmodel.Interfaces.ITotalValue;
import es.gpulido.annotationmodel.target.TotalDefinition;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 13/11/14.
 */
public class ContenderTotalValue extends RealmObject implements ITotalValue {
    @PrimaryKey
    private String UUID;

    private Contender contender;

    private RealmList<AnnotationTotalValue> annotationTotalValues;

    private TotalDefinition totalDefinition;

    private double doubleFieldValue;

    public Contender getContender() {
        return contender;
    }

    public void setContender(Contender contender) {
        this.contender = contender;
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

    public void updateTotal()
    {

        if (totalDefinition.getAggregationExpression().equals(TotalDefinition.SUM))
            doubleFieldValue = getAnnotationTotalValues().where().sum("doubleFieldValue").doubleValue();
//            doubleFieldValue = Stream.of(getAnnotationTotalValues())
//                    .map(AnnotationTotalValue::getDoubleFieldValue)
//                    .reduce(0.0, (a, b) -> a + b);
        else {
            doubleFieldValue = getAnnotationTotalValues().where().average("doubleFieldValue");
//            doubleFieldValue = Stream.of(getAnnotationTotalValues())
//                    .collect(Collectors.averaging(AnnotationTotalValue::getDoubleFieldValue));
        }
    }

    public RealmList<AnnotationTotalValue> getAnnotationTotalValues() {
        return annotationTotalValues;
    }

    public void setAnnotationTotalValues(RealmList<AnnotationTotalValue> annotationTotalValues) {
        this.annotationTotalValues = annotationTotalValues;
    }
}
