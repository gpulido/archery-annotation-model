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

package es.gpulido.annotationmodel.training;

import es.gpulido.annotationmodel.Interfaces.ICanBeDeleted;
import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.category.Division;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by GGPT on 1/9/2017.
 */

public class BowConfiguration extends RealmObject implements IHasPrimarykey, ICanBeDeleted {

    @PrimaryKey
    private String UUID;
    private boolean deleted;
    private String name;
    private Division division;

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void delete(Realm realm) {
        realm.beginTransaction();
        setDeleted(true);
        realm.commitTransaction();
    }

    @Override
    public void undoDelete(Realm realm) {
        realm.beginTransaction();
        setDeleted(false);
        realm.commitTransaction();
    }

    @Override
    public String getUUID() {
        return this.UUID;
    }
    public void setUUID(String uuid)
    {
        this.UUID = uuid;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division bowType) {
        this.division = bowType;
    }
}
