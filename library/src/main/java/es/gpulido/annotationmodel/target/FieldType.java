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

import android.graphics.Color;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.helper.Utils;
import io.realm.Case;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 13/11/14.
 */
@SuppressWarnings("unused")
public class FieldType extends RealmObject implements IHasPrimarykey{


    @StringDef({NUMBER, DOUBLE, STRING, MINUS10, MINUS2, FACE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FieldTypes{}
    public static final String NUMBER = "number";
    public static final String DOUBLE = "double";
    public static final String STRING = "string";
    public static final String MINUS10 = "minus10";
    public static final String MINUS2 = "minus2";
    public static final String FACE = "face";

    @PrimaryKey
    private String UUID;



    private int color;
    //MUST BE THE SAME NAME AS THE CHILD to find the linked field
    private String name;
    private String description;
    private String childType;
    private String KeyboardName;
    private int inputTypeId;

    //FaceType
    private int size;
    private String faceImageResourceName;
    private RealmList<FaceRing> faceRings;

    @Override
    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID)
    {
        this.UUID= UUID;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChildType() {
        return childType;
    }

    public void setChildType(String childType) {
        this.childType = childType;
    }

    public int getInputTypeId() {
        return inputTypeId;
    }

    public void setInputTypeId(int inputTypeId) {
        this.inputTypeId = inputTypeId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public RealmList<FaceRing> getFaceRings() {
        return faceRings;
    }

    public void setFaceRings(RealmList<FaceRing> faceRings) {
        this.faceRings = faceRings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFaceImageResourceName() {
        return faceImageResourceName;
    }

    public void setFaceImageResourceName(String faceImageResourceName) {
        this.faceImageResourceName = faceImageResourceName;
    }

    public String getKeyboardName() {
        return KeyboardName;
    }

    public void setKeyboardName(String keyboardName) {
        KeyboardName = keyboardName;
    }

    public int getValueColor(String fieldValue)
    {
        switch (getChildType()) {
            case FACE:
                FaceRing ring = getFaceRings().where().equalTo("symbol", fieldValue, Case.INSENSITIVE).findFirst();
                if (ring!= null) return ring.getColor();
                return Color.LTGRAY;
            case NUMBER:
                return Color.LTGRAY;
            case DOUBLE:
                return Color.LTGRAY;
            default:
                return Color.LTGRAY;
        }
    }

    public  Integer getValueIntValue(String fieldValue)
    {
        if (fieldValue.equals("")) return 0;
        if (getChildType().equals(FieldType.FACE))
        {
            FaceRing faceRing =  getFaceRings().where().equalTo("symbol", fieldValue, Case.INSENSITIVE).findFirst();
            if (faceRing != null)
                return faceRing.getValue();
            //TODO: review this value
            return 0;
        }
        else if(getChildType().equals(FieldType.NUMBER))
        {
            Integer intValue = Utils.tryParseInt(fieldValue);
            return intValue == null ? 0:intValue;
        }
        return 0;
    }

}
