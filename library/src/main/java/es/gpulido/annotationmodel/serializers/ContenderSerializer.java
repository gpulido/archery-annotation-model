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

/**
 * Created by gpt on 3/06/16.
 */

public class ContenderSerializer implements JsonSerializer<Contender> {
    @Override
    public JsonElement serialize(Contender src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UUID", src.getUUID());
        jsonObject.add("competition", context.serialize(src.getCompetition()));
        jsonObject.add("initCompetitionDate",  context.serialize(src.getInitCompetitionDate()));
        jsonObject.addProperty("isContenderCompleted", src.getIsContenderCompleted());
        jsonObject.add("letter",  context.serialize(src.getLetter()));
        jsonObject.addProperty("name", src.getName());
        jsonObject.add("club", context.serialize(src.getClub()));
        jsonObject.addProperty("state", src.getState());
        //jsonObject.add("annotations",  context.serialize(src.getAnnotations().toArray(), Annotation[].class));
        //jsonObject.add("archer",  context.serialize(src.getArcher()));
        return jsonObject;
    }

}
