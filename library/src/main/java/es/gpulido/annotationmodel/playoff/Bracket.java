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

import java.util.ArrayList;
import java.util.List;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.annotation.Contender;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Class that models a complete plafoff structure. It holds the list of all matches that conforms
 * the playoff.
 */

public class Bracket extends RealmObject implements IHasPrimarykey {

    @PrimaryKey
    private String UUID;

    private RealmList<Match> matches;
    private SetDefinition setDefinition;

    @Override
    public String getUUID() {
        return UUID;
    }


    public static void createBracket(Realm realm, List<MatchContender> contenders)
    {
        //TODO: create the bracket properly
        Bracket bracket = new Bracket();
        //Need to create the bye contender
        int numContenders = contenders.size();

        int closestNumber = getClosetPowerOfTwoNumber(numContenders);
        int byes = closestNumber - numContenders;
        numContenders = closestNumber;
        List<Match> firstRoundBrackets = new ArrayList<>();

        for (int i = 0; i < numContenders/2; i++)
        {
            boolean isbye = false;
            if (byes > 0)
            {
                isbye = true;
                byes--;
            }
            Match match = Match.createMatch(realm, bracket, null, null, i, 0, isbye);
            firstRoundBrackets.add(match);
            bracket.getMatches().add(match);
        }
        generateRound(realm, bracket, 1,firstRoundBrackets);

    }
    private static void generateRound(Realm realm, Bracket bracket, int roundNumber, List<Match> lastRoundMatches)
    {
        int numContenders = lastRoundMatches.size();
        List<Match> newRoundMatches = new ArrayList<>();

        int numRoundMatches = numContenders/2;
        for (int i = 0; i < numRoundMatches; i++)
        {
            Match previousMatch1 = lastRoundMatches.get(i);
            Match previousMatch2 = lastRoundMatches.get(numRoundMatches -1 - i);
            Match match = Match.createMatch(realm, bracket, previousMatch1, previousMatch2, i, roundNumber, false);
            newRoundMatches.add(match);
            bracket.getMatches().add(match);
        }
        if (numRoundMatches > 1)
            generateRound(realm, bracket, ++roundNumber, newRoundMatches);

    }

    private static int getClosetPowerOfTwoNumber(int contenderSize)
    {
        //http://stackoverflow.com/questions/5242533/fast-way-to-find-exponent-of-nearest-superior-power-of-2
        return contenderSize == 0 ? 0: 32 - Integer.numberOfLeadingZeros(contenderSize - 1);
    }

    public RealmList<Match> getMatches() {
        return matches;
    }

    public void setMatches(RealmList<Match> matches) {
        this.matches = matches;
    }


}
