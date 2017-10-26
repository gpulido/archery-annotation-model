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

package es.gpulido.annotationmodel;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

/**
 * Created by gpt on 18/01/16.
 */
public class Migration implements RealmMigration{
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

//        RealmSchema schema = realm.getSchema();
//        if (oldVersion == 4) {
//
//            RealmObjectSchema tournamentSchema = schema.get("Tournament");
//            DynamicRealmObject cdetaClub = realm.where("Club").contains("name", "CDETA").contains("name", "Rivas").findFirst();
//            tournamentSchema
//                    .addRealmObjectField("hostingClub",schema.get("Club"))
//                    .transform(obj -> obj.set("hostingClub", cdetaClub));
//            //oldVersion ++;
//        }
//        if (oldVersion == 6) {
//
//            RealmObjectSchema realmObjectSchema = schema.create("BowConfiguration")
//                    .addField("UUID", String.class, FieldAttribute.PRIMARY_KEY)
//                    .addField("name", String.class)
//                    .addField("deleted", boolean.class);
//            realmObjectSchema.addRealmObjectField("BowType", realmObjectSchema);
//            oldVersion ++;
//        }

    }
}
