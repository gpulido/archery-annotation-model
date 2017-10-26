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

import java.util.Date;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.helper.RealmHelper;
import es.gpulido.annotationmodel.target.FieldDefinition;
import es.gpulido.annotationmodel.target.FieldType;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 13/11/14.
 */
@SuppressWarnings("unused")
public class AnnotationValue extends RealmObject implements IHasPrimarykey{
    @PrimaryKey
    private String UUID;

    private Annotation annotation;

    private FieldDefinition fieldDefinition;

    private String stringFieldValue;
    private double doubleFieldValue;

    private int fieldDrawable;

    private int xPosition;
    private int yPosition;
    private Date annotationDate;


    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public String getStringFieldValue() {
        return stringFieldValue;
    }

    public void setStringFieldValue(String stringFieldValue) {
        this.stringFieldValue = stringFieldValue;
    }

    public FieldDefinition getFieldDefinition() {
        return fieldDefinition;
    }

    public void setFieldDefinition(FieldDefinition fieldDefinition) {
        this.fieldDefinition = fieldDefinition;
    }

    public int getFieldDrawable() {
        return fieldDrawable;
    }

    public void setFieldDrawable(int fieldDrawable) {
        this.fieldDrawable = fieldDrawable;
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

    public int getXPosition() {
        return xPosition;
    }

    public void setXPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public void setYPosition(int yPosition) {
        this.yPosition = yPosition;
    }


    public void SetAnnotationValue(String data) {

        setStringFieldValue(data);
        setDoubleFieldValue((double)getFieldDefinition().getFieldType().getValueIntValue(getStringFieldValue()));
        Date annotationDate = new Date();
        setAnnotationDate(annotationDate);
        getAnnotation().updateIsCompleted();
        //Update contender info
        getAnnotation().getContender().updateIsCompleted();
        getAnnotation().updateTotals();
        if (getAnnotation().isCompleted()) {
            String tournamentUUID = getAnnotation().getTournament().getUUID();
            String competitionUUID = getAnnotation().getContender().getCompetition().getUUID();
            try (Realm realm = RealmHelper.getActiveInstance()) {
                realm.executeTransactionAsync(realm1 -> RealmHelper
                        .getByPrimaryKey(realm1, Tournament.class, tournamentUUID)
                        .refreshCompetitionClassification(competitionUUID));
            }
        }

    }


    public String GetAnnotationValueSymbol()
    {
        FieldType ft = getFieldDefinition().getFieldType();
        if (ft.getChildType().equals(FieldType.FACE))
        {
            return getStringFieldValue();
        }
        else if(ft.getChildType().equals(FieldType.NUMBER))
        {
            return "";
        }
        return "";
    }

    public Date getAnnotationDate() {
        return annotationDate;
    }

    public void setAnnotationDate(Date annotationDate) {
        this.annotationDate = annotationDate;
    }

}
