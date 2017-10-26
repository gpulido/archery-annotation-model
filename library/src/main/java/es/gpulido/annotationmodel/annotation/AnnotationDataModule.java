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

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;

import java.util.Random;

import es.gpulido.annotationmodel.management.AnnotationServer;
import io.realm.Realm;
import io.realm.annotations.RealmModule;
import timber.log.Timber;

/**
 * Created by gpt on 23/05/16.
 */
@RealmModule(library = true, classes = {Annotation.class, AnnotationValue.class, AnnotationTotalValue.class, Annotator.class, ContenderTotalValue.class, Contender.class, End.class, Letter.class,
        Parapet.class, Round.class,  Tournament.class, AnnotationServer.class})
public class AnnotationDataModule {

    public static RandomBasedGenerator UUIDGenerator = Generators.randomBasedGenerator(new Random(1));

    public static void resetGenerator()
    {
        UUIDGenerator = Generators.randomBasedGenerator(new Random(1));
    }

    public static void createDefaultModuleData(Realm realm)
    {
        Timber.i("Creating Annotation Data");
        //Annotator.CreateLocalAnnotator(realm);
       // AnnotationServer.CreateLocalServerData(realm, android.os.Build.MODEL, 9001);
    }
}
