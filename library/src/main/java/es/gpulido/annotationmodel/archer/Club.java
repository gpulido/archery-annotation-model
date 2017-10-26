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

package es.gpulido.annotationmodel.archer;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.Arrays;
import java.util.List;

import es.gpulido.annotationmodel.Interfaces.ICanBeDeleted;
import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.helper.RealmHelper;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 16/08/15.
 */
@SuppressWarnings("unused")
public class Club extends RealmObject implements IHasPrimarykey, ICanBeDeleted {

    @PrimaryKey
    private String UUID;
    private boolean deleted;
    private boolean defaultValue;
    private String name;
    private String imageResourceName;
    private String clubMail;
    private RealmList<ShotField> shotFields;


    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getImageResourceName() {
        return imageResourceName;
    }

    public void setImageResourceName(String imageResourceName) {
        this.imageResourceName = imageResourceName;
    }

    public RealmList<ShotField> getShotFields() {
        return shotFields;
    }

    public void setShotField(RealmList<ShotField> shotField) {
        this.shotFields = shotFields;
    }

    @Override
    public void delete(Realm realm)
    {
        realm.executeTransactionAsync(realm1 ->
                RealmHelper.getByPrimaryKey(realm1, Club.class, getUUID())
                        .setDeleted(true));
    }

    @Override
    public void undoDelete(Realm realm) {
        realm.executeTransactionAsync(realm1 ->
                RealmHelper.getByPrimaryKey(realm1, Club.class, getUUID())
                        .setDeleted(false));
    }

    public String getSystemImageResourcePath()
    {
        return "android_asset/clubs_images/" +getImageResourceName()+".png";
    }

    public String getDefaultImageFileName()
    {
        return "club_" + getName().replace(" ","_").toLowerCase();
    }

    //TODO: Add clubShortName (3 characters max)
    public String getShortName()
    {
        List<String> words = Arrays.asList(getName()
                .replace("arqueros", "")
                .replace("de", "")
                .split(" "));
        if (words.size() == 1) return words.get(0).substring(0,1);
        return Stream.of(words)
                .filter(s -> !s.equals(""))
                .map(s -> s.substring(0, 1).toUpperCase())
                .map(Object::toString)
                .collect(Collectors.joining(""));
    }

    public String getClubMail() {
        return clubMail;
    }

    public void setClubMail(String clubMail) {
        this.clubMail = clubMail;
    }
}
