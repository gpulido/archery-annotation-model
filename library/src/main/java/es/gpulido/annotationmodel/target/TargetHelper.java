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

import es.gpulido.annotationmodel.category.Distance;
import io.realm.Realm;

/**
 * Created by ggpt on 1/9/2015.
 */
@SuppressWarnings("SpellCheckingInspection")
public class TargetHelper {

    public static void CreateTargets(Realm realm) {
        createTarget(realm, Distance.NINETY_M, FaceHelper.COMPLETA_122, 6);
        createTarget(realm, Distance.SEVENTY_M, FaceHelper.COMPLETA_122, 6);
        createTarget(realm, Distance.SIXTY_M, FaceHelper.COMPLETA_122, 6);
        createTarget(realm, Distance.FIFTY_M, FaceHelper.COMPLETA_122, 6);
        createTarget(realm, Distance.FORTY_M, FaceHelper.COMPLETA_122, 6);
        createTarget(realm, Distance.THIRTY_M, FaceHelper.COMPLETA_122, 6);
        createTarget(realm, Distance.FIFTY_M, FaceHelper.REDUCIDA_80_5, 6);
        createTarget(realm, Distance.FORTY_M, FaceHelper.REDUCIDA_80_5, 6);
        createTarget(realm, Distance.THIRTY_M, FaceHelper.REDUCIDA_80_5, 6);
        createTarget(realm, Distance.THIRTY_M, FaceHelper.COMPLETA_80, 6);
        createTarget(realm, Distance.EIGHTEEN_M, FaceHelper.REDUCIDA_80_5, 6);
        createTarget(realm, Distance.TWENTY_FIVE_M, FaceHelper.REDUCIDA_60_TRIPLE, 6);
        createTarget(realm, Distance.TWENTY_FIVE_M, FaceHelper.REDUCIDA_60_TRIPLE_NO_X, 6);
        createTarget(realm, Distance.EIGHTEEN_M, FaceHelper.COMPLETA_40, 6);
        createTarget(realm, Distance.THIRTY_M, FaceHelper.REDUCIDA_40_TRIPLE, 6);
        createTarget(realm, Distance.EIGHTEEN_M, FaceHelper.REDUCIDA_40_TRIPLE, 6);
        //createTarget(realm, Distance.EIGHTEEN_M, FaceHelper.REDUCIDA_40_TRIPLE, 6);

        createTarget(realm, Distance.EIGHT_M, FaceHelper.FIELD_20_TRIPLE, 6, true);
        createTarget(realm, Distance.EIGHTEEN_M, FaceHelper.FIELD_40, 6, true);
        createTarget(realm, Distance.TWENTY_FIVE_M, FaceHelper.FIELD_60, 6, true);
        createTarget(realm, Distance.THIRTY_M, FaceHelper.FIELD_80, 6, true);

        createTarget(realm, Distance.TWENTY_FIVE_M, FaceHelper.REDUCIDA_80_5, 6);



        createEspiralTarget(realm,  FaceHelper.ESPIRAL_122_10);
        createEspiralTarget(realm,  FaceHelper.ESPIRAL_122_9);
        createEspiralTarget(realm,  FaceHelper.ESPIRAL_122_8);

    }

    public static void createTarget(Realm realm, @Distance.Distances String distance, @FaceHelper.Faces String face, int numArrows)
    {
        createTarget(realm, distance, face, numArrows, false);
    }

    public static void createTarget(Realm realm, @Distance.Distances String distance, @FaceHelper.Faces String face, int numArrows, boolean isField)
    {
        Target target = realm.createObject(Target.class, generateTargetName(face, numArrows, distance));
        target.setDistance(Distance.getByName(realm, distance));
        AsignDefaultArrowFieldDefinitionsForTarget(realm, target, FaceHelper.getFaceTypeByName(realm, face), 1, numArrows);
        if(isField)
            AsignDefaultTotalsFieldDefinitionsForTraditionalTarget(realm, target);
        else
            AsignDefaultTotalsFieldDefinitionsForTarget(realm, target);
    }

    private static String generateTargetName(@FaceHelper.Faces String face, int numArrows, @Distance.Distances String distance)
    {
        return face + "_" + numArrows + "_" + distance;
    }

    private static void createEspiralTarget(Realm realm, @FaceHelper.Faces String face){
        FieldType defaultMinus10 = realm.where(FieldType.class).equalTo("name", FieldTypeHelper.DEFAULT_MINUS10_TYPE).findFirst();
        FieldType defaultMinus2 = realm.where(FieldType.class).equalTo("name", FieldTypeHelper.DEFAULT_MINUS2_TYPE).findFirst();

        Target target = realm.createObject(Target.class, generateTargetName(face, 3, Distance.EIGHTEEN_M));
        target.setDistance(Distance.getByName(realm, Distance.EIGHTEEN_M));
        AsignDefaultArrowFieldDefinitionsForTarget(realm, target, FaceHelper.getFaceTypeByName(realm, face), 1, 3);

        target.getFieldDefinitions().add(FieldDefinition.findOrCreate(realm, "uso_4", "Uso 4", defaultMinus2,  10));
        target.getFieldDefinitions().add(FieldDefinition.findOrCreate(realm, "salta_10", "Salta 10", defaultMinus10, 11));
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.TOTAL_ESPIRAL));
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.PENALTY_ESPIRAL));

    }

    private static void AsignDefaultArrowFieldDefinitionsForTarget(Realm realm, Target target, FieldType fieldType, int initArrow, int endArrow) {
        for (int i = initArrow; i <= endArrow; i++) {
            String name = "arrow_" + i; 
            String description = "Arrow " + i;
            target.getFieldDefinitions().add(FieldDefinition.findOrCreate(realm, name, description, fieldType, i));
        }
    }

    private static void AsignDefaultTotalsFieldDefinitionsForTarget(Realm realm, Target target) {
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.TOTAL_6_ARROWS));
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.TOTAL_X_6_ARROWS));
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.TOTAL_10_6_ARROWS));
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.TOTAL_M_6_ARROWS));
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.MEDIA_6_ARROWS));
    }


    public static void AsignDefaultTotalsFieldDefinitionsForTraditionalTarget(Realm realm, Target target) {
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.TOTAL_6_ARROWS));
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.TOTAL_6_6_ARROWS));
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.TOTAL_5_6_ARROWS));
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.TOTAL_M_6_ARROWS));
        target.getTotalDefinitions().add(TotalDefinition.getByName(realm, TotalDefinitionHelper.MEDIA_6_ARROWS));
    }


}
