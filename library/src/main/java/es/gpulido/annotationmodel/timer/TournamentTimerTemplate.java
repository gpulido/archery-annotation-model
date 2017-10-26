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
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ggpt on 3/26/2015.
 */
public class TournamentTimerTemplate extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;
    private String name;
    private int numRounds;
    private int numEnds;
    private int numWarmingEnds;
    private int numLetters;
    private int endInitTime;
    private String timerHash;

    //actual status part
    private int phase_order;
    private int end_order;
    private int round_order;
    private int status;
    private int number;

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

    public int getNumWarmingEnds() {
        return numWarmingEnds;
    }

    public void setNumWarmingEnds(int numWarmingEnds) {
        this.numWarmingEnds = numWarmingEnds;
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

    public int getPhase_order() {
        return phase_order;
    }

    public void setPhase_order(int phase_order) {
        this.phase_order = phase_order;
    }

    public int getEnd_order() {
        return end_order;
    }

    public void setEnd_order(int end_order) {
        this.end_order = end_order;
    }

    public int getRound_order() {
        return round_order;
    }

    public void setRound_order(int round_order) {
        this.round_order = round_order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumLetters() {
        return numLetters;
    }

    public void setNumLetters(int numLetters) {
        this.numLetters = numLetters;
    }
}
