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

package es.gpulido.annotationmodel;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Function;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;

import java.util.Random;

import es.gpulido.annotationmodel.annotation.Annotation;
import es.gpulido.annotationmodel.annotation.AnnotationDataModule;
import es.gpulido.annotationmodel.annotation.AnnotationTotalValue;
import es.gpulido.annotationmodel.annotation.AnnotationValue;
import es.gpulido.annotationmodel.annotation.Contender;
import es.gpulido.annotationmodel.annotation.End;
import es.gpulido.annotationmodel.annotation.Letter;
import es.gpulido.annotationmodel.annotation.Parapet;
import es.gpulido.annotationmodel.annotation.Round;
import es.gpulido.annotationmodel.annotation.Tournament;
import es.gpulido.annotationmodel.archer.Archer;
import es.gpulido.annotationmodel.archer.ArcherDataModule;
import es.gpulido.annotationmodel.category.CategoryDataModule;
import es.gpulido.annotationmodel.competition.CompetitionDataModule;
import es.gpulido.annotationmodel.serie.SerieDataModule;
import es.gpulido.annotationmodel.target.TargetDataModule;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by gpt on 22/05/16.
 */

public class BasicDataModule {

    public static  RandomBasedGenerator UUIDGenerator = Generators.randomBasedGenerator(new Random(1));

    public static void resetGenerator()
    {UUIDGenerator = Generators.randomBasedGenerator(new Random(1));
    }

    public static void createDefaultModuleData(Realm realm)
    {
        Timber.i("Creating DefaultData");
        CategoryDataModule.createDefaultModuleData(realm);
        TargetDataModule.createDefaultModuleData(realm);
        SerieDataModule.createDefaultModuleData(realm);
        ArcherDataModule.createDefaultModuleData(realm);
        CompetitionDataModule.createDefaultModuleData(realm);

        AnnotationDataModule.createDefaultModuleData(realm);
        //TimerDataModule
        //TrainingDataModule


    }


    public static void PurgeRealm(Realm realm, Function<Boolean, Void> progressCallBack) {

        progressCallBack.apply(true);
        realm.executeTransactionAsync(realm1 -> {
            RealmResults<Archer> deletedArchers = realm1.where(Archer.class).equalTo("deleted", true).findAll();
            Stream.of(deletedArchers).flatMap(a -> Stream.of(realm1.where(Contender.class).equalTo("archer.UUID", a.getUUID()).findAll())).forEach(c -> c.setArcher(null));

            RealmResults<Tournament> deletedTournaments = realm1.where(Tournament.class).equalTo("deleted", true).findAll();
            Stream.of(deletedTournaments).flatMap(t -> Stream.of(realm1.where(Contender.class).equalTo("tournament.UUID", t.getUUID()).findAll())).forEach(c -> c.setDeleted(true));

            RealmResults<Parapet> deletedParapets = realm1.where(Parapet.class).equalTo("tournament.deleted",true).findAll();
            RealmResults<Letter> deletedLetters = realm1.where(Letter.class).equalTo("parapet.tournament.deleted",true).findAll();
            RealmResults<Round> deletedRounds = realm1.where(Round.class).equalTo("tournament.deleted",true).findAll();
            RealmResults<End> deletedEntries = realm1.where(End.class).equalTo("round.tournament.deleted",true).findAll();


            RealmResults<AnnotationValue> deletedAnnotationValues = realm1.where(AnnotationValue.class).equalTo("annotation.contender.deleted", true).findAll();
            RealmResults<AnnotationTotalValue> deletedAnnotationTotalValues = realm1.where(AnnotationTotalValue.class).equalTo("annotation.contender.deleted", true).findAll();
            RealmResults<Annotation> deletedAnnotations = realm1.where(Annotation.class).equalTo("contender.deleted", true).findAll();
            RealmResults<Contender> deletedContenders = realm1.where(Contender.class).equalTo("deleted", true).findAll();

            deletedAnnotationTotalValues.deleteAllFromRealm();
            deletedAnnotationValues.deleteAllFromRealm();
            deletedAnnotations.deleteAllFromRealm();
            deletedLetters.deleteAllFromRealm();
            deletedParapets.deleteAllFromRealm();
            deletedEntries.deleteAllFromRealm();
            deletedRounds.deleteAllFromRealm();
            deletedContenders.deleteAllFromRealm();
            deletedTournaments.deleteAllFromRealm();
            deletedArchers.deleteAllFromRealm();
        },() -> progressCallBack.apply(false));


    }
}
