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

import es.gpulido.annotationmodel.archer.Archer;
import es.gpulido.annotationmodel.archer.Gender;
import es.gpulido.annotationmodel.category.ClassGender;
import io.realm.Realm;
import io.realm.RealmResults;

@SuppressWarnings("WeakerAccess,SpellCheckingInspection")
public class CompetitionHelper {




    public static RealmResults<Competition> GetCompetitionsByCategory(Realm realm, @CompetitionCategory.Competitions String rankingType) {
        return realm.where(Competition.class).equalTo("competitionCategory.name", rankingType).findAll();
    }

    public static boolean IsArcherInClassGender(ClassGender classGender, Archer archer) {
        return classGender.getName().equals(ClassGender.MIX) ||
                classGender.getName().equals(ClassGender.MEN)
                && archer.getGender().getName().equals(Gender.MALE) ||
                classGender.getName().equals(ClassGender.WOMEN) && archer.getGender().getName().equals(Gender.FEMALE);
    }

}

