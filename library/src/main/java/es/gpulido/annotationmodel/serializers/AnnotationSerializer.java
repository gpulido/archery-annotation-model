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

import es.gpulido.annotationmodel.annotation.Annotation;
import es.gpulido.annotationmodel.annotation.AnnotationTotalValue;
import es.gpulido.annotationmodel.annotation.AnnotationValue;

/**
 * Created by gpt on 3/06/16.
 */

public class AnnotationSerializer implements JsonSerializer<Annotation> {
    @Override
    public JsonElement serialize(Annotation src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UUID", src.getUUID());
        jsonObject.add("tournament", context.serialize(src.getTournament()));
        jsonObject.add("contender", context.serialize(src.getContender()));
        jsonObject.add("end", context.serialize(src.getEnd()));
        jsonObject.add("target", context.serialize(src.getTarget()));
        jsonObject.addProperty("isCompleted", src.isCompleted());
//        jsonObject.addProperty("isSynchronized", src.getIsSynchronized());
        jsonObject.add("annotationValues", context.serialize(src.annotationValues.toArray(), AnnotationValue[].class));
        jsonObject.add("annotationTotalValues", context.serialize(src.annotationTotalValues.toArray(), AnnotationTotalValue[].class));
        return jsonObject;
    }

}

