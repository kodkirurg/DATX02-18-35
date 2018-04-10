package com.datx02_18_35.model.userdata;

import com.datx02_18_35.model.level.LevelCategory;
import com.datx02_18_35.model.level.LevelCollection;

/**
 * Created by robin on 2018-04-10.
 */

public class LevelCategoryProgression {

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
