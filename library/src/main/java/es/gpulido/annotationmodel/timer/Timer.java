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

package es.gpulido.annotationmodel.timer;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.helper.ModelTypes;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ggpt on 3/26/2015.
 */
public class Timer extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;
    private String name;
    private int numRounds;
    private int numEnds;
    private int numLetters;
    private int endInitTime;
    private String timerHash;
    //private TimerInstant currentTimerInstant;
    @ModelTypes.TimerTypes
    private String timerType;

    public Timer(){}

    public Timer(int seconds, int rounds, int ends, int letters) {
        this(seconds,rounds,ends,letters,null);
    }

    public Timer(int seconds, int rounds, int ends, int letters, String timerHash){

        UUID = java.util.UUID.randomUUID().toString();
        name = "new Timer";
        endInitTime = seconds;
        numRounds = rounds;
        numEnds = ends;
        numLetters = letters;
        timerType = ModelTypes.TOURNAMENT;
        if (timerHash == null) {
            this.timerHash = UUID.substring(0, 6);
        }
        else
        {
         this.timerHash = timerHash;
        }
    }

    public Timer(Timer timer)
    {
        //UUID = java.util.UUID.randomUUID().toString();
        name = timer.getName();
        endInitTime = timer.getEndInitTime();
        numRounds = timer.getNumRounds();
        numEnds = timer.getNumEnds();
        numLetters = timer.getNumLetters();
        timerType = timer.getTimerType();
        this.timerHash = timer.getTimerHash();
    }

    public static Timer CreateTimer(Realm realm, int seconds, int rounds, int ends, int letters)
    {
        Timer timer = new Timer( seconds,
                rounds,
                ends,
                letters);

        TimerOrder timerOrder = new TimerOrder(timer.getTimerHash(), 0, 0, 0, 0, -1);
        realm.beginTransaction();
        Timer realmTimer = realm.copyToRealmOrUpdate(timer);
        realm.copyToRealmOrUpdate(timerOrder);
        realm.commitTransaction();
        return realmTimer;
    }

    public static Timer UpdateTimer(Realm realm, Timer timer)
    {
        realm.beginTransaction();
        if (timer.getUUID() == null)
            timer.setUUID(java.util.UUID.randomUUID().toString());
        Timer realmTimer = realm.copyToRealmOrUpdate(timer);
        realm.commitTransaction();
        return realmTimer;
    }


    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumRounds() {
        return numRounds;
    }

    public void setNumRounds(int numRounds) {
        this.numRounds = numRounds;
    }

    public int getNumEnds() {
        return numEnds;
    }

    public void setNumEnds(int numEnds) {
        this.numEnds = numEnds;
    }

    public int getEndInitTime() {
        return endInitTime;
    }

    public void setEndInitTime(int endInitTime) {
        this.endInitTime = endInitTime;
    }

    public String getTimerHash() {
        return timerHash;
    }

    public void setTimerHash(String timerHash) {
        this.timerHash = timerHash;
    }

    public int getNumLetters() {
        return numLetters;
    }

    public void setNumLetters(int numLetters) {
        this.numLetters = numLetters;
    }

    public @ModelTypes.TimerTypes String getTimerType() {
        return timerType;
    }

    public void setTimerType(@ModelTypes.TimerTypes String timerType) {
        this.timerType = timerType;
    }

}
