package com.datx02_18_35.model.game;

import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.level.LevelCategory;
import com.datx02_18_35.model.level.LevelCollection;
import com.datx02_18_35.model.level.LevelProgression;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by robin on 2018-03-15.
 */

public class UserData implements Serializable {

    private final HashMap<Level, LevelProgression> progression;
    private final HashMap<LevelCategory, Boolean> categoryUnlocked;

    public UserData(LevelCollection levelCollection) {
        progression = new HashMap<>();
        categoryUnlocked = new HashMap<>();
        for (LevelCategory category : levelCollection.getCategories()) {
            categoryUnlocked.put(category, false);

            for (Level level : category.getLevels()) {
                progression.put(level, new LevelProgression());
            }
        }
    }

    public void markCaterygoryUnlocked(LevelCategory category) {
        categoryUnlocked.put(category, true);
    }

    public boolean isCategoryUnlocked(LevelCategory category) {
        Boolean unlocked = categoryUnlocked.get(category);
        if (unlocked == null) {
            unlocked = false;
            categoryUnlocked.put(category, false);
        }
        return unlocked;
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
