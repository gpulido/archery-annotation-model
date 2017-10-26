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

import es.gpulido.annotationmodel.annotation.AnnotationValue;

/**
 * Created by gpt on 3/06/16.
 */

public class AnnotationValueSerializer implements JsonSerializer<AnnotationValue> {
    @Override
    public JsonElement serialize(AnnotationValue src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UUID", src.getUUID());
        jsonObject.add("fieldDefinition",  context.serialize(src.getFieldDefinition()));
        jsonObject.addProperty("stringFieldValue", src.getStringFieldValue());
        jsonObject.addProperty("doubleFieldValue", src.getDoubleFieldValue());
        jsonObject.addProperty("fieldDrawable", src.getFieldDrawable());
        jsonObject.addProperty("xPosition", src.getXPosition());
        jsonObject.addProperty("yPosition", src.getYPosition());
        jsonObject.add("annotationDate",  context.serialize(src.getAnnotationDate()));
        return jsonObject;
    }
}
