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
public class Round extends RealmObject implements IHasPrimarykey {

    @PrimaryKey
    private String UUID;

    private Tournament tournament;
    //private String name;
    private int order;
    @LinkingObjects("round")
    public final RealmResults<End> entries = null;

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

    public boolean isRoundCompleted()
    {
        try (Realm realm = RealmHelper.getActiveInstance()){
            return realm.where(Annotation.class)
                    .equalTo("end.round.UUID", UUID)
                    .equalTo("isCompleted", false).findAll().size() == 0;
        }

    }

}
