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

package es.gpulido.annotationmodel.competition;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.annotation.Tournament;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by ggpt on 28/07/2017.
 */

public class Template extends RealmObject implements IHasPrimarykey {

    @PrimaryKey
    private String UUID;

    private String name;

    private String templateValue;

    @Override
    public String getUUID() {
        return null;
    }

    public String getTemplateValue() {
        return templateValue;
    }

    public void setTemplateValue(String templateValue) {
        this.templateValue = templateValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void createDefaultData(Realm realm) {

        Timber.i("Creating Templates");

        Template defaultMailTemplate = realm.createObject(Template.class, CompetitionDataModule.UUIDGenerator.generate().toString());
        defaultMailTemplate.setName("Mail Template");
//        defaultMailTemplate.setTemplateValue(
//                "Estimado/a {$contender.name},
//
//                    Muchas gracias por participar en nuestro {$tournament.name}.
//
//                Continuando con nuestra costumbre,  adjunto te enviamos las clasificaciones finales de torneo, que también las encontrarás publicadas en nuestra página web, así como el detalle de tu tablilla, para que puedas analizarla, si lo deseas.
//
//        También nos gustaría conocer tu opinión sobre el desarrollo del torneo, especialmente las carencias o deficiencias que hayas detectado, al objeto de corregirlas en lo sucesivo y así mejorar en la medida de lo posible. Puedes enviar tus comentarios a {$club.mail}
//
//        Deseando que hayas disfrutado de la competición, esperamos volver a tenerte en nuestra línea de tiro el próximo año.
//
//                Recibe un cordial saludo.
//
//                El Comité de Competición
//        {$club.name}"
//
//
//        );

    }
}
