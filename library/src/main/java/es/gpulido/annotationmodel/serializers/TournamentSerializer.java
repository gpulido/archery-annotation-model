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

package es.gpulido.annotationmodel.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import es.gpulido.annotationmodel.annotation.Contender;
import es.gpulido.annotationmodel.annotation.Parapet;
import es.gpulido.annotationmodel.annotation.Round;
import es.gpulido.annotationmodel.annotation.Tournament;
import es.gpulido.annotationmodel.competition.Competition;

/**
 * Created by gpt on 3/06/16.
 */

public class TournamentSerializer implements JsonSerializer<Tournament> {

    @Override
    public JsonElement serialize(Tournament src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UUID", src.getUUID());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("description", src.getDescription());
        jsonObject.add("dateTime",  context.serialize(src.getDateTime()));
        jsonObject.add("endDateTime",  context.serialize(src.getEndDateTime()));
        jsonObject.add("shotField", context.serialize(src.getField()));
        jsonObject.addProperty("tournamentType", src.getTournamentType());
        jsonObject.add("competitions", context.serialize(src.getCompetitions().toArray(), Competition[].class));
        jsonObject.add("rounds", context.serialize(src.rounds.toArray(), Round[].class));
        jsonObject.add("parapets", context.serialize(src.parapets.toArray(), Parapet[].class));
        jsonObject.add("contenders", context.serialize(src.contenders.toArray(), Contender[].class));
        return jsonObject;
    }



   // private AnnotationServer server;
//    private RealmList<Parapet> parapets;

}
