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

package es.gpulido.annotationmodel.Interfaces;

import io.realm.Realm;

/**
 * Created by gpt on 27/06/16.
 */

public interface ICanBeDeleted {

    boolean isDeleted();

    void setDeleted(boolean deleted);
    
    void delete(Realm realm);

    void undoDelete(Realm realm);
}
