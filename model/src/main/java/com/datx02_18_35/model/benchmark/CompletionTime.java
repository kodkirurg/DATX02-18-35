package com.datx02_18_35.model.benchmark;

import com.datx02_18_35.model.level.Level;

/**
 * Created by robin on 2018-04-16.
 */

public class CompletionTime {
    public final int id;
    public final Level level;
    public final long time;

    public CompletionTime(int id, Level level, long time) {
        this.id = id;
        this.level = level;
        this.time = time;
    }

    @Override
    public String toString() {
        return "id=" + id + ", seconds=" + (time / 1000) + ", levelTitle=" + level.title;
    }
}
