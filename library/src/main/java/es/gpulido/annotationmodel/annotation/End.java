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

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.helper.RealmHelper;
import es.gpulido.annotationmodel.management.ManagementDataModule;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 13/11/14.
 */
@SuppressWarnings("unused")
public class End extends RealmObject implements IHasPrimarykey{
    @PrimaryKey
    private String UUID;
    private int order;

    private Round round;


    @LinkingObjects("end")
    public final RealmResults<Annotation> annotations = null;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }



    public boolean isLocalCompleted(boolean showOnlyLocalAnnotator)
    {
        try (Realm realm = RealmHelper.getActiveInstance()) {

            RealmQuery<AnnotationValue> annotationValueRealmQuery =
                    realm.where(AnnotationValue.class)
                    .equalTo("annotation.end.UUID", UUID)
                    .equalTo("annotation.contender.state", Contender.ACTIVE)
                    .equalTo("stringFieldValue", "");

            if (showOnlyLocalAnnotator)
                annotationValueRealmQuery = annotationValueRealmQuery.equalTo("annotation.contender.letter.parapet.annotator.UUID", ManagementDataModule.getActiveServerAnnotatorUUID());

            return annotationValueRealmQuery.count() == 0;


        }
    }

    public RealmResults<Parapet> incompleteParapets(boolean showOnlyLocalAnnotator)
    {
        try (Realm realm = RealmHelper.getActiveInstance()) {

            RealmQuery<Annotation> annotationRealmQuery = realm.where(Annotation.class)
                    .equalTo("end.UUID", getUUID())
                    .equalTo("contender.state", Contender.ACTIVE)
                    .equalTo("isCompleted", false);
            if (showOnlyLocalAnnotator)
                annotationRealmQuery = annotationRealmQuery.equalTo("contender.letter.parapet.annotator.UUID", ManagementDataModule.getActiveServerAnnotatorUUID());

            RealmResults<Annotation> annotations = annotationRealmQuery.findAll();

            List<Parapet> parapets = Stream.of(annotations).map(a -> a.getContender().getLetter().getParapet()).distinct().collect(Collectors.toList());
            RealmQuery<Parapet> parapetQuery = realm.where(Parapet.class);
            boolean first = true;
            if (parapets.size() == 0)
                return parapetQuery.equalTo("UUID", "").findAll();

            for(Parapet parapet: parapets)
            {
                if (!first)
                    parapetQuery = parapetQuery.or();
                parapetQuery = parapetQuery.equalTo("UUID", parapet.getUUID());
                first = false;
            }
            return parapetQuery.findAll();
        }

    }


    @SuppressLint("NewApi")
    public boolean isCompleted()
    {
        try (Realm realm = RealmHelper.getActiveInstance()) {
            return realm.where(AnnotationValue.class).equalTo("annotation.end.UUID", UUID)
                    .equalTo("stringFieldValue", "").count() == 0;
        }
    }
    @SuppressLint("NewApi")
    public boolean isPartialCompleted()
    {
        try (Realm realm = RealmHelper.getActiveInstance()) {
            return realm.where(AnnotationValue.class).equalTo("annotation.end.UUID", UUID)
                    .equalTo("stringFieldValue", "").count() != 0;
        }
    }
    //TODO: calculate properly this value
    public int getAbsoluteOrder() {
        return getRound().entries.size()*getRound().getOrder() + getOrder();
    }
}
