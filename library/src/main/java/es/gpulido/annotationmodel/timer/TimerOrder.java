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

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ggpt on 4/20/2015.
 */
public class TimerOrder extends RealmObject {

    @PrimaryKey
    private String timerHash;
    private int phaseOrder;
    private int endOrder;
    private int roundOrder;
    private int status;
    private int number;


//    private TimerRepresentation timer_rep;
    public TimerOrder()
    {}

    public TimerOrder(String timerHash, int roundOrder,  int endOrder, int phaseOrder, int status, int number)
    {
        this.timerHash = timerHash;
        this.phaseOrder = phaseOrder;
        this.endOrder = endOrder;
        this.roundOrder = roundOrder;
        this.status = status;
        this.number = number;
    }

    public TimerOrder(TimerOrder timerOrder)
    {
        this.timerHash = timerOrder.getTimerHash();
        this.phaseOrder = timerOrder.getPhaseOrder();
        this.endOrder = timerOrder.getEndOrder();
        this.roundOrder = timerOrder.roundOrder;
        this.status = timerOrder.status;
        this.number = timerOrder.number;
    }

    public static TimerOrder UpdateTimerOrder(Realm realm, TimerOrder timerOrder)
    {
        realm.beginTransaction();
        TimerOrder realmTimerOrder = realm.copyToRealmOrUpdate(timerOrder);
        realm.commitTransaction();
        return realmTimerOrder;
    }


    public int getPhaseOrder() {
        return phaseOrder;
    }

    public void setPhaseOrder(int phaseOrder) {
        this.phaseOrder = phaseOrder;
    }

    public int getEndOrder() {
        return endOrder;
    }

    public void setEndOrder(int endOrder) {
        this.endOrder = endOrder;
    }

    public int getRoundOrder() {
        return roundOrder;
    }

    public void setRoundOrder(int roundOrder) {
        this.roundOrder = roundOrder;
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


    public String getTimerHash() {
        return timerHash;
    }

    public void setTimerHash(String timerHash) {
        this.timerHash = timerHash;
    }
}
