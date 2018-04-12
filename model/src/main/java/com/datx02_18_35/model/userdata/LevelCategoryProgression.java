package com.datx02_18_35.model.userdata;

import java.io.Serializable;

/**
 * Created by robin on 2018-04-10.
 */

public class LevelCategoryProgression implements Serializable {

    public final Status status;

    public LevelCategoryProgression(Status status) {
        this.status = status;
    }

    public enum Status {
        COMPLETED,
        UNLOCKED,
        LOCKED
    }
}
