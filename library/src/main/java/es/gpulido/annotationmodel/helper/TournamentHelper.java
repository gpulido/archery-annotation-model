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

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import es.gpulido.annotationmodel.annotation.End;
import es.gpulido.annotationmodel.annotation.Letter;
import es.gpulido.annotationmodel.annotation.Parapet;
import es.gpulido.annotationmodel.annotation.Round;
import es.gpulido.annotationmodel.annotation.Tournament;
import es.gpulido.annotationmodel.archer.Club;
import es.gpulido.annotationmodel.competition.Competition;
import es.gpulido.annotationmodel.serie.SerieRound;
import io.realm.Realm;
import timber.log.Timber;

/**
 * Created by gpt on 20/02/17.
 */

public class TournamentHelper {

    public static void GenerateTournament(@NonNull Realm realm, @NonNull Tournament tournament)
    {
        boolean realmIntransaction =realm.isInTransaction();
        if (!realmIntransaction)
            realm.beginTransaction();
        if (tournament.getTournamentType().equals(ModelTypes.T_COMPETITION))
            GenerateParapetsBasedOnShotField(realm, tournament);
        if (tournament.getTournamentType().equals(ModelTypes.T_COMPETITION)
                || tournament.getTournamentType().equals(ModelTypes.T_SUMMER_RANKING)
                || tournament.getTournamentType().equals(ModelTypes.T_WINTER_RANKING)) {
            Competition baseCompetition = tournament.getCompetitions().get(0);
            GenerateRoundsForTournament(realm, baseCompetition.getSerie().getSeriesRounds().size(), baseCompetition.getSerie().getSeriesRounds().first().getNumEntries(), tournament);
        }
        if (!realmIntransaction)
            realm.commitTransaction();
    }

    public static Tournament createNewTournament(Realm realm,
                                                 String name,
                                                 String description,
                                                 Date dateTime,
                                                 Date enDateTime,
                                                 List<Competition> competitions,
                                                 @ModelTypes.TournamentTypes String tournamentType,
                                                 boolean isShared, Club hostingClub, String tournamentImage) {
        boolean realmIntransaction =realm.isInTransaction();
        if (!realmIntransaction)
            realm.beginTransaction();

        Tournament tournament = realm.createObject(Tournament.class,  UUID.randomUUID().toString());
        tournament.setName(name);
        tournament.setDescription(description);
        tournament.setDateTime(dateTime);
        tournament.setEndDateTime(enDateTime);
        tournament.getCompetitions().addAll(competitions);
        //tournament.setIsShared(isShared);
        tournament.setTournamentType(tournamentType);
        tournament.setImageResourceName(tournamentImage);


        tournament.setIsInscriptionClosed(false);
        tournament.setHostingClub(hostingClub);
        if (hostingClub != null)
            tournament.setField(hostingClub.getShotFields().first());
        GenerateTournament(realm, tournament);
        if (!realmIntransaction)
            realm.commitTransaction();
        return tournament;
    }



    public static void GenerateRoundsBasedOnCompetition(@NonNull Realm realm, @NonNull Competition competition, @NonNull Tournament tournament) {

        Timber.i( "GenerateCompetition: %s", competition.getName());
        int j = 0;
        for (SerieRound serieRound : competition.getSerie().getSeriesRounds()) {
            Timber.i( "GenerateRound %s", j);
            Round round = realm.createObject(Round.class, UUID.randomUUID().toString());
            round.setOrder(j);
            round.setTournament(tournament);
            //tournament.getRounds().add(round);
            j++;
            for (int i = 0; i < serieRound.getNumEntries(); i++) {
                Timber.i("GenerateEntry %s", i);
                End end = realm.createObject(End.class, UUID.randomUUID().toString());
                end.setOrder(i);
                end.setRound(round);
                //round.getEntries().add(end);

            }

        }
        tournament.setIsGenerated(true);
    }

    private static void GenerateRoundsForTournament(@NonNull Realm realm, int numRounds , int numEnds, @NonNull Tournament tournament)
    {
        for (int i = 0; i< numRounds; i++) {
            Round round = realm.createObject(Round.class, UUID.randomUUID().toString());
            round.setOrder(i);
            round.setTournament(tournament);
            //tournament.getRounds().add(round);

            for (int j = 0; j < numEnds; j++) {
                Timber.i("GenerateEnd %s", i);
                End end = realm.createObject(End.class, UUID.randomUUID().toString());
                end.setOrder(j);
                end.setRound(round);
               // round.getEntries().add(end);
            }

        }
        tournament.setIsGenerated(true);

    }

    private static void GenerateParapetsBasedOnShotField(@NonNull Realm realm, @NonNull Tournament tournament) {
        ArrayList<Letter> lettersArrayList = new ArrayList<>();

        for(int k=0; k< tournament.getField().getNumParapetsMax(); k++)
        {
            Parapet parapet = realm.createObject(Parapet.class, UUID.randomUUID().toString());
            parapet.setOrder(k);
            parapet.setTournament(tournament);
            //tournament.getParapets().add(parapet);

            //Creation of the Letters for each parapet
            List<String> letters = tournament.getField().getFieldLetters();
            for (String let: letters) {
                Letter letter = realm.createObject(Letter.class, UUID.randomUUID().toString());
                letter.setLetterSymbol(let);
                letter.setParapet(parapet);
                //parapet.getLetters().add(letter);
                lettersArrayList.add(letter);
            }
        }
    }


}
