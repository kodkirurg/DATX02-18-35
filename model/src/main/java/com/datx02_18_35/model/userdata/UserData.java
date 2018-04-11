package com.datx02_18_35.model.userdata;

import com.datx02_18_35.model.Config;
import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.level.LevelCategory;
import com.datx02_18_35.model.level.LevelCollection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
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
        for (LevelCategory category : levelCollection.getCategories()) {
            categoryProgressionMap.put(category, new LevelCategoryProgression(LevelCategoryProgression.Status.LOCKED));
            for (Level level : category.getLevels()) {
                levelProgressionMap.put(level, new LevelProgression());
            }
        }
        LevelCategory firstCategory = levelCollection.getCategories().get(0);
        categoryProgressionMap.put(firstCategory, new LevelCategoryProgression(LevelCategoryProgression.Status.UNLOCKED));

        checkDebugSettings();
    }

    public static byte[] saveUserData(UserData userData) {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutput objOut = null;
        byte[] byteArray = null;
        try {
            objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(userData);
            objOut.flush();
            byteArray = byteOut.toByteArray();
            Util.log("Serializing user data, size=" + byteArray.length + "B");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return byteArray;
    }

    public static UserData loadUserData(LevelCollection levelCollection, byte[] data) {
        if (Config.DEBUG_RESET_PROGRESS) {
            //Ignore read input
            return new UserData(levelCollection);
        }
        UserData userData = null;
        ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
        ObjectInput objIn = null;
        try {
            objIn = new ObjectInputStream(byteIn);
            Object obj = objIn.readObject();
            if (obj instanceof UserData) {
                userData = (UserData)obj;
            } else {
                throw new IllegalArgumentException("userData byte array is not an instance of the UserData class");
            }
        } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
            Util.log("userData byte array is invalid, falling back to default values." +
                    "The following exception was caught: \n" + e);
        } finally {
            if (objIn != null) {
                try {
                    objIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (userData == null) {
            userData = new UserData(levelCollection);
        } else {
            for (LevelCategory category : levelCollection.getCategories()) {
                userData.getCategoryProgression(category); // Ignore result, only make sure it's filled
                for (Level level : category.getLevels()) {
                    userData.getLevelProgression(level); // Ignore result, only make sure it's filled
                }
            }
        }
        userData.checkDebugSettings();
        return userData;
    }

    private void checkDebugSettings() {
        if (Config.DEBUG_UNLOCK_ALL) {
            for (LevelCategory category : categoryProgressionMap.keySet()) {
                categoryProgressionMap.put(category, new LevelCategoryProgression(LevelCategoryProgression.Status.UNLOCKED));
            }
        }
    }

    public void logCategoryProgression() {
        for (Map.Entry<LevelCategory, LevelCategoryProgression> entry : categoryProgressionMap.entrySet()) {
            LevelCategory cat = entry.getKey();
            LevelCategoryProgression catProg = entry.getValue();
            //Util.log(cat.getName() + " " + catProg.status);
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
        Util.log("previous=" + levelProgression.stepsApplied + "\nnew="+stepsApplied);
        if (levelProgression.completed && stepsApplied > levelProgression.stepsApplied) {
            Util.log("Testing");
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
