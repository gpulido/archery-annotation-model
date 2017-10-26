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

package es.gpulido.annotationmodel.serie;

import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.category.Category;
import es.gpulido.annotationmodel.category.Distance;
import es.gpulido.annotationmodel.category.Division;
import es.gpulido.annotationmodel.category.Level;
import es.gpulido.annotationmodel.target.FaceHelper;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by GGPT on 2/23/2017.
 */

public class Serie extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;
    @Index
    private String name;

    private RealmList<Category> categories;
    private RealmList<SerieRound> seriesRounds;

    public Serie(){}

    public Serie(String name)
    {
        this.UUID = SerieDataModule.UUIDGenerator.generate().toString();
        this.name = name;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public static void createDefaultData(Realm realm) {

            Timber.i("Creating SeriesRound");
            Timber.i("Generating 1440");

            Serie fita1440_1 =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            fita1440_1.setName("1440 - 90m 70m 50m 30m");
            fita1440_1.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.NINETY_M, FaceHelper.COMPLETA_122, 6, 6));
            fita1440_1.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SEVENTY_M, FaceHelper.COMPLETA_122, 6, 6));
            fita1440_1.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.FIFTY_M, FaceHelper.REDUCIDA_80_5, 6, 6));
            fita1440_1.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.THIRTY_M, FaceHelper.REDUCIDA_80_5, 6, 6));
            fita1440_1.categories.add(Category.getByName(realm, Category.RJM));
            fita1440_1.categories.add(Category.getByName(realm, Category.RM));
            fita1440_1.categories.add(Category.getByName(realm, Category.CJM));
            fita1440_1.categories.add(Category.getByName(realm, Category.CM));

            Serie fita1440_2 =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            fita1440_2.setName("1440 - 70m 60m 50m 30m");
            fita1440_2.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SEVENTY_M, FaceHelper.COMPLETA_122, 6, 6));
            fita1440_2.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SIXTY_M, FaceHelper.COMPLETA_122, 6, 6));
            fita1440_2.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.FIFTY_M, FaceHelper.REDUCIDA_80_5, 6, 6));
            fita1440_2.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.THIRTY_M, FaceHelper.REDUCIDA_80_5, 6, 6));
            fita1440_2.categories.add(Category.getByName(realm, Category.RCM));
            fita1440_2.categories.add(Category.getByName(realm, Category.RW));
            fita1440_2.categories.add(Category.getByName(realm, Category.RJW));
            fita1440_2.categories.add(Category.getByName(realm, Category.RMM));
            fita1440_2.categories.add(Category.getByName(realm, Category.CCM));
            fita1440_2.categories.add(Category.getByName(realm, Category.CW));
            fita1440_2.categories.add(Category.getByName(realm, Category.CJW));
            fita1440_2.categories.add(Category.getByName(realm, Category.CMM));

            Serie fita1440_3 =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            fita1440_3.setName("1440 - 60m 50m 40m 30m");
            fita1440_3.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SIXTY_M, FaceHelper.COMPLETA_122, 6, 6));
            fita1440_3.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.FIFTY_M, FaceHelper.COMPLETA_122, 6, 6));
            fita1440_3.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.FORTY_M, FaceHelper.REDUCIDA_80_5, 6, 6));
            fita1440_3.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.THIRTY_M, FaceHelper.REDUCIDA_80_5, 6, 6));
            fita1440_3.categories.add(Category.getByName(realm, Category.RCW));
            fita1440_3.categories.add(Category.getByName(realm, Category.RMW));
            fita1440_3.categories.add(Category.getByName(realm, Category.CCW));
            fita1440_3.categories.add(Category.getByName(realm, Category.CMW));

            Timber.i("Generating 900");
            Serie fita900 =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            fita900.setName("900");
            fita900.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SIXTY_M, FaceHelper.COMPLETA_122, 3, 10));
            fita900.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.FIFTY_M, FaceHelper.COMPLETA_122, 3, 10));
            fita900.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.FORTY_M, FaceHelper.COMPLETA_122, 3, 10));
            fita900.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.RECURVE_BOW).findAll());
            fita900.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.COMPOUND_BOW).findAll());

            Timber.i("Generating Olympic");
            Serie olympic =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            olympic.setName("Olympic");
            olympic.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SEVENTY_M, FaceHelper.COMPLETA_122, 6, 6));
            olympic.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SEVENTY_M, FaceHelper.COMPLETA_122, 6, 6));
            olympic.categories.add(Category.getByName(realm, Category.RW));
            olympic.categories.add(Category.getByName(realm, Category.RM));

            Serie olympicSmall =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            olympicSmall.setName("Olympic - Cadet and Master");
            olympicSmall.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SIXTY_M, FaceHelper.COMPLETA_122, 6, 6));
            olympicSmall.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SIXTY_M, FaceHelper.COMPLETA_122, 6, 6));
            olympicSmall.categories.add(Category.getByName(realm, Category.RCW));
            olympicSmall.categories.add(Category.getByName(realm, Category.RCM));
            olympicSmall.categories.add(Category.getByName(realm, Category.RMW));
            olympicSmall.categories.add(Category.getByName(realm, Category.RMM));

            Serie setentam =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            setentam.setName("70m - Recurve");
            setentam.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SEVENTY_M, FaceHelper.COMPLETA_122, 6, 6));
            setentam.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SEVENTY_M, FaceHelper.COMPLETA_122, 6, 6));
            setentam.categories.add(Category.getByName(realm, Category.RW));
            setentam.categories.add(Category.getByName(realm, Category.RM));

            Serie sesentam =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            sesentam.setName("60m - Recurve");
            sesentam.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SIXTY_M, FaceHelper.COMPLETA_122, 6, 6));
            sesentam.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.SIXTY_M, FaceHelper.COMPLETA_122, 6, 6));
            sesentam.categories.add(Category.getByName(realm, Category.RCW));
            sesentam.categories.add(Category.getByName(realm, Category.RCM));
            sesentam.categories.add(Category.getByName(realm, Category.RMW));
            sesentam.categories.add(Category.getByName(realm, Category.RMM));

            Serie cincuentam =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            cincuentam.setName("50m - Compound");
            cincuentam.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.FIFTY_M, FaceHelper.REDUCIDA_80_5, 6, 6));
            cincuentam.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.FIFTY_M, FaceHelper.REDUCIDA_80_5, 6, 6));
            cincuentam.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.COMPOUND_BOW).findAll());

            Serie estandar =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            estandar.setName("Standard");
            estandar.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.FIFTY_M, FaceHelper.COMPLETA_122, 3, 12));
            estandar.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.THIRTY_M, FaceHelper.COMPLETA_122, 3, 12));
            estandar.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.STANDARD_BOW).findAll());

            Serie infantil_18 =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            infantil_18.setName("Infantil");
            infantil_18.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.EIGHTEEN_M, FaceHelper.REDUCIDA_80_6, 6, 6));
            infantil_18.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.EIGHTEEN_M, FaceHelper.REDUCIDA_80_6, 6, 6));
            infantil_18.categories.add(Category.getByName(realm, Category.RIX));
            infantil_18.categories.add(Category.getByName(realm, Category.CIX));


            Serie traditional =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            traditional.setName("Instintive and LongBow");
            traditional.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.THIRTY_M, FaceHelper.FIELD_80, 6, 6));
            traditional.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.EIGHTEEN_M, FaceHelper.FIELD_40, 6, 6));
            traditional.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.LONGBOW)
                    .notEqualTo("archeryClass.level.name", Level.CHILD).findAll());
            traditional.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.INSTICTIVE_BOW)
                    .notEqualTo("archeryClass.level.name", Level.CHILD).findAll());

            Serie instintive_18 =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            instintive_18.setName("Instintive and LongBow");
            instintive_18.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.EIGHTEEN_M, FaceHelper.FIELD_80, 6, 6));
            instintive_18.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.EIGHTEEN_M, FaceHelper.FIELD_80, 6, 6));
            instintive_18.categories.add(Category.getByName(realm, Category.IIX));
            instintive_18.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.INSTICTIVE_BOW)
                    .equalTo("archeryClass.level.name", Level.CHILD).findAll());

            //Indoor
            Serie veinticincom =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            veinticincom.setName("25m");
            veinticincom.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.TWENTY_FIVE_M, FaceHelper.COMPLETA_60, 3, 10));
            veinticincom.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.TWENTY_FIVE_M, FaceHelper.COMPLETA_60, 3, 10));
            veinticincom.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.RECURVE_BOW).findAll());
            veinticincom.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.COMPOUND_BOW).findAll());

            Serie dieciochom =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            dieciochom.setName("18m");
            dieciochom.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.EIGHTEEN_M, FaceHelper.REDUCIDA_40_TRIPLE, 3, 10));
            dieciochom.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.EIGHTEEN_M, FaceHelper.REDUCIDA_40_TRIPLE, 3, 10));
            dieciochom.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.RECURVE_BOW).findAll());
            dieciochom.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.COMPOUND_BOW).findAll());

            Serie dieciochom_novel =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            dieciochom_novel.setName("18m Novel");
            dieciochom_novel.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.EIGHTEEN_M, FaceHelper.REDUCIDA_80_5, 3, 10));
            dieciochom_novel.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.EIGHTEEN_M, FaceHelper.REDUCIDA_80_5, 3, 10));
            dieciochom_novel.categories.add(Category.getByName(realm, Category.RN));
            dieciochom_novel.categories.add(Category.getByName(realm, Category.CN));

            Serie combinada =  realm.createObject(Serie.class, SerieDataModule.UUIDGenerator.generate().toString());
            combinada.setName("Combined");
            combinada.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.TWENTY_FIVE_M, FaceHelper.REDUCIDA_60_TRIPLE, 3, 10));
            combinada.seriesRounds.add(SerieRound.findOrCreate(realm, Distance.EIGHTEEN_M, FaceHelper.REDUCIDA_40_TRIPLE, 3, 10));
            combinada.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.RECURVE_BOW).findAll());
            combinada.categories.addAll(realm.where(Category.class).equalTo("division.name", Division.COMPOUND_BOW).findAll());

    }




    public RealmList<Category> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<Category> categories) {
        this.categories = categories;
    }

    public RealmList<SerieRound> getSeriesRounds() {
        return seriesRounds;
    }

    public void setSeriesRounds(RealmList<SerieRound> seriesRounds) {
        this.seriesRounds = seriesRounds;
    }

    public static Serie getByName(Realm realm, String serieName) {
        return realm.where(Serie.class).equalTo("name", serieName).findFirst();
    }
}
