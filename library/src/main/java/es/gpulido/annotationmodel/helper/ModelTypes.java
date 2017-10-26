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

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by gpt on 17/07/15.
 */
public class ModelTypes {

    @StringDef({TOURNAMENT,
            WARMING,
            ARROW,
            TIMER
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TimerTypes{}
    public static final String TOURNAMENT = "Tournament_timer";
    public static final String WARMING = "Warming_timer";
    public static final String ARROW = "arrow_timer";
    public static final String TIMER = "normal_timer";

    @StringDef({
            T_NONE,
            T_COMPETITION,
            T_SUMMER_RANKING,
            T_WINTER_RANKING,
            T_TRAINING
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface TournamentTypes{}
    public static final String T_NONE = "Tournament_none";
    public static final String T_COMPETITION = "Competition_tournament";
    public static final String T_SUMMER_RANKING = "Summer_ranking_tournament";
    public static final String T_WINTER_RANKING = "Winter_ranking_tournament";
    public static final String T_TRAINING = "Training_tournament";


}
