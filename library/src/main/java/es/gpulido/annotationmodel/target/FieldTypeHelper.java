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
import android.view.inputmethod.EditorInfo;

import io.realm.Realm;
import timber.log.Timber;


/**
 * Created by ggpt on 1/9/2015.
 */
public class FieldTypeHelper {



    public static final String DEFAULT_NUMBER_TYPE = "DefaultNumberType";
    public static final String DEFAULT_DOUBLE_TYPE = "DefaultDoubleType";
    public static final String DEFAULT_STRING_TYPE = "DefaultStringType";
    public static final String DEFAULT_MINUS2_TYPE = "DefaultMinus2Type";
    public static final String DEFAULT_MINUS10_TYPE = "DefaultMinus10Type";

    public static FieldType CreateNumberType(Realm realm)
    {
        FieldType fieldType = realm.createObject(FieldType.class, TargetDataModule.UUIDGenerator.generate().toString());
        fieldType.setName(DEFAULT_NUMBER_TYPE);
        fieldType.setColor(Color.WHITE);
        fieldType.setChildType(FieldType.NUMBER);
        fieldType.setInputTypeId(EditorInfo.TYPE_CLASS_NUMBER);
        fieldType.setKeyboardName("");
        return fieldType;
    }

    public static FieldType CreateDoubleType(Realm realm)
    {
        FieldType fieldType = realm.createObject(FieldType.class,  TargetDataModule.UUIDGenerator.generate().toString());
        fieldType.setName(DEFAULT_DOUBLE_TYPE);
        fieldType.setColor(Color.WHITE);
        fieldType.setChildType(FieldType.DOUBLE);
        fieldType.setInputTypeId(EditorInfo.TYPE_CLASS_NUMBER);
        fieldType.setKeyboardName("");
        return fieldType;
    }

    public static FieldType CreateMinus10DoubleType(Realm realm)
    {
        FieldType fieldType = realm.createObject(FieldType.class,  TargetDataModule.UUIDGenerator.generate().toString());
        fieldType.setName(DEFAULT_MINUS10_TYPE);
        fieldType.setColor(Color.WHITE);
        fieldType.setChildType(FieldType.NUMBER);
        fieldType.setInputTypeId(EditorInfo.TYPE_NULL);
        fieldType.setKeyboardName("keyboard_espiral_minus10_layout");
        return fieldType;
    }

    public static FieldType CreateMinus2DoubleType(Realm realm)
    {
        FieldType fieldType = realm.createObject(FieldType.class,  TargetDataModule.UUIDGenerator.generate().toString());
        fieldType.setName(DEFAULT_MINUS2_TYPE);
        fieldType.setColor(Color.WHITE);
        fieldType.setChildType(FieldType.NUMBER);
        fieldType.setInputTypeId(EditorInfo.TYPE_NULL);
        fieldType.setKeyboardName("keyboard_espiral_minus2_layout");
        return fieldType;
    }



    public static FieldType CreateStringType(Realm realm)
    {
        Timber.i("Init String type");
        FieldType fieldType = realm.createObject(FieldType.class,  TargetDataModule.UUIDGenerator.generate().toString());
        fieldType.setName(DEFAULT_STRING_TYPE);
        fieldType.setColor(Color.WHITE);
        fieldType.setChildType(FieldType.STRING);
        fieldType.setInputTypeId(EditorInfo.TYPE_CLASS_TEXT);
        fieldType.setKeyboardName("");
        return fieldType;
    }

    public static FieldType CreateFaceType(Realm realm, @FaceHelper.Faces String faceName, String keyboardName)
    {
        FieldType fieldType = realm.createObject(FieldType.class,  TargetDataModule.UUIDGenerator.generate().toString());
        fieldType.setName(faceName);
        fieldType.setColor(Color.WHITE);
        fieldType.setChildType(FieldType.FACE);
        fieldType.setKeyboardName(keyboardName);
        fieldType.setInputTypeId(EditorInfo.TYPE_NULL);
        return fieldType;
    }


}
