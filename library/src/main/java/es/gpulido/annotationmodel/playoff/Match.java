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

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.annotation.Contender;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;


/**
 * Models a match of a bracket.
 * A match have reference to the two matches that provides the contenders and the match to wich provides
 * the resultant contender.
 */

public class Match extends RealmObject implements IHasPrimarykey {
    @PrimaryKey
    private String UUID;


    private RealmList<Set> sets;

    private Bracket bracket;
    private Match match1;
    private Match match2;
    private Match nextMatch;
    private int matchNumber;
    private int roundOrder; //the first value is the number of players
    private boolean isBye;

    private MatchContender contender1;
    private MatchContender contender2;
    private MatchContender winner;

    private int contender1SetTotal;
    private int contender2SetTotal;

    //TODO: very important. Think of the untie mechanics
    public static Match createMatch(Realm realm,  Bracket bracket, Match match1, Match match2,int matchNumber, int roundOrder, boolean isBye)
    {
        //Timber.d(": end: %s Contender: %s", contender.getName(), end.getOrder());
        Match match = realm.createObject(Match.class, java.util.UUID.randomUUID().toString());
        match.setBracket(bracket);
        if (match1 != null)
        {
            match.setMatch1(match1);
            match1.setNextMatch(match);
        }
        if (match2 != null) {
            match.setMatch1(match2);
            match2.setNextMatch(match);
        }
        match.setMatchNumber(matchNumber);
        match.setRoundOrder(roundOrder);
        match.setBye(isBye);
        return match;
    }


    @Override
    public String getUUID() {
        return UUID;
    }

    public Bracket getBracket() {
        return bracket;
    }

    public void setBracket(Bracket bracket) {
        this.bracket = bracket;
    }

    public Match getMatch1() {
        return match1;
    }

    public void setMatch1(Match match1) {
        this.match1 = match1;
    }

    public Match getMatch2() {
        return match2;
    }

    public void setMatch2(Match match2) {
        this.match2 = match2;
    }

    public Match getNextMatch() {
        return nextMatch;
    }

    public void setNextMatch(Match nextMatch) {
        this.nextMatch = nextMatch;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }

    public int getRoundOrder() {
        return roundOrder;
    }

    public void setRoundOrder(int roundOrder) {
        this.roundOrder = roundOrder;
    }

    public boolean isBye() {
        return isBye;
    }

    public void setBye(boolean bye) {
        isBye = bye;
    }

    public MatchContender getContender1() {
        return contender1;
    }

    public void setContender1(MatchContender contender1) {
        this.contender1 = contender1;
    }

    public MatchContender getContender2() {
        return contender2;
    }

    public void setContender2(MatchContender contender2) {
        this.contender2 = contender2;
    }
}
