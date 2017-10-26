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
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 6/07/16.
 */

@SuppressWarnings("unused")
public class Meeting extends RealmObject implements IHasPrimarykey{

    @PrimaryKey
    private String UUID;

    private String name;
    private String description;
    private RealmList<Competition> meetingCompetitions;
    private boolean isOpen;

    public Meeting()
    {}

    public Meeting(String name, String description, boolean isOpen)
    {
        this.setUUID(CompetitionDataModule.UUIDGenerator.generate().toString());
        this.setName(name);
        this.setDescription(description);
        this.setOpen(isOpen);
    }

    @Override
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<Competition> getMeetingCompetitions() {
        return meetingCompetitions;
    }

    public void setMeetingCompetitions(RealmList<Competition> meetingCompetitions) {
        this.meetingCompetitions = meetingCompetitions;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
