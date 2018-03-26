package com.datx02_18_35.model.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by robin on 2018-03-15.
 */

public class UserData implements Serializable {

    private final HashMap<Level, LevelProgression> progression;


    public UserData(Iterable<Level> levels) {
        progression = new HashMap<>();
        for (Level level : levels) {
            progression.put(level, new LevelProgression());
        }
    }

    public LevelProgression getProgression(Level level) {
        LevelProgression levelProgression = progression.get(level);
        if (levelProgression == null) {
            levelProgression = new LevelProgression();
            progression.put(level, levelProgression);
        }
        return levelProgression;
    }

    public Map<Level, LevelProgression> getProgressionMapReadOnly() {
        Map<Level, LevelProgression> map = new HashMap<>();
        for (Map.Entry<Level, LevelProgression> entry : progression.entrySet()) {
            map.put(entry.getKey(), entry.getValue().clone());
        }
        return map;
    }
}
