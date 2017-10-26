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

package es.gpulido.annotationmodel.management;

import android.support.annotation.NonNull;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.RandomBasedGenerator;

import java.util.Random;

import es.gpulido.annotationmodel.annotation.Annotator;
import es.gpulido.annotationmodel.helper.RealmHelper;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.annotations.RealmModule;

import static es.gpulido.annotationmodel.helper.Utils.EmptyUUID;

/**
 * Created by gpt on 22/05/16.
 */
@RealmModule(library = true, classes = {AnnotationServer.class})
public class ManagementDataModule {

    public static Realm getManagementRealm()
    {
        return Realm.getInstance(getRealmConfiguration());
    }

    public static RandomBasedGenerator UUIDGenerator = Generators.randomBasedGenerator(new Random(1));

    public static void resetGenerator() {
        UUIDGenerator = Generators.randomBasedGenerator(new Random(1));
    }

    public static void createDefaultModuleData(Realm realm) {
        AnnotationServer localServer = realm.createObject(AnnotationServer.class, UUIDGenerator.generate().toString());
        localServer.setDatabaseName("Local");
        localServer.setLocal(true);

    }

    public static RealmConfiguration getRealmConfiguration() {
        return new RealmConfiguration.Builder()
                .name("management")
                .modules(new ManagementDataModule())
                .initialData(ManagementDataModule::createDefaultModuleData)
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                //.migration(new Migration())
                .build();
    }

    /**
     * Obtains the AnnotationServer
     * @param realm
     * @param annotationServer
     * @return
     */
    public static AnnotationServer getManagedAnnotationServer(Realm realm, AnnotationServer annotationServer)
    {
        return realm.where(AnnotationServer.class)
                .equalTo("serverIP", annotationServer.getserverIP())
                .equalTo("userId", annotationServer.getUserId()).findFirst();
    }


    //TODO: maybe this has to be on the RealmHelper instead
    public static void setActiveServerAnnotatorUUID(String annotatorUUID) {

        try(Realm realmManagement = getManagementRealm()) {
            AnnotationServer activeServer = RealmHelper.getActiveServer(realmManagement);
            if (activeServer != null) {
                realmManagement.beginTransaction();
                activeServer.setLocalAnnotatorUUID(annotatorUUID);
                realmManagement.commitTransaction();
            }

        }

    }

    public static boolean isAnnotatorOnActiveServer()
    {
        return !getActiveServerAnnotatorUUID().equals(EmptyUUID);
    }



    public static @NonNull  String getActiveServerAnnotatorUUID() {
        try(Realm realmManagement = getManagementRealm()) {
            AnnotationServer activeServer = RealmHelper.getActiveServer(realmManagement);
            if (activeServer != null && activeServer.getLocalAnnotatorUUID() != null)
            {
                return activeServer.getLocalAnnotatorUUID();
            }
        }
        return EmptyUUID;

    }


    public static void registerAsAnnotatorOnActiveServer(Realm realm, String annotatorUUID, String annotatorName) {
        ManagementDataModule.setActiveServerAnnotatorUUID(annotatorUUID);
        Annotator.CreateLocalAnnotator(realm, annotatorName);
    }
}