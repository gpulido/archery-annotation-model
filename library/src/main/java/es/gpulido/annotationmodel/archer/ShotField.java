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

package es.gpulido.annotationmodel.archer;

import java.util.ArrayList;
import java.util.List;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.category.Distance;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.Sort;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ggpt on 5/25/2015.
 */
@SuppressWarnings("unused")
public class ShotField extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;

    private String name;
    private int numParapetsMax;

    private RealmList<Distance> distances;

    private int numMaxContendersByParapet;
    private double longitude;
    private double latitude;
    private boolean indoor;

    public ShotField(){}

    public ShotField(String name, int numParapetsMax, List<Distance> distances, int numMaxContendersByParapet)
    {
        this.UUID = ArcherDataModule.UUIDGenerator.generate().toString();
        this.name = name;
        this.numParapetsMax = numParapetsMax;
        this.numMaxContendersByParapet = numMaxContendersByParapet;
        this.distances.addAll(distances);

    }

    public List<String> getFieldLetters(){

        List<String> letters = new ArrayList<>();
        for(int i = 0; i< getNumMaxContendersByParapet(); i++)
        {
            if (i == 0)
                letters.add("A");
            if (i == 1 )
                letters.add("B");
            if (i == 2)
                letters.add("C");
            if (i == 3)
                letters.add("D");
        }

        return letters;
    }


    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getNumParapetsMax() {
        return numParapetsMax;
    }

    public void setNumParapetsMax(int numParapetsMax) {
        this.numParapetsMax = numParapetsMax;
    }

    public int getDistanceMax() {
        return distances.sort("meters", Sort.DESCENDING).first().getMeters();
    }

    public int getNumMaxContendersByParapet() {
        return numMaxContendersByParapet;
    }

    public void setNumMaxContendersByParapet(int numMaxContendersByParapet) {
        this.numMaxContendersByParapet = numMaxContendersByParapet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public RealmList<Distance> getDistances() {
        return distances;
    }

    public void setDistances(RealmList<Distance> distances) {
        this.distances = distances;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }
}
