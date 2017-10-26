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
import android.support.v4.util.ArrayMap;

import com.annimon.stream.Stream;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 13/11/14.
 */
@SuppressWarnings("unused")
public class TotalDefinition extends RealmObject implements IHasPrimarykey {


    @StringDef({SUM, MEAN})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AgregateType{}

    public static final  String SUM = "sum";
    public static final  String MEAN = "mean";

    @PrimaryKey
    private String UUID;
    @Index
    private String name;

    private boolean isMainTotal;
    private boolean showInAnnotation;

    private String description;
    private String shortDescription;
    private FieldType fieldType;

    private int order;
    private String valueExpression;
    private String expressionVariables;
    private @AgregateType String aggregationExpression;

    private int classificationOrder;
    private int classificationSign;

    @Override
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


    public String getValueExpression() {
        return valueExpression;
    }

    public void setValueExpression(String valueExpression) {
        this.valueExpression = valueExpression;
    }

    public String getExpressionVariables() {
        return expressionVariables;
    }

    public void setExpressionVariables(String expressionVariables) {
        this.expressionVariables = expressionVariables;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public @AgregateType String getAggregationExpression() {
        return aggregationExpression;
    }

    public void setAggregationExpression(@AgregateType String aggregationExpression) {
        this.aggregationExpression = aggregationExpression;
    }

    public boolean isMainTotal() {
        return isMainTotal;
    }

    public void setMainTotal(boolean mainTotal) {
        isMainTotal = mainTotal;
    }

    public int getClassificationOrder() {
        return classificationOrder;
    }

    public void setClassificationOrder(int classificationOrder) {
        this.classificationOrder = classificationOrder;
    }

    public int getClassificationSign() {
        return classificationSign;
    }

    public void setClassificationSign(int classificationSign) {
        this.classificationSign = classificationSign;
    }

    public boolean isShowInAnnotation() {
        return showInAnnotation;
    }

    public void setShowInAnnotation(boolean showInAnnotation) {
        this.showInAnnotation = showInAnnotation;
    }


    public Double calculateValue(Map<String, Long> extClousure)
    {
        if (getValueExpression()== null || getValueExpression().equals(""))
            return (double)0;
        HashSet<String> expressionVariables = new HashSet<>(Arrays.asList(getExpressionVariables().split(",")));
        //We need to complete for those values that are not already there
        Map<String, Double> completeClousure = new ArrayMap<>();
        for(String var:expressionVariables)
        {
            Long value = extClousure.get(var);
            if (value == null)
                value = 0L;
            completeClousure.put(var,(double)value);

        }
        Expression e = new ExpressionBuilder(getValueExpression())
                .variables(expressionVariables)
                .build()
                .setVariables(completeClousure);
        return e.evaluate();

    }

    public static TotalDefinition getByName(Realm realm, String name)
    {
        return realm.where(TotalDefinition.class).equalTo("name",name).findFirst();
    }

    public static class TotalDefinitionBuilder
    {

        private TotalDefinition totalDefinition;
        private @TotalDefinitionHelper.TotalTypes
        String mName;
        private String mDescription;
        private String mShortDescription;
        private FieldType mFieldType;
        private int mOrder;
        private int mClassificationOrder;
        private int mClassificationSign;
        private String mValueExpression;
        private String mExpressionVariables;
        private @TotalDefinition.AgregateType String mAggregationType;
        private boolean mIsMainTotal = false;
        private boolean mShowInAnnotation = true;

        public TotalDefinitionBuilder(Realm realm)
        {
            totalDefinition = realm.createObject(TotalDefinition.class, TargetDataModule.UUIDGenerator.generate().toString());
        }

        public TotalDefinitionBuilder withName(String name)
        {
            mName = name;
            return this;
        }

        public TotalDefinitionBuilder withDescription(String description)
        {
            mDescription = description;
            return this;
        }
        public TotalDefinitionBuilder withShortDescription(String shortDescription)
        {
            mShortDescription = shortDescription;
            return this;
        }
        public TotalDefinitionBuilder withFieldType(FieldType fieldType)
        {
            mFieldType = fieldType;
            return this;
        }
        public TotalDefinitionBuilder withOrder(int order)
        {
            mOrder = order;
            return this;
        }
        public TotalDefinitionBuilder withClassificationOrder(int classificationOrder)
        {
            mClassificationOrder = classificationOrder;
            return this;
        }
        public TotalDefinitionBuilder withClassificationSign(int classificationSign)
        {
            mClassificationSign = classificationSign;
            return this;
        }
        public TotalDefinitionBuilder withValueExpression(String valueExpression)
        {
            mValueExpression = valueExpression;
            return this;
        }
        public TotalDefinitionBuilder withExpressionVariables(String expressionVariables)
        {
            mExpressionVariables = expressionVariables;
            return this;
        }

        public TotalDefinitionBuilder withAggregateExpression(@TotalDefinition.AgregateType String aggregationType)
        {
            mAggregationType = aggregationType;
            return this;

        }
        public TotalDefinitionBuilder withMainTotal(boolean isMainTotal)
        {
            mIsMainTotal = isMainTotal;
            return this;
        }
        public TotalDefinitionBuilder withShowInAnnotation(boolean showInAnnotation)
        {
            mShowInAnnotation = showInAnnotation;
            return this;
        }

        public TotalDefinition build()
        {
            totalDefinition.setName(mName);
            totalDefinition.setDescription(mDescription);
            totalDefinition.setShortDescription(mShortDescription);
            totalDefinition.setFieldType(mFieldType);
            totalDefinition.setOrder(mOrder);
            totalDefinition.setClassificationOrder(mClassificationOrder);
            totalDefinition.setClassificationSign(mClassificationSign);
            totalDefinition.setAggregationExpression(mAggregationType);
            totalDefinition.setExpressionVariables(mExpressionVariables);
            totalDefinition.setValueExpression(mValueExpression);
            totalDefinition.setMainTotal(mIsMainTotal);
            totalDefinition.setShowInAnnotation(mShowInAnnotation);
            return totalDefinition;
        }


    }





}
