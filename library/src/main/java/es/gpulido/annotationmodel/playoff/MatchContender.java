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

package es.gpulido.annotationmodel.playoff;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.annotation.Contender;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by GGPT on 10/04/2017.
 */

public class MatchContender extends RealmObject implements IHasPrimarykey {
    @PrimaryKey
    private String UUID;
    private Contender contender;


    @Override
    public String getUUID() {
        return UUID;
    }
}
