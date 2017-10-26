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

package es.gpulido.annotationmodel.annotation;


import es.gpulido.annotationmodel.Interfaces.IHasPrimarykey;
import es.gpulido.annotationmodel.helper.RealmHelper;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by gpt on 13/11/14.
 */
public class Letter extends RealmObject implements IHasPrimarykey{
    @PrimaryKey
    private String UUID;
    private String letterSymbol;
    private Parapet parapet;
    private Contender contender;
    private boolean parapetAnnotator;

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getLetterSymbol() {
        return letterSymbol;
    }

    public void setLetterSymbol(String letterSymbol) {
        this.letterSymbol = letterSymbol;
    }

    public Parapet getParapet() {
        return parapet;
    }

    public void setParapet(Parapet parapet) {
        this.parapet = parapet;
    }

    public Contender getContender() {
        return contender;
    }

    public void setContender(Contender contender) {
        this.contender = contender;
    }

    public void clearLetter(Realm realm)
    {
        String localUUID = UUID;
        realm.executeTransactionAsync(realm1 -> {
            Letter letter = RealmHelper.getByPrimaryKey(realm1, Letter.class, localUUID);
            if (letter.getContender()!= null) {
                letter.getContender().removeContenderAnnotations(realm1);
                letter.getContender().setLetter(null);
            }
            letter.setContender(null);
        });
    }
    public void clearContender(Realm realm)
    {
        Contender previousContender = getContender();
        if (previousContender!= null) {
            previousContender.removeContenderAnnotations(realm);
            previousContender.setLetter(null);
        }
        setContender(null);
    }

    public boolean isAsigned()
    {
        return getContender() != null && !getContender().isDeleted();
    }

    public boolean isParapetAnnotator() {
        return parapetAnnotator;
    }

    public void setParapetAnnotator(boolean parapetAnnotator) {
        this.parapetAnnotator = parapetAnnotator;
    }
}
