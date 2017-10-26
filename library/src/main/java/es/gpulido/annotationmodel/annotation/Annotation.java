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

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.helper.RealmHelper;
import es.gpulido.annotationmodel.helper.TournamentHelper;
import es.gpulido.annotationmodel.target.FieldDefinition;
import es.gpulido.annotationmodel.target.Target;
import es.gpulido.annotationmodel.target.TotalDefinition;
import es.gpulido.annotationmodel.target.FieldType;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 13/11/14.
 */
@SuppressWarnings("unused")
public class Annotation extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;

    private Tournament tournament;
    private End end;
    private Target target;
    @LinkingObjects("annotation")
    public final RealmResults<AnnotationValue> annotationValues = null;
    @LinkingObjects("annotation")
    public final RealmResults<AnnotationTotalValue> annotationTotalValues = null;

    private boolean isCompleted;

    private Contender contender;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public End getEnd() {
        return end;
    }

    public void setEnd(End end) {
        this.end = end;
    }


    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public Contender getContender() {
        return contender;
    }

    public void setContender(Contender contender) {
        this.contender = contender;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }


    public void updateIsCompleted()
    {
        boolean newCompleted = annotationValues.where().isEmpty("stringFieldValue").count() == 0;
        setIsCompleted(newCompleted);
    }

    public boolean isLocalCompleted()
    {
        return isCompleted ||
                getContender().getLetter().getParapet().getAnnotator() == null ||
                !getContender().getLetter().getParapet().getAnnotator().isLocalAnnotator();
    }


    public void updateTotals() {
        //Timber.d(getContender().getLetter().getParapet().getOrder()+ getContender().getLetter().getLetterSymbol() + " " + getEntry().getRound().getOrder() + " " +getEntry().getOrder());
        Map<String, Long> extClosure = getExtendedClosure();
        //TODO: there is a bug on realm that avoids to use the iterator.
        //https://github.com/realm/realm-java/issues/640
//        OrderedRealmCollection<AnnotationTotalValue> snapshot = annotationTotalValues.createSnapshot();
//        for (int i = 0;i< snapshot.size(); i++) {
//            annotationTotalValues.get(i).calculateTotalValue(extClosure);
//        }
//
        for (int i = 0; i < annotationTotalValues.size(); i++) {
            annotationTotalValues.get(i).calculateTotalValue(extClosure);
        }

    }

    /***
     * Creates the calculation context with the values to be used by the expression calculator
     * @return a map with the variables filled
     */
    public  Map<String, Long> getExtendedClosure() {

        //ArrayMap<String, Double> extendedClosure = new ArrayMap<>();
        Map<String, Long> extendedClosure2 = Stream.of(annotationValues)
                .map(AnnotationValue::GetAnnotationValueSymbol)
                .filter(s -> !s.equals(""))
                .map(s -> "total_" + s.toLowerCase())
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        Map<String, Long> extendedClosure3 =
                Stream.of(annotationValues).collect(Collectors.toMap(e-> e.getFieldDefinition().getName(),e2 -> (long)e2.getDoubleFieldValue()));
        extendedClosure2.putAll(extendedClosure3);
        return  extendedClosure2;

//        for (AnnotationValue av : getAnnotationValues()) {
//            //Totals for values
//            String symbol = av.GetAnnotationValueSymbol().toLowerCase();
//            if (!symbol.equals("")) {
//                String totalName = "total_" + symbol;
//                if (!extendedClosure.containsKey(totalName)) {
//                    extendedClosure.put(totalName, (double) 0);
//                }
//                extendedClosure.put(totalName, extendedClosure.get(totalName) + 1);
//                //Now put the values of the fields
//            }
//            extendedClosure.put(av.getFieldDefinition().getName(), av.getDoubleFieldValue());
//        }
//
//        return extendedClosure;
    }

    public @Nullable FieldType getAnnotationBaseFace()
    {
        return getTarget().getBaseFace();
    }


    public AnnotationTotalValue getAnnotationMainTotalValue()
    {
        return annotationTotalValues.where().equalTo("totalDefinition.isMainTotal", true).findFirst();
    }

    public Double getAnnotationMainTotal()
    {
        return annotationTotalValues.where().equalTo("totalDefinition.isMainTotal", true).findFirst().getDoubleFieldValue();
    }

    public RealmResults<AnnotationTotalValue> getAnnotationNotMainTotalValues() {
        return annotationTotalValues.where().equalTo("totalDefinition.isMainTotal", false).findAll();
    }


    public void deleteRelatedObjectsFromRealm()
    {
        annotationValues.deleteAllFromRealm();
        annotationTotalValues.deleteAllFromRealm();
    }
}

