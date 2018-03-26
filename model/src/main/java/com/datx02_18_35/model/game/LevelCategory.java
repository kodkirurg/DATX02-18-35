package com.datx02_18_35.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 2018-03-26.
 */

public class LevelCategory {
    private final int difficulty;
    private final List<Level> levels;

    public LevelCategory(int difficulty) {
        this.difficulty = difficulty;
        this.levels = new ArrayList<>();
    }

    public void add(Level level) {
        if (difficulty != level.difficulty) {
            throw new IllegalArgumentException(
                    "Difficulty of level (" + level.difficulty + ") " +
                    "does not match difficulty of category (" + this.difficulty + ").");
        }
        levels.add(level);
    }

    public Iterable<Level> getLevels() {
        return levels;
    }

    public int getDifficulty() {
        return this.difficulty;
    }
}
