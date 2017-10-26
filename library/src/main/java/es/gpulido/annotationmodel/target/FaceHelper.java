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

import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by ggpt on 1/12/2015.
 */
@SuppressWarnings("WeakerAccess,SpellCheckingInspection")
public class FaceHelper {
    @StringDef({COMPLETA_122,
            REDUCIDA_80_5,
            REDUCIDA_80_6,
            REDUCIDA_60_TRIPLE,
            REDUCIDA_60_TRIPLE_NO_X,
            REDUCIDA_40_TRIPLE,
            REDUCIDA_40_TRIPLE_TRIANGULAR,
            COMPLETA_40,
            COMPLETA_60,
            COMPLETA_80,
            FIELD_20_TRIPLE,
            FIELD_40,
            FIELD_60,
            FIELD_80,
            ESPIRAL_122_10,
            ESPIRAL_122_9,
            ESPIRAL_122_8})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Faces{}
    public static final String COMPLETA_122 = "122_Completa";
    public static final String REDUCIDA_80_5 = "80_Reducida_5";
    public static final String REDUCIDA_80_6 = "80_Reducida_6";
    public static final String REDUCIDA_60_TRIPLE = "60_Reducida_triple";
    public static final String REDUCIDA_60_TRIPLE_NO_X = "60_Reducida_triple_noX";
    public static final String REDUCIDA_40_TRIPLE = "40_Reducida_triple";
    public static final String REDUCIDA_40_TRIPLE_TRIANGULAR = "40_Reducida_triple_triangular";
    public static final String COMPLETA_40 = "40_Completa";
    public static final String COMPLETA_60 = "60_Completa";
    public static final String COMPLETA_80 = "80_Completa";
    public static final String FIELD_20_TRIPLE = "20_triple_campo";
    public static final String FIELD_80 = "80_Campo";
    public static final String FIELD_60 = "60_Campo";
    public static final String FIELD_40 = "40_Campo";
    public static final String ESPIRAL_122_10 = "122_espiral_10";
    public static final String ESPIRAL_122_9 = "122_espiral_9";
    public static final String ESPIRAL_122_8 = "122_espiral_8";


    public static FieldType getFaceTypeByName(Realm realm, String name)
    {
        return realm.where(FieldType.class).equalTo("name", name).findFirst();
    }

    public static void CreateFaces(Realm realm)
    {
        Timber.i("Init Faces");
        FieldType face = FieldTypeHelper.CreateFaceType(realm, COMPLETA_122, "keyboard_arrow_layout");
        face.setSize(122);
        face.setDescription("122 completa");
        face.setFaceImageResourceName("face");
        CreateYellowRings(realm, face);
        CreateRedRings(realm, face);
        CreateBlueRings(realm, face);
        CreateBlackRings(realm, face);
        CreateWhiteRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, COMPLETA_40,"keyboard_arrow_layout");
        face.setSize(40);
        face.setDescription("40 completa");
        face.setFaceImageResourceName("face_80");
        CreateYellowRings(realm, face);
        CreateRedRings(realm, face);
        CreateBlueRings(realm, face);
        CreateBlackRings(realm, face);
        CreateWhiteRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, COMPLETA_60,"keyboard_arrow_layout");
        face.setSize(60);
        face.setDescription("60 completa");
        face.setFaceImageResourceName("face_80");
        CreateYellowRings(realm, face);
        CreateRedRings(realm, face);
        CreateBlueRings(realm, face);
        CreateBlackRings(realm, face);
        CreateWhiteRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, COMPLETA_80,"keyboard_arrow_layout");
        face.setSize(80);
        face.setDescription("80 completa");
        face.setFaceImageResourceName("face_80");
        CreateYellowRings(realm, face);
        CreateRedRings(realm, face);
        CreateBlueRings(realm, face);
        CreateBlackRings(realm, face);
        CreateWhiteRings(realm, face);
        CreateMissRings(realm, face);


        face = FieldTypeHelper.CreateFaceType(realm, REDUCIDA_80_5, "keyboard_arrow_5_layout");
        face.setSize(80);
        face.setDescription("80 reducida al 5");
        face.setFaceImageResourceName("face_80_5");
        CreateYellowRings(realm, face);
        CreateRedRings(realm, face);
        CreateBlueRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, REDUCIDA_80_6, "keyboard_arrow_6_layout");
        face.setSize(80);
        face.setDescription("80 reducida al 6");
        face.setFaceImageResourceName("face_80_6");
        CreateYellowRings(realm, face);
        CreateRedRings(realm, face);
        CreateFaceRing(realm, "6", 6, Color.BLUE, face);
        CreateMissRings(realm, face);


        face = FieldTypeHelper.CreateFaceType(realm, REDUCIDA_60_TRIPLE, "keyboard_arrow_6_layout");
        face.setSize(60);
        face.setDescription("Triple Vertical 60");
        face.setFaceImageResourceName("face_40_5_triple");
        CreateYellowRings(realm, face);
        CreateRedRings(realm, face);
        CreateFaceRing(realm, "6", 6, Color.BLUE, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, REDUCIDA_60_TRIPLE_NO_X, "keyboard_arrow_6_no_x_layout");
        face.setSize(60);
        face.setDescription("Triple Vertical 60 sin X");
        face.setFaceImageResourceName("face_40_5_triple");
        CreateFaceRing(realm, "10", 10, Color.YELLOW, face);
        CreateFaceRing(realm, "9", 9, Color.YELLOW, face);
        CreateRedRings(realm, face);
        CreateFaceRing(realm, "6", 6, Color.BLUE, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, REDUCIDA_40_TRIPLE, "keyboard_arrow_5_layout");
        face.setSize(40);
        face.setDescription("Triple Vertical");
        face.setFaceImageResourceName("face_40_5_triple");
        CreateYellowRings(realm, face);
        CreateRedRings(realm, face);
        CreateBlueRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, REDUCIDA_40_TRIPLE_TRIANGULAR, "keyboard_arrow_5_layout");
        face.setSize(40);
        face.setDescription("Triple Vertical Triangular");
        face.setFaceImageResourceName("face_40_5_triple_triangular");
        CreateYellowRings(realm, face);
        CreateRedRings(realm, face);
        CreateBlueRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, FIELD_80, "keyboard_traditional_layout");
        face.setSize(80);
        face.setDescription("80 Campo");
        face.setFaceImageResourceName("face_traditional");
        CreateTraditionalRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, FIELD_60, "keyboard_traditional_layout");
        face.setSize(60);
        face.setDescription("60 Campo");
        face.setFaceImageResourceName("face_traditional");
        CreateTraditionalRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, FIELD_40, "keyboard_traditional_layout");
        face.setSize(40);
        face.setDescription("40 Campo");
        face.setFaceImageResourceName("face_traditional");
        CreateTraditionalRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, FIELD_20_TRIPLE, "keyboard_traditional_layout");
        face.setSize(20);
        face.setDescription("20 Triple Campo");
        face.setFaceImageResourceName("face_20_field_triple");
        CreateTraditionalRings(realm, face);
        CreateMissRings(realm, face);

        CreateEspiralFaces(realm);
    }

    public static void CreateEspiralFaces(Realm realm)
    {
        FieldType face = FieldTypeHelper.CreateFaceType(realm, ESPIRAL_122_10, "keyboard_espiral_10_layout");
        face.setSize(122);
        face.setDescription("Espiral max 10");
        face.setFaceImageResourceName("face_espiral");
        CreateFaceRing(realm, "10", 10, Color.YELLOW, face);
        CreateFaceRing(realm, "9", 9, Color.YELLOW, face);
        CreateRedRings(realm, face);
        CreateBlueRings(realm, face);
        CreateBlackRings(realm, face);
        CreateWhiteRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, ESPIRAL_122_9, "keyboard_espiral_9_layout");
        face.setSize(122);
        face.setDescription("Espiral max 9");
        face.setFaceImageResourceName("face_espiral");
        CreateFaceRing(realm, "9", 9, Color.YELLOW, face);
        CreateRedRings(realm, face);
        CreateBlueRings(realm, face);
        CreateBlackRings(realm, face);
        CreateWhiteRings(realm, face);
        CreateMissRings(realm, face);

        face = FieldTypeHelper.CreateFaceType(realm, ESPIRAL_122_8, "keyboard_espiral_8_layout");
        face.setSize(122);
        face.setDescription("Espiral max 8");
        face.setFaceImageResourceName("face_espiral");
        CreateRedRings(realm, face);
        CreateBlueRings(realm, face);
        CreateBlackRings(realm, face);
        CreateWhiteRings(realm, face);
        CreateMissRings(realm, face);
    }

    public static void CreateYellowRings(Realm realm, FieldType face)
    {
        CreateFaceRing(realm, "X", 10, Color.YELLOW, face);
        CreateFaceRing(realm, "10", 10, Color.YELLOW, face);
        CreateFaceRing(realm, "9", 9, Color.YELLOW, face);
    }
    public static void CreateRedRings(Realm realm, FieldType face)
    {
        CreateFaceRing(realm, "8", 8, Color.RED, face);
        CreateFaceRing(realm, "7", 7, Color.RED, face);
    }
    public static void CreateBlueRings(Realm realm, FieldType face)
    {
        CreateFaceRing(realm, "6", 6, Color.BLUE, face);
        CreateFaceRing(realm, "5", 5, Color.BLUE, face);
    }
    public static void CreateBlackRings(Realm realm, FieldType face)
    {
        CreateFaceRing(realm, "4", 4, Color.BLACK, face);
        CreateFaceRing(realm, "3", 3, Color.BLACK, face);
    }
    public static void CreateWhiteRings(Realm realm, FieldType face)
    {
        CreateFaceRing(realm, "2", 2, Color.WHITE, face);
        CreateFaceRing(realm, "1", 1, Color.WHITE, face);
    }
    public static void CreateMissRings(Realm realm, FieldType face)
    {
        CreateFaceRing(realm, "M", 0, Color.WHITE, face);
    }

    public static void CreateTraditionalRings(Realm realm, FieldType face)
    {
        CreateFaceRing(realm, "6", 6, Color.YELLOW, face);
        CreateFaceRing(realm, "5", 5, Color.YELLOW, face);
        CreateFaceRing(realm, "4", 4, Color.BLACK, face);
        CreateFaceRing(realm, "3", 3, Color.BLACK, face);
        CreateFaceRing(realm, "2", 2, Color.BLACK, face);
        CreateFaceRing(realm, "1", 1, Color.BLACK, face);
    }


    public static FaceRing CreateFaceRing(Realm realm,
                                        String symbol,
                                        int value,
                                        int color,
                                        FieldType faceType)
    {
        FaceRing newRing = realm.createObject(FaceRing.class, TargetDataModule.UUIDGenerator.generate().toString());
        newRing.setSymbol(symbol);
        newRing.setValue(value);
        newRing.setColor(color);
        newRing.setFaceType(faceType);
        faceType.getFaceRings().add(newRing);
        return newRing;
    }



}
