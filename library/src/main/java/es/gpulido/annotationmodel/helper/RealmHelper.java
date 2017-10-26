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

package es.gpulido.annotationmodel.helper;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.annotation.Annotation;
import es.gpulido.annotationmodel.annotation.AnnotationValue;
import es.gpulido.annotationmodel.annotation.Contender;
import es.gpulido.annotationmodel.annotation.End;
import es.gpulido.annotationmodel.annotation.Tournament;
import es.gpulido.annotationmodel.archer.Club;
import es.gpulido.annotationmodel.management.AnnotationServer;
import es.gpulido.annotationmodel.management.ManagementDataModule;
import es.gpulido.annotationmodel.target.FieldDefinition;
import es.gpulido.annotationmodel.target.Target;
import es.gpulido.annotationmodel.serializers.AnnotationSerializer;
import es.gpulido.annotationmodel.serializers.AnnotationTotalValueSerializer;
import es.gpulido.annotationmodel.serializers.AnnotationValueSerializer;
import es.gpulido.annotationmodel.serializers.AnnotatorSerializer;
import es.gpulido.annotationmodel.serializers.ArcherSerializer;
import es.gpulido.annotationmodel.serializers.ClubSerializer;
import es.gpulido.annotationmodel.serializers.CompetitionSerializer;
import es.gpulido.annotationmodel.serializers.ContenderSerializer;
import es.gpulido.annotationmodel.serializers.ContenderWithTournamentSerializer;
import es.gpulido.annotationmodel.serializers.EndSerializer;
import es.gpulido.annotationmodel.serializers.FieldDefinitionSerializer;
import es.gpulido.annotationmodel.serializers.GenderSerializer;
import es.gpulido.annotationmodel.serializers.LetterSerializer;
import es.gpulido.annotationmodel.serializers.ParapetSerializer;
import es.gpulido.annotationmodel.serializers.RoundSerializer;
import es.gpulido.annotationmodel.serializers.ShotFieldSerializer;
import es.gpulido.annotationmodel.serializers.TargetSerializer;
import es.gpulido.annotationmodel.serializers.TotalDefinitionSerializer;
import es.gpulido.annotationmodel.serializers.TournamentSerializer;
import es.gpulido.annotationmodel.serializers.TournamentUUIDSerializer;
import io.realm.Case;
import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncManager;
import io.realm.SyncSession;
import io.realm.SyncUser;

/**
 * Created by ggpt on 02/12/2015.
 */
public class RealmHelper {


    public static Realm getActiveInstance()
    {
        return Realm.getInstance(getActiveConfiguration());
    }

    public static RealmAsyncTask getActiveInstanceAsync(Realm.Callback callback)
    {
        return Realm.getInstanceAsync(getActiveConfiguration(), callback);
    }

    private volatile static List<WeakReference<Callback>> callbacks = new CopyOnWriteArrayList<>();



    private static AnnotationServer getLocalServer(Realm realm)
    {
        return realm.where(AnnotationServer.class).equalTo("isLocal", true).findFirst();
    }

    public static boolean isConnected()
    {
        return getCurrentUser() != null;
    }


    private static SyncUser getCurrentUser()
    {
        try {
            return SyncUser.currentUser();
        }
        catch (Exception ex) {
            return null;
        }

    }

    private static AnnotationServer getActiveUserServer(Realm realm)
    {
        if (getCurrentUser() != null)
        {
            return realm.where(AnnotationServer.class).equalTo("userIdentity", SyncUser.currentUser().getIdentity()).findFirst();
        }
        return null;
    }


    public static AnnotationServer getActiveServer(Realm realm)
    {
        try {
            if (getCurrentUser() != null) {
                return getActiveUserServer(realm);

            } else {
                return getLocalServer(realm);
            }
        }
        catch(Exception ex)
        {
            return getLocalServer(realm);
        }

    }

    public static RealmConfiguration getActiveConfiguration()
    {

        try(Realm realm = ManagementDataModule.getManagementRealm())
        {
            AnnotationServer as = getActiveServer(realm);
            if (as == null) return null;

            if (as.isLocal())
                return as.getConfiguration(null);

            if (getCurrentUser() != null)
                return SyncManager.getSession((SyncConfiguration) as.getConfiguration(SyncUser.currentUser())).getConfiguration();

            //we need to ask the user to relogin
            return null;

        }
    }

    public static void uploadAllLocalChanges() {
        Thread thread = new Thread(() -> {
            try {
                if (getActiveConfiguration()!= null) {
                    SyncManager.getSession((SyncConfiguration) getActiveConfiguration()).uploadAllLocalChanges();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
    public static void downloadAllServerChanges() {
        Thread thread = new Thread(() -> {
            try {
                if (getActiveConfiguration()!= null) {
                    SyncManager.getSession((SyncConfiguration) getActiveConfiguration()).downloadAllServerChanges();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }


    public static void logoutActiveUser() {
        if (getCurrentUser() != null) {
            SyncUser.currentUser().logout();

        }

    }

    public static void closeDefaultInstance()
    {
        logoutActiveUser();
        for (WeakReference<Callback> c : callbacks) {
            Callback callback = c.get();
            if (callback != null)
                c.get().onCloseDefaultInstance();
            else
                callbacks.remove(c);
        }

    }

    public static void registerCallback(Callback callback) {
        for (int i = 0; i< callbacks.size(); i++) {
            if (callbacks.get(i).get()!= null && callbacks.get(i).get().equals(callback)) {
                return;
            }
        }
        callbacks.add(new WeakReference<>(callback));
    }

    public static void unRegisterCallback(Callback callback) {
        for (int i = 0; i< callbacks.size(); i++) {
            if (callbacks.get(i).get()!= null && callbacks.get(i).get().equals(callback)) {
                callbacks.remove(i);
                return;
            }
        }
    }


    public static <E extends RealmObject> RealmResults<E> GetFilteredRealmResults(String newText, RealmResults<E> results, String... filterFieldNames) {
        TextUtils.SimpleStringSplitter splitter = new TextUtils.SimpleStringSplitter(' ');
        splitter.setString(newText);
        RealmQuery<E>  realmQuery = results.where();

        for(String word: splitter)
        {
            realmQuery = realmQuery.beginGroup();
            for(int i = 0; i<filterFieldNames.length; i++)
            {
                String fieldName = filterFieldNames[i];
                if (i != 0)
                    realmQuery = realmQuery.or();
                realmQuery = realmQuery.contains(fieldName, word, Case.INSENSITIVE);
            }
            realmQuery= realmQuery.endGroup();
        }
        return realmQuery.findAll();
    }

    private static Gson realmObjectGson;
    public static Gson RealmObjectGson()
    {
        if (realmObjectGson == null)
            try {
                realmObjectGson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStrategy() {
                            @Override
                            public boolean shouldSkipField(FieldAttributes f) {
                                return f.getDeclaringClass().equals(RealmObject.class);
                            }
                            @Override
                            public boolean shouldSkipClass(Class<?> clazz) {
                                return false;
                            }
                        })
                        .setDateFormat(DateFormat.MILLISECOND_FIELD)
                        // .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .registerTypeAdapter(Class.forName("io.realm.AnnotatorRealmProxy"), new AnnotatorSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.ArcherRealmProxy"), new ArcherSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.ClubRealmProxy"), new ClubSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.GenderRealmProxy"), new GenderSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.TournamentRealmProxy"), new TournamentSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.CompetitionRealmProxy"), new CompetitionSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.ShotFieldRealmProxy"), new ShotFieldSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.ParapetRealmProxy"), new ParapetSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.LetterRealmProxy"), new LetterSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.RoundRealmProxy"), new RoundSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.EndRealmProxy"), new EndSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.ContenderRealmProxy"), new ContenderSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.AnnotationRealmProxy"), new AnnotationSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.AnnotationValueRealmProxy"), new AnnotationValueSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.AnnotationTotalValueRealmProxy"), new AnnotationTotalValueSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.TargetRealmProxy"), new TargetSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.FieldDefinitionRealmProxy"), new FieldDefinitionSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.TotalDefinitionRealmProxy"), new TotalDefinitionSerializer())
                        .create();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        return realmObjectGson;

    }

    private static Gson annotationRealmGson;
    public static Gson AnnotationRealmGson()
    {
        if (annotationRealmGson == null)
            try {
                annotationRealmGson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStrategy() {
                            @Override
                            public boolean shouldSkipField(FieldAttributes f) {
                                return f.getDeclaringClass().equals(RealmObject.class);
                            }
                            @Override
                            public boolean shouldSkipClass(Class<?> clazz) {
                                return false;
                            }
                        })
                        .setDateFormat(DateFormat.MILLISECOND_FIELD)
                        .registerTypeAdapter(Class.forName("io.realm.AnnotationRealmProxy"), new AnnotationSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.AnnotationValueRealmProxy"), new AnnotationValueSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.AnnotationTotalValueRealmProxy"), new AnnotationTotalValueSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.FieldDefinitionRealmProxy"), new FieldDefinitionSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.TotalDefinitionRealmProxy"), new TotalDefinitionSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.TournamentRealmProxy"), new TournamentUUIDSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.ClubRealmProxy"), new ClubSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.ContenderRealmProxy"), new ContenderWithTournamentSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.CompetitionRealmProxy"), new CompetitionSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.EndRealmProxy"), new EndSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.LetterRealmProxy"), new LetterSerializer())
                        .registerTypeAdapter(Class.forName("io.realm.TargetRealmProxy"), new TargetSerializer())
                        .create();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        return annotationRealmGson;

    }
    private static Gson annotationGson;
    public static Gson AnnotationGson()
    {
        if (annotationGson == null)
            annotationGson = new GsonBuilder()
                    .setExclusionStrategies(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getDeclaringClass().equals(RealmObject.class);
                        }
                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    })
                    .setDateFormat(DateFormat.MILLISECOND_FIELD)
                    .registerTypeAdapter(Annotation.class, new AnnotationSerializer())
                    .registerTypeAdapter(AnnotationValue.class, new AnnotationValueSerializer())
                    .registerTypeAdapter(Annotation.class, new AnnotationTotalValueSerializer())
                    .registerTypeAdapter(FieldDefinition.class, new FieldDefinitionSerializer())
                    .registerTypeAdapter(FieldDefinition.class, new TotalDefinitionSerializer())
                    .registerTypeAdapter(Tournament.class, new TournamentUUIDSerializer())
                    .registerTypeAdapter(Club.class, new ClubSerializer())
                    .registerTypeAdapter(Contender.class, new ContenderWithTournamentSerializer())
                    .registerTypeAdapter(End.class, new EndSerializer())
                    .registerTypeAdapter(Target.class, new TargetSerializer())
                    .create();

        return annotationGson;

    }

    public interface Callback {
        void onDefaultInstanceChanged();
        void onCloseDefaultInstance();
    }

    public static <T extends RealmObject & IHasPrimarykey> T getByPrimaryKey(Realm realm, Class<T> type, String primaryKey)
    {
        return realm.where(type).equalTo("UUID", primaryKey).findFirst();
    }

    public static <T extends RealmObject & IHasPrimarykey> T getByPrimaryKeyAsync(Realm realm, Class<T> type, String primaryKey)
    {
        return realm.where(type).equalTo("UUID", primaryKey).findFirstAsync();
    }

}


