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

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import es.gpulido.annotationmodel.target.FieldTypeHelper;
import es.gpulido.annotationmodel.target.TotalDefinition;
import es.gpulido.annotationmodel.target.FieldType;
import io.realm.Realm;

/**
 * Created by gpt on 21/07/16.
 */
@SuppressWarnings("SpellCheckingInspection")
public class TotalDefinitionHelper {
    @StringDef({
            TOTAL_6_ARROWS,
            TOTAL_X_6_ARROWS,
            TOTAL_10_6_ARROWS,
            TOTAL_M_6_ARROWS,
            TOTAL_6_6_ARROWS,
            TOTAL_5_6_ARROWS,
            MEDIA_6_ARROWS,
            TOTAL_ESPIRAL,
            PENALTY_ESPIRAL,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TotalTypes {
    }
    public static final String TOTAL_6_ARROWS = "total_6_flechas";
    public static final String TOTAL_X_6_ARROWS = "total_X_6_flechas";
    public static final String TOTAL_10_6_ARROWS = "total_10_flechas";
    public static final String TOTAL_M_6_ARROWS = "total_M_6_flechas";
    public static final String TOTAL_6_6_ARROWS = "total_6_6_flechas";
    public static final String TOTAL_5_6_ARROWS = "total_5_6_flechas";
    public static final String MEDIA_6_ARROWS = "media_6_flechas";

    public static final String TOTAL_ESPIRAL = "total_espiral";
    public static final String PENALTY_ESPIRAL = "penalty_espiral";


    private static final String totalExpression = "arrow_1 + arrow_2 + arrow_3 + arrow_4 + arrow_5 + arrow_6";
    private static final String expressionVariables = "arrow_1,arrow_2,arrow_3,arrow_4,arrow_5,arrow_6,total_x,total_10,total_6,total_5,total_m";

    // static String expressionVariablesForTraditional = "arrow_1,arrow_2,arrow_3,arrow_4,arrow_5,arrow_6,total_6,total_m";
    private static final String totalX = "total_x";
    private static final String total10 = "total_x + total_10";
    private static final String total6 = "total_6";
    private static final String total5 = "total_5";
    private static final String totalM = "total_m";
    private static final String mean = "(" + totalExpression + ")/6";

    private static final String totalExpressionForEspiral = "arrow_1 + arrow_2 + arrow_3 + (-1*uso_4) + (-1*salta_10)";
    private static final String espiralPenalty = "uso_4 + salta_10";
    private static final String expressionVariablesForEspiral = "arrow_1,arrow_2,arrow_3,uso_4,salta_10,total_m";


    public static void CreateTotals(Realm realm) {
        FieldType defaultNumber = realm.where(FieldType.class).equalTo("name", FieldTypeHelper.DEFAULT_NUMBER_TYPE).findFirst();
        new TotalDefinition.TotalDefinitionBuilder(realm).withName(TOTAL_6_ARROWS)
                .withDescription("Total").withShortDescription("T").withFieldType(defaultNumber)
                .withOrder(0).withClassificationOrder(1).withClassificationSign(1)
                .withValueExpression(totalExpression).withExpressionVariables(expressionVariables)
                .withAggregateExpression(TotalDefinition.SUM).withMainTotal(true)
                .withShowInAnnotation(true).build();
        new TotalDefinition.TotalDefinitionBuilder(realm).withName(TOTAL_X_6_ARROWS)
                .withDescription("Total_X").withShortDescription("X").withFieldType(defaultNumber)
                .withOrder(10).withClassificationOrder(30).withClassificationSign(1)
                .withValueExpression(totalX).withExpressionVariables(expressionVariables)
                .withAggregateExpression(TotalDefinition.SUM).withMainTotal(false)
                .withShowInAnnotation(true).build();
        new TotalDefinition.TotalDefinitionBuilder(realm).withName(TOTAL_10_6_ARROWS)
                .withDescription("Total_10").withShortDescription("10").withFieldType(defaultNumber)
                .withOrder(20).withClassificationOrder(20).withClassificationSign(1)
                .withValueExpression(total10).withExpressionVariables(expressionVariables)
                .withAggregateExpression(TotalDefinition.SUM).withMainTotal(false)
                .withShowInAnnotation(true).build();
        new TotalDefinition.TotalDefinitionBuilder(realm).withName(TOTAL_M_6_ARROWS)
                .withDescription("Total_M").withShortDescription("M").withFieldType(defaultNumber)
                .withOrder(50).withClassificationOrder(100).withClassificationSign(-1)
                .withValueExpression(totalM).withExpressionVariables(expressionVariables)
                .withAggregateExpression(TotalDefinition.SUM).withMainTotal(false)
                .withShowInAnnotation(true).build();
        new TotalDefinition.TotalDefinitionBuilder(realm).withName(TOTAL_6_6_ARROWS)
                .withDescription("Total_6").withShortDescription("6").withFieldType(defaultNumber)
                .withOrder(30).withClassificationOrder(40).withClassificationSign(1)
                .withValueExpression(total6).withExpressionVariables(expressionVariables)
                .withAggregateExpression(TotalDefinition.SUM).withMainTotal(false)
                .withShowInAnnotation(true).build();
        new TotalDefinition.TotalDefinitionBuilder(realm).withName(TOTAL_5_6_ARROWS)
                .withDescription("Total_5").withShortDescription("5").withFieldType(defaultNumber)
                .withOrder(40).withClassificationOrder(50).withClassificationSign(1)
                .withValueExpression(total5).withExpressionVariables(expressionVariables)
                .withAggregateExpression(TotalDefinition.SUM).withMainTotal(false)
                .withShowInAnnotation(true).build();
        new TotalDefinition.TotalDefinitionBuilder(realm).withName(MEDIA_6_ARROWS)
                .withDescription("Media").withShortDescription("s").withFieldType(defaultNumber)
                .withOrder(60).withClassificationOrder(-1).withClassificationSign(0)
                .withValueExpression(mean).withExpressionVariables(expressionVariables)
                .withAggregateExpression(TotalDefinition.MEAN).withMainTotal(false)
                .withShowInAnnotation(false).build();
        new TotalDefinition.TotalDefinitionBuilder(realm).withName(TOTAL_ESPIRAL)
                .withDescription("Total").withShortDescription("T").withFieldType(defaultNumber)
                .withOrder(0).withClassificationOrder(10).withClassificationSign(1)
                .withValueExpression(totalExpressionForEspiral)
                .withExpressionVariables(expressionVariablesForEspiral)
                .withAggregateExpression(TotalDefinition.SUM).withMainTotal(true)
                .withShowInAnnotation(true).build();
        new TotalDefinition.TotalDefinitionBuilder(realm).withName(PENALTY_ESPIRAL)
                .withDescription("Penalización").withShortDescription("P")
                .withFieldType(defaultNumber).withOrder(10).withClassificationOrder(20)
                .withClassificationSign(-1)
                .withValueExpression(espiralPenalty)
                .withExpressionVariables(expressionVariablesForEspiral)
                .withAggregateExpression(TotalDefinition.SUM).withMainTotal(false)
                .withShowInAnnotation(true).build();
    }


}
