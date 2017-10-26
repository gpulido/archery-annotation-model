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

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;

import java.util.List;
import java.util.Random;

import es.gpulido.annotationmodel.annotation.Contender;
import io.realm.Realm;
import io.realm.annotations.RealmModule;
import timber.log.Timber;

/**
 * Created by GGPT on 27/03/2017.
 */

@RealmModule(library = false, classes = {Match.class, Set.class, SetAnnotation.class, SetDefinition.class, MatchContender.class, Contender.class})
public class PlayoffDataModule {

    public static RandomBasedGenerator UUIDGenerator = Generators.randomBasedGenerator(new Random(1));

    public static void resetGenerator()
    {
        UUIDGenerator = Generators.randomBasedGenerator(new Random(1));
    }

    public static void createDefaultModuleData(Realm realm)
    {
        Timber.i("Creating PlayOff Data");
    }



}
