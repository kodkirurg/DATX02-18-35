package com.datx02_18_35.model.game;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * Created by robin on 2018-03-15.
 */

public class UserData implements Serializable {

    private final HashMap<Level, LevelProgression> progression;


    public UserData(List<Level> levels) {
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
}
