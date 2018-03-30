package com.datx02_18_35.model.level;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by robin on 2018-03-26.
 */

public class LevelCategory {
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
}
