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

import java.util.UUID;

import es.gpulido.annotationmodel.annotation.Annotation;
import es.gpulido.annotationmodel.annotation.AnnotationTotalValue;
import es.gpulido.annotationmodel.annotation.AnnotationValue;
import es.gpulido.annotationmodel.annotation.Contender;
import es.gpulido.annotationmodel.annotation.End;
import es.gpulido.annotationmodel.annotation.Tournament;
import es.gpulido.annotationmodel.target.FieldDefinition;
import es.gpulido.annotationmodel.target.Target;
import es.gpulido.annotationmodel.target.TotalDefinition;
import io.realm.Realm;
import timber.log.Timber;

//import org.apache.commons.lang.StringUtils;

/**
 * Helper class to annotations
 */
public class AnnotationHelper {

    public static Annotation CreateAnnotation(Realm realm, End end, Contender contender, Tournament tournament, Target target)
    {
        Timber.d(": end: %s Contender: %s", contender.getName(), end.getOrder());
        Annotation annotation = realm.createObject(Annotation.class, UUID.randomUUID().toString());
        annotation.setTarget(target);
        annotation.setEnd(end);
        //end.getAnnotations().add(annotation);
        annotation.setContender(contender);
        //contender.getAnnotations().add(annotation);
        annotation.setTournament(tournament);
//        annotation.setIsSynchronized(true);
        CreateAnnotationValues(realm, annotation);
        CreateTotalValues(realm, annotation);
        return annotation;
    }



    private static void CreateAnnotationValues(Realm realm, Annotation annotation)
    {
        for(FieldDefinition fd: annotation.getTarget().getFieldDefinitions())
        {
            AnnotationValue value = realm.createObject(AnnotationValue.class,UUID.randomUUID().toString());
            value.setFieldDefinition(fd);
            value.setStringFieldValue("");
            //annotation.getAnnotationValues().add(value);
            value.setAnnotation(annotation);
        }
    }

    private static void CreateTotalValues(Realm realm, Annotation annotation) {
        for(TotalDefinition td: annotation.getTarget().getTotalDefinitions())
        {
            AnnotationTotalValue value = realm.createObject(AnnotationTotalValue.class, UUID.randomUUID().toString());
            value.setTotalDefinition(td);
            //annotation.getAnnotationTotalValues().add(value);
            value.setAnnotation(annotation);
            value.setDoubleFieldValue(0);
        }
    }




}
