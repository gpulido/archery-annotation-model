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

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;

import java.util.Random;

import io.realm.Realm;
import io.realm.annotations.RealmModule;
import timber.log.Timber;

/**
 * Created by gpt on 22/05/16.
 */
@RealmModule(library = true, classes = {Archer.class, Club.class, Gender.class, ShotField.class})
public class ArcherDataModule {

    public static  RandomBasedGenerator UUIDGenerator = Generators.randomBasedGenerator(new Random(1000));

    public static void resetGenerator()
    {UUIDGenerator = Generators.randomBasedGenerator(new Random(1000));
    }

    public static void createDefaultModuleData(Realm realm)
    {
        Timber.i("Creating Archer Data");
        Gender.createGenderDefaultData(realm);
    }
}
