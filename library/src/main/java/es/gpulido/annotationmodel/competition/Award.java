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

package es.gpulido.annotationmodel.competition;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 17/08/15.
 */
@SuppressWarnings("unused")
public class Award extends RealmObject {

    @PrimaryKey
    private String UUID;
    //Name of the award ("insignia blanca")
    @Index
    private String name;
    //min total value to obtain the award
    @Index
    private int minTotalValue;
    //Description of the award
    private String description;
    //color name of the award
    private String colorName;
    //color of the award
    private int color;
    //total to be used as count
    private String totalNameString;

    //True if the award is part of a group where the bigger is the one to be obtain
    private boolean groupedAward;

    public Award(){}

    public Award(String name, String description, String colorName,int color, String totalNameString, int minTotalValue, boolean groupedAward)
    {
        this.setUUID(CompetitionDataModule.UUIDGenerator.generate().toString());

        this.setName(name);
        this.setColorName(colorName);
        this.setColor(color);
        this.setDescription(description);
        this.setTotalNameString(totalNameString);
        this.setMinTotalValue(minTotalValue);
        this.setGroupedAward(groupedAward);
    }

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

    public String getTotalNameString() {
        return totalNameString;
    }

    public void setTotalNameString(String totalNameString) {
        this.totalNameString = totalNameString;
    }

    public int getMinTotalValue() {
        return minTotalValue;
    }

    public void setMinTotalValue(int minTotalValue) {
        this.minTotalValue = minTotalValue;
    }

    public boolean isGroupedAward() {
        return groupedAward;
    }

    public void setGroupedAward(boolean groupedAward) {
        this.groupedAward = groupedAward;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }
}
