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

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.helper.RealmHelper;
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
public class Parapet extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;

    private Tournament tournament;
    private int order;

    //App instance that is being used as annotator
    private Annotator annotator;

    @LinkingObjects("parapet")
    public final RealmResults<Letter> letters = null;

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Annotator getAnnotator() {
        return annotator;
    }

    public void setAnnotator(Annotator annotator) {
        this.annotator = annotator;
    }


    public void assignAnnotator(Realm realm, String annotatorUUID) {
        String parapetUUID = UUID;
        realm.executeTransactionAsync(realm1 -> {
            Parapet asyncParapet = RealmHelper.getByPrimaryKey(realm1, Parapet.class, parapetUUID);
            Annotator asyncAnnotator = RealmHelper.getByPrimaryKey(realm1, Annotator.class, annotatorUUID);
            asyncParapet.setAnnotator(asyncAnnotator);
        });
    }

    public void moveToOrder(Realm realm, int newOrder) {
         String tournamentUUID = getTournament().getUUID();
         String uuid = UUID;
         int oldOrder = order;
         realm.executeTransactionAsync(realm1 -> {
             if (newOrder > oldOrder)
             {
                 for(Parapet parapet: realm1.where(Parapet.class)
                         .equalTo("tournament.UUID", tournamentUUID)
                         .notEqualTo("UUID", uuid)
                         .between("order", oldOrder, newOrder).findAll())

                 {

                     parapet.setOrder(parapet.getOrder() - 1);
                 }

             }
             else if (newOrder < oldOrder)
             {
                 for(Parapet parapet: realm1.where(Parapet.class)
                         .equalTo("tournament.UUID", tournamentUUID)
                         .notEqualTo("UUID", uuid)
                         .between("order", newOrder, oldOrder).findAll())
                 {
                     parapet.setOrder(parapet.getOrder() + 1);
                 }
             }
             RealmHelper.getByPrimaryKey(realm1, Parapet.class, uuid).setOrder(newOrder);
         });
    }
}
