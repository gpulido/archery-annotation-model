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

import es.gpulido.annotationmodel.category.Division;


/**
 * Created by gpt on 20/02/16.
 */
@SuppressWarnings("unused")
public class DivisionSerializer implements JsonSerializer<Division> {

    @Override
    public JsonElement serialize(Division src, Type typeOfSrc, JsonSerializationContext context) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("UUID", src.getUUID());
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("bowResourceName", src.getBowResourceName());
        return jsonObject;
    }

}
