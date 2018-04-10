package com.datx02_18_35.model.userdata;

import com.datx02_18_35.model.Config;
import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.level.LevelCategory;
import com.datx02_18_35.model.level.LevelCollection;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by robin on 2018-03-15.
 */

public class UserData implements Serializable {

    private final Map<Level, LevelProgression> levelProgressionMap;
    private final Map<LevelCategory, LevelCategoryProgression> categoryProgressionMap;

    public UserData(LevelCollection levelCollection) {
        levelProgressionMap = new HashMap<>();
        categoryProgressionMap = new HashMap<>();
        Iterator<LevelCategory> categoryIterator = levelCollection.getCategories().iterator();
        if (categoryIterator.hasNext()) {
            LevelCategory firstCategory = categoryIterator.next();
            categoryProgressionMap.put(
                    firstCategory,
                    new LevelCategoryProgression(LevelCategoryProgression.Status.UNLOCKED));

            while (categoryIterator.hasNext()) {
                LevelCategory category = categoryIterator.next();
                categoryProgressionMap.put(
                        category,
                        new LevelCategoryProgression(LevelCategoryProgression.Status.LOCKED));
                for (Level level : category.getLevels()) {
                    levelProgressionMap.put(level, new LevelProgression());
                }
            }
        }
    }

    /**
     *
     * @param collection
     * @param level
     * @param stepsApplied
     * @return If a new category is unlocked as a result, it is returned, otherwise null
     */
    public LevelCategory markLevelCompleted(LevelCollection collection, Level level, int stepsApplied) {
        if (collection == null) {
            throw new IllegalArgumentException("collection can't be null");
        }
        if (level == null) {
            throw new IllegalArgumentException("level can't be null");
        }
        if (stepsApplied < 0) {
            throw new IllegalArgumentException("stepsApplied can't be negative");
        }

        LevelCategory category = collection.getCategoryFromLevel(level);
        LevelProgression levelProgression = getLevelProgression(level);

        if (levelProgression.completed && stepsApplied <= levelProgression.stepsApplied) {
            return null;
        }

        levelProgression = new LevelProgression(stepsApplied);
        levelProgressionMap.put(level, levelProgression);
        LevelCategoryProgression categoryProgression = getCategoryProgression(category);
        switch (categoryProgression.status) {
            case LOCKED:
                throw new IllegalStateException("Can't complete a level of a locked category");
            case COMPLETED:
                return null; // Category already completed, nothing more to do
            case UNLOCKED:
                int completedLevels = category.getCompleted(this);
                int totalLevels = category.getCount();
                if (completedLevels == totalLevels) {
                    categoryProgression = new LevelCategoryProgression(LevelCategoryProgression.Status.COMPLETED);
                    categoryProgressionMap.put(category, categoryProgression);
                }
                LevelCategory nextCategory = collection.nextCategory(category);
                LevelCategoryProgression nextCategoryProgression = getCategoryProgression(nextCategory);
                switch (nextCategoryProgression.status) {
                    case COMPLETED:
                    case UNLOCKED:
                        return null; // Next category already unlocked nothing more to do
                    case LOCKED:
                        int threshold = (int)Math.round(Config.CATEGORY_UNLOCK_THRESHOLD_RATIO*totalLevels);
                        if (completedLevels >= threshold) {
                            nextCategoryProgression = new LevelCategoryProgression(LevelCategoryProgression.Status.UNLOCKED);
                            categoryProgressionMap.put(nextCategory, nextCategoryProgression);
                            return nextCategory;
                        }
                        else {
                            return null; // Next category not yet unlocked
                        }
                    default:
                        throw new IllegalStateException("Unknown category status: " + nextCategoryProgression.status);
                }
            default:
                throw new IllegalStateException("Unknown category status: " + categoryProgression.status);
        }
    }


    public LevelCategoryProgression getCategoryProgression(LevelCategory category) {
        LevelCategoryProgression categoryProgression = categoryProgressionMap.get(category);
        if (categoryProgression == null) {
            categoryProgression = new LevelCategoryProgression(LevelCategoryProgression.Status.LOCKED);
            categoryProgressionMap.put(category, categoryProgression);
        }
        return categoryProgression;
    }

    public LevelProgression getLevelProgression(Level level) {
        LevelProgression levelProgression = levelProgressionMap.get(level);
        if (levelProgression == null) {
            levelProgression = new LevelProgression();
            levelProgressionMap.put(level, levelProgression);
        }
        return levelProgression;
    }

    public Map<Level, LevelProgression> getLevelProgressionMap() {
        return new HashMap<>(levelProgressionMap);
    }

    public Map<LevelCategory, LevelCategoryProgression> getCategoryProgressionMap() {
        return new HashMap<>(categoryProgressionMap);
    }
}
