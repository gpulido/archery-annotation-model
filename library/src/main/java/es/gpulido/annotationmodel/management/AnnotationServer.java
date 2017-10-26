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

import android.support.annotation.Nullable;

import es.gpulido.annotationmodel.BasicDataModule;
import es.gpulido.annotationmodel.Interfaces.ICanBeDeleted;
import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.annotation.AnnotationDataModule;
import es.gpulido.annotationmodel.archer.ArcherDataModule;
import es.gpulido.annotationmodel.category.CategoryDataModule;
import es.gpulido.annotationmodel.competition.CompetitionDataModule;
import es.gpulido.annotationmodel.serie.SerieDataModule;
import es.gpulido.annotationmodel.target.TargetDataModule;
import es.gpulido.annotationmodel.timer.TimerDataModule;
import es.gpulido.annotationmodel.training.TrainingDataModule;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 26/02/17.
 */

public class AnnotationServer extends RealmObject implements IHasPrimarykey, ICanBeDeleted{

    @PrimaryKey
    private String UUID;
    private String databaseName;
    private String serverIP;
    private String userId;
    private String userIdentity;
    private boolean isLocal;
    private boolean isActive;
    private String activeTournamentUUID;
    private boolean deleted;
    private String localAnnotatorUUID;


    public String getRealmUrl()
    {
        return "realm://" + serverIP + ":9080/~/"+ databaseName;
    }

    public String getAuthUrl()
    {
        return "http://" +serverIP + ":9080/auth";

    }
    public SyncCredentials getSyncCredentials(String password)
    {
        return getSyncCredentials(password, false);
    }

    public SyncCredentials getSyncCredentials(String password, boolean createUser)
    {
        return SyncCredentials.usernamePassword(userId, password, createUser);
    }

    public RealmConfiguration getConfiguration(@Nullable SyncUser user)
    {
        if (isLocal)
        {
            return new RealmConfiguration.Builder()
                    .modules(new CategoryDataModule(),
                            new TargetDataModule(),
                            new SerieDataModule(),
                            new ArcherDataModule(),
                            new CompetitionDataModule(),
                            new AnnotationDataModule(),
                            new TimerDataModule(),
                            new TrainingDataModule())
                    .name(databaseName)
                    //.initialData(BasicDataModule::createDefaultModuleData)
                    .schemaVersion(4)
                    .deleteRealmIfMigrationNeeded()
                    //.migration(new Migration())
                    .build();
        }
        else
            //if (databaseName == "Rankings")
            {
            String serverURL = getRealmUrl();
            SyncConfiguration configuration = new SyncConfiguration.Builder(user, serverURL)
                    .modules(new CategoryDataModule(),
                            new TargetDataModule(),
                            new SerieDataModule(),
                            new ArcherDataModule(),
                            new CompetitionDataModule(),
                            new AnnotationDataModule(),
                            new TimerDataModule(),
                            new TrainingDataModule())
                    .schemaVersion(4)
                    //.initialData(BasicDataModule::createDefaultModuleData)
                    .waitForInitialRemoteData()
                    .build();
            return configuration;


        }
    }



    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getserverIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getActiveTournamentUUID() {
        return activeTournamentUUID;
    }

    public void setActiveTournamentUUID(String activeTournamentUUID) {
        this.activeTournamentUUID = activeTournamentUUID;
    }


    @Override
    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
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

    public String getLocalAnnotatorUUID() {
        return localAnnotatorUUID;
    }

    public void setLocalAnnotatorUUID(String localAnnotatorUUID) {
        this.localAnnotatorUUID = localAnnotatorUUID;
    }
}
