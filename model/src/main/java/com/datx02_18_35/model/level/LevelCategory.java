package com.datx02_18_35.model.level;

import com.datx02_18_35.model.Config;
import com.datx02_18_35.model.userdata.LevelProgression;
import com.datx02_18_35.model.userdata.UserData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by robin on 2018-03-26.
 */

public class LevelCategory implements Serializable {
    private static final int HASH_MAGIC_NUMBER = 1_425_169_241;

    private final String name;
    private final List<Level> levels;
    private final Set<Level> levelSet; //Used for quick lookup;
    private final Map<Level, Integer> levelIndexMap;

    LevelCategory(String name, List<Level> levels) {
        this.name = name;
        this.levels = levels;
        this.levelSet = new HashSet<>(levels);
        this.levelIndexMap = new HashMap<>();
        for (int i = 0; i < levels.size(); i++) {
            this.levelIndexMap.put(levels.get(i), i);
        }
    }

    public Iterable<Level> getLevels() {
        return levels;
    }

    public int getCompleted(UserData userData) {
        int completed = 0;
        for (Level level : levels) {
            LevelProgression progression = userData.getLevelProgression(level);
            if (progression.completed) {
                completed += 1;
            }
        }
        return completed;
    }

    public int getCount() {
        return levels.size();
    }

    int getLevelsLeftToUnlockNext(UserData userData) {
        int cutoff = (int)(Math.round(getCount() * (1.0 - Config.CATEGORY_UNLOCK_THRESHOLD_RATIO)));
        int notFinished = getCount() - getCompleted(userData);
        int left = notFinished - cutoff;
        return left >= 0 ? left : 0;
    }

    public Level getNextLevel(Level level) {
        Integer index = levelIndexMap.get(level);
        if (index == null) return null;
        if (index >= levels.size()-1) return null;
        return levels.get(index+1);
    }

    public Level getFirstLevel() {
        return levels.size() > 0 ? levels.get(0) : null;
    }

    public String getName() {
        return name;
    }

    public boolean contains(Level level) {
        return levelSet.contains(level);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof LevelCategory && this.name.equals(((LevelCategory) other).name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() * HASH_MAGIC_NUMBER;
    }
}
