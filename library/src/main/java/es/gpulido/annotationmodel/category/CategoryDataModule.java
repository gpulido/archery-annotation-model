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

package es.gpulido.annotationmodel.category;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;

import java.util.Random;

import io.realm.Realm;
import io.realm.annotations.RealmModule;
import timber.log.Timber;

/**
 * Created by gpt on 22/05/16.
 */
@RealmModule(library = true, classes = {ArcheryClass.class, Category.class, Discipline.class, ClassGender.class, Distance.class, Division.class, Level.class})
public class CategoryDataModule {

    public static  RandomBasedGenerator UUIDGenerator = Generators.randomBasedGenerator(new Random(1));

    public static void resetGenerator()
    {UUIDGenerator = Generators.randomBasedGenerator(new Random(1));
    }

    public static void createDefaultModuleData(Realm realm)
    {
        Timber.i("Creating Category Data");
        Level.createDefaultData(realm);
        Division.createDefaultData(realm);
        Distance.createDefaultData(realm);
        Discipline.createDefaultData(realm);
        ClassGender.createDefaultData(realm);
        ArcheryClass.createDefaultData(realm);
        Category.createDefaultData(realm);

    }
}
