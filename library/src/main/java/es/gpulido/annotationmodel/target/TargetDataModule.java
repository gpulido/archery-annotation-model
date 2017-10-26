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

package es.gpulido.annotationmodel.target;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;

import java.util.Random;

import io.realm.Realm;
import io.realm.annotations.RealmModule;
import timber.log.Timber;

/**
 * Created by gpt on 22/05/16.
 */
@RealmModule(library = true, classes = {FaceRing.class, FieldType.class, FieldDefinition.class, TotalDefinition.class, Target.class})
public class TargetDataModule {

    public static  RandomBasedGenerator UUIDGenerator = Generators.randomBasedGenerator(new Random(1000));

    public static void resetGenerator()
    {UUIDGenerator = Generators.randomBasedGenerator(new Random(1000));
    }

    public static void createDefaultModuleData(Realm realm)
    {
        Timber.i("Creating Target Data");
        FieldTypeHelper.CreateStringType(realm);
        FieldTypeHelper.CreateNumberType(realm);
        FieldTypeHelper.CreateDoubleType(realm);
        FieldTypeHelper.CreateMinus10DoubleType(realm);
        FieldTypeHelper.CreateMinus2DoubleType(realm);
        FaceHelper.CreateFaces(realm);
        TotalDefinitionHelper.CreateTotals(realm);
        TargetHelper.CreateTargets(realm);

    }
}
