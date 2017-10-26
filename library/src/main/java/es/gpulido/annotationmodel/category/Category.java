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

package es.gpulido.annotationmodel.category;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by gpt on 22/02/17.
 */

public class Category  extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;
    @Index
    private @Categories String name;

    private Division division;
    private ArcheryClass archeryClass;


    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public  @Categories String getName() {
        return name;
    }

    public void setName(@Categories String name) {
        this.name = name;
    }


    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public ArcheryClass getArcheryClass() {
        return archeryClass;
    }

    public void setArcheryClass(ArcheryClass archeryClass) {
        this.archeryClass = archeryClass;
    }

    public static Category getByName(Realm realm, @Categories String name)
    {
        return realm.where(Category.class).equalTo("name", name).findFirst();
    }


    @StringDef({
            RW, RM, RCW, RCM, RJW, RJM, RMW, RMM, RX, RN, RIX,
            CW, CM, CCW, CCM, CJW, CJM, CMW, CMM, CX, CN, CIX,
            SW, SM, SCW, SCM, SJW, SJM, SMW, SMM, SX, SN, SIX,
            BW, BM, BCW, BCM, BJW, BJM, BMW, BMM, BX, BN, BIX,
            IW, IM, LW, LM, IX, LX, IIX, LIX, IN, LN
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface Categories{}
    public static final String RW = "Recurve Women";
    public static final String RM = "Recurve Men";
    public static final String RCW = "Recurve Cadet Women";
    public static final String RCM = "Recurve Cadet Men";
    public static final String RJW = "Recurve Junior Women";
    public static final String RJM = "Recurve Junior Men";
    public static final String RMW = "Recurve Master Women";
    public static final String RMM = "Recurve Master Men";
    public static final String RX = "Recurve Mix";
    public static final String RN = "Recurve Novel";
    public static final String RIX = "Recurve Child Mix";

    public static final String CW = "Compound Women";
    public static final String CM = "Compound Men";
    public static final String CCW = "Compound Cadet Women";
    public static final String CCM = "Compound Cadet Men";
    public static final String CJW = "Compound Junior Women";
    public static final String CJM = "Compound Junior Men";
    public static final String CMW = "Compound Master Women";
    public static final String CMM = "Compound Master Men";
    public static final String CX = "Compound Mix";
    public static final String CN = "Compound Novel";
    public static final String CIX = "Compound Child Mix";

    public static final String SW = "Standard Women";
    public static final String SM = "Standard Men";
    public static final String SCW = "Standard Cadet Women";
    public static final String SCM = "Standard Cadet Men";
    public static final String SJW = "Standard Junior Women";
    public static final String SJM = "Standard Junior Men";
    public static final String SMW = "Standard Master Women";
    public static final String SMM = "Standard Master Men";
    public static final String SX = "Standard Mix";
    public static final String SN = "Standard Novel";
    public static final String SIX = "Standard Child Mix";

    public static final String BW = "Bare Women";
    public static final String BM = "Bare Men";
    public static final String BCW = "Bare Cadet Women";
    public static final String BCM = "Bare Cadet Men";
    public static final String BJW = "Bare Junior Women";
    public static final String BJM = "Bare Junior Men";
    public static final String BMW = "Bare Master Women";
    public static final String BMM = "Bare Master Men";
    public static final String BX = "Bare Mix";
    public static final String BN = "Bare Novel";
    public static final String BIX = "Bare Child Mix";

    public static final String IW = "Instintive Women";
    public static final String IM = "Instintive Men";
    public static final String LW = "Longbow Women";
    public static final String LM = "Longbow Men";
    public static final String IX = "Instictive Mix";
    public static final String LX = "Longbow Mix";
    public static final String IIX = "Instictive Child Mix";
    public static final String LIX = "Longbow Child Mix";
    public static final String IN = "Instictive Novel";
    public static final String LN = "Longbow Novel";



    public static void createDefaultData(Realm realm)
    {
            Timber.i("Creating Categories");
            createCategory(realm, RW, Division.RECURVE_BOW, ArcheryClass.SENIOR_WOMEN);
            createCategory(realm, RM, Division.RECURVE_BOW, ArcheryClass.SENIOR_MEN);
            createCategory(realm, RCW, Division.RECURVE_BOW, ArcheryClass.CADET_WOMEN);
            createCategory(realm, RCM, Division.RECURVE_BOW, ArcheryClass.CADET_MEN);
            createCategory(realm, RJW, Division.RECURVE_BOW, ArcheryClass.JUNIOR_WOMEN);
            createCategory(realm, RJM, Division.RECURVE_BOW, ArcheryClass.JUNIOR_MEN);
            createCategory(realm, RMW, Division.RECURVE_BOW, ArcheryClass.MASTER_WOMEN);
            createCategory(realm, RMM, Division.RECURVE_BOW, ArcheryClass.MASTER_MEN);
            createCategory(realm, RX, Division.RECURVE_BOW, ArcheryClass.SENIOR_MIX);
            createCategory(realm, RN, Division.RECURVE_BOW, ArcheryClass.NOVEL_MIX);
            createCategory(realm, RIX, Division.RECURVE_BOW, ArcheryClass.CHILD_MIX);

            createCategory(realm, CW, Division.COMPOUND_BOW, ArcheryClass.SENIOR_WOMEN);
            createCategory(realm, CM, Division.COMPOUND_BOW, ArcheryClass.SENIOR_MEN);
            createCategory(realm, CCW, Division.COMPOUND_BOW, ArcheryClass.CADET_WOMEN);
            createCategory(realm, CCM, Division.COMPOUND_BOW, ArcheryClass.CADET_MEN);
            createCategory(realm, CJW, Division.COMPOUND_BOW, ArcheryClass.JUNIOR_WOMEN);
            createCategory(realm, CJM, Division.COMPOUND_BOW, ArcheryClass.JUNIOR_MEN);
            createCategory(realm, CMW, Division.COMPOUND_BOW, ArcheryClass.MASTER_WOMEN);
            createCategory(realm, CMM, Division.COMPOUND_BOW, ArcheryClass.MASTER_MEN);
            createCategory(realm, CX, Division.COMPOUND_BOW, ArcheryClass.SENIOR_MIX);
            createCategory(realm, CN, Division.COMPOUND_BOW, ArcheryClass.NOVEL_MIX);
            createCategory(realm, CIX, Division.COMPOUND_BOW, ArcheryClass.CHILD_MIX);

            createCategory(realm, SW, Division.STANDARD_BOW, ArcheryClass.SENIOR_WOMEN);
            createCategory(realm, SM, Division.STANDARD_BOW, ArcheryClass.SENIOR_MEN);
            createCategory(realm, SCW, Division.STANDARD_BOW, ArcheryClass.CADET_WOMEN);
            createCategory(realm, SCM, Division.STANDARD_BOW, ArcheryClass.CADET_MEN);
            createCategory(realm, SJW, Division.STANDARD_BOW, ArcheryClass.JUNIOR_WOMEN);
            createCategory(realm, SJM, Division.STANDARD_BOW, ArcheryClass.JUNIOR_MEN);
            createCategory(realm, SMW, Division.STANDARD_BOW, ArcheryClass.MASTER_WOMEN);
            createCategory(realm, SMM, Division.STANDARD_BOW, ArcheryClass.MASTER_MEN);
            createCategory(realm, SX, Division.STANDARD_BOW, ArcheryClass.SENIOR_MIX);
            createCategory(realm, SN, Division.STANDARD_BOW, ArcheryClass.NOVEL_MIX);
            createCategory(realm, SIX, Division.STANDARD_BOW, ArcheryClass.CHILD_MIX);

            createCategory(realm, BW, Division.BARE_BOW, ArcheryClass.SENIOR_WOMEN);
            createCategory(realm, BM, Division.BARE_BOW, ArcheryClass.SENIOR_MEN);
            createCategory(realm, BCW, Division.BARE_BOW, ArcheryClass.CADET_WOMEN);
            createCategory(realm, BCM, Division.BARE_BOW, ArcheryClass.CADET_MEN);
            createCategory(realm, BJW, Division.BARE_BOW, ArcheryClass.JUNIOR_WOMEN);
            createCategory(realm, BJM, Division.BARE_BOW, ArcheryClass.JUNIOR_MEN);
            createCategory(realm, BMW, Division.BARE_BOW, ArcheryClass.MASTER_WOMEN);
            createCategory(realm, BMM, Division.BARE_BOW, ArcheryClass.MASTER_MEN);
            createCategory(realm, BX, Division.BARE_BOW, ArcheryClass.SENIOR_MIX);
            createCategory(realm, BN, Division.BARE_BOW, ArcheryClass.NOVEL_MIX);
            createCategory(realm, BIX, Division.BARE_BOW, ArcheryClass.CHILD_MIX);

            createCategory(realm, IW, Division.INSTICTIVE_BOW, ArcheryClass.SENIOR_WOMEN);
            createCategory(realm, IM, Division.INSTICTIVE_BOW, ArcheryClass.SENIOR_MEN);
            createCategory(realm, LW, Division.LONGBOW, ArcheryClass.SENIOR_WOMEN);
            createCategory(realm, LM, Division.LONGBOW, ArcheryClass.SENIOR_MEN);
            createCategory(realm, IX, Division.INSTICTIVE_BOW, ArcheryClass.SENIOR_MIX);
            createCategory(realm, LX, Division.LONGBOW, ArcheryClass.SENIOR_MIX);
            createCategory(realm, IIX, Division.INSTICTIVE_BOW, ArcheryClass.CHILD_MIX);
            createCategory(realm, LIX, Division.LONGBOW, ArcheryClass.CHILD_MIX);
            createCategory(realm, IN, Division.INSTICTIVE_BOW, ArcheryClass.NOVEL_MIX);
            createCategory(realm, LN, Division.LONGBOW, ArcheryClass.NOVEL_MIX);

    }

    private static void createCategory(Realm realm,
                                       @Categories String name,
                                       @Division.Divisions String division,
                                       @ArcheryClass.ArcheryClasses String archeryClass) {
        Timber.i("Creating ArcheryClass %s", name);
        Category category = realm.createObject(Category.class, CategoryDataModule.UUIDGenerator.generate().toString());
        category.setName(name);
        category.setDivision(realm.where(Division.class).equalTo("name", division).findFirst());
        category.setArcheryClass(realm.where(ArcheryClass.class).equalTo("name", archeryClass).findFirst());
    }



}
