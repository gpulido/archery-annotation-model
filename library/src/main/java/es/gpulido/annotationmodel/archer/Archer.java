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

import java.util.Date;
import java.util.List;

import es.gpulido.annotationmodel.Interfaces.ICanBeDeleted;
import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;

import es.gpulido.annotationmodel.category.Division;
import es.gpulido.annotationmodel.category.Level;
import es.gpulido.annotationmodel.helper.RealmHelper;
import es.gpulido.annotationmodel.helper.Utils;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 16/08/15.
 */
@SuppressWarnings("unused")
public class Archer extends RealmObject implements ICanBeDeleted, IHasPrimarykey{

    @PrimaryKey
    private String UUID;
    private boolean deleted;
    private String name;
    private String surname;
    private String email;
    private boolean overrideCategory;
    private String archeryLicense;
    private Date licenseDate;
    private Club club;
    private Gender gender;
    private int membershipNumber;
    private String membershipNumberString;
    private RealmList<Division> divisions;
    private boolean shotFieldManager;
    private String phoneNumber;

    private String signature;

    public static Archer CreateOrUpdateArcher(Realm realm,
                                          String name,
                                          String surname,
                                          boolean overrideCategory,
                                          String archeryLicense,
                                          Date licenseDate,
                                          Club club,
                                          Gender gender,
                                          int membershipNumber,
                                          String formatMemberShipNumber)
    {

        Archer archer = realm.where(Archer.class).equalTo("club.UUID", club.getUUID())
                .equalTo("membershipNumber", membershipNumber).findFirst();
        if (archer == null) {
            archer = new Archer();
            archer.setUUID(java.util.UUID.randomUUID().toString());
        }
        archer.setName(name);
        archer.setSurname(surname);
        archer.setOverrideCategory(overrideCategory);
        archer.setArcheryLicense(archeryLicense);
        archer.setLicenseDate(licenseDate);
        archer.setGender(gender);
        archer.setMembershipNumber(membershipNumber);
        archer.setMembershipNumberString("");
        if (membershipNumber != 0)
            archer.setMembershipNumberString(String.format(formatMemberShipNumber,membershipNumber));
        archer.setClub(club);
        return realm.copyToRealmOrUpdate(archer);
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getArcheryLicense() {
        return archeryLicense;
    }

    public void setArcheryLicense(String archeryLicense) {
        this.archeryLicense = archeryLicense;
    }

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public int getMembershipNumber() {
        return membershipNumber;
    }

    public void setMembershipNumber(int membershipNumber) {
        this.membershipNumber = membershipNumber;
    }

    public String getMembershipNumberString() {
        return membershipNumberString;
    }

    public void setMembershipNumberString(String membershipNumberString) {
        this.membershipNumberString = membershipNumberString;
    }

    public RealmList<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(RealmList<Division> divisions) {
        this.divisions = divisions;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isOverrideCategory() {
        return overrideCategory;
    }

    public void setOverrideCategory(boolean overrideCategory) {
        this.overrideCategory = overrideCategory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void delete(Realm realm)
    {
        realm.executeTransactionAsync(realm1 ->
                RealmHelper.getByPrimaryKey(realm1, Archer.class, getUUID())
                        .setDeleted(true));
    }

    @Override
    public void undoDelete(Realm realm) {

        realm.executeTransactionAsync(realm1 ->
                RealmHelper.getByPrimaryKey(realm1, Archer.class, getUUID())
                        .setDeleted(false));
    }


    public List<Level> getArcherLevel(Realm realm, Date date)
    {
        RealmResults<Level> levels = realm.where(Level.class).findAll();
        if (getLicenseDate() == null && !isOverrideCategory())
            return levels;
        if (isOverrideCategory())
            return levels.where().equalTo("name", Level.SENIOR).findAll();

        if (getLicenseDate() != null && Utils.getDiffYears(getLicenseDate(), date) > 1)
            return levels.where().equalTo("name", Level.SENIOR).findAll();
        return levels.where().equalTo("name", Level.NOVEL).findAll();


    }

    public String getClubImageResourceName() {
        if (getClub()!= null && getClub().getImageResourceName()!= null)
            return getClub().getImageResourceName();
        return null;

    }

    public boolean isShotFieldManager() {
        return shotFieldManager;
    }

    public void setShotFieldManager(boolean shotFieldManager) {
        this.shotFieldManager = shotFieldManager;
    }

    public static RealmResults<Archer> getShotFieldManagers(Realm realm)
    {
        return realm.where(Archer.class).equalTo("shotFieldManager", true).findAllAsync();
    }

    public String getSignature() {
        if (signature == null)
            return getMembershipNumberString();
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public static Archer getArcherOrDefault(Realm realm, String nSocio, Club defaultClub) {

        Archer archer = realm.where(Archer.class).equalTo("membershipNumber", Integer.parseInt(nSocio)).findFirst();
        //Create of an empty archer
        if (archer == null) {
            Club club = defaultClub;
            Gender male = Gender.getByName(realm, Gender.MALE);

            archer = Archer.CreateOrUpdateArcher(realm,
                    "Arquero",
                    "",
                    true,
                    null,
                    null,
                    club,
                    male,
                    Integer.parseInt(nSocio),
                    "%03d");
        }
        return archer;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
