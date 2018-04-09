package com.datx02_18_35.model.level;

import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.expression.ExpressionParseException;
import com.datx02_18_35.model.game.UserData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by robin on 2018-03-26.
 */

public class LevelCollection {

    private static final String CATEGORY = "CATEGORY";
    private static final String NO_CATEGORY_NAME = "<NO CATEGORY>";
    private static final String LEVEL_LIST_FILENAME = "levels.txt";


    private final List<LevelCategory> categories;
    private final Map<Level, LevelCategory> levelCategoryMap;

    public LevelCollection(Map<String, String> configFiles) throws LevelParseException, ExpressionParseException {
        categories = parseLevelList(configFiles);
        levelCategoryMap = new HashMap<>();
        for (LevelCategory category : categories) {
            for (Level level : category.getLevels()) {
                levelCategoryMap.put(level, category);
            }
        }
    }

    public boolean contains(Level level) {
        return levelCategoryMap.containsKey(level);
    }

    public boolean isUnlocked(UserData userData, LevelCategory category) {
        return levelsUntilUnlocked(userData, category) > 0;
    }

    public boolean isUnlocked(UserData userData, Level level) {
        return levelsUntilUnlocked(userData, level) > 0;
    }

    /**
     * Checks whether a level is unlocked
     * @param userData
     * @param level
     * @return Returns 0 if category is unlocked, otherwise the number of levels to complete in the previous one
     */
    public int levelsUntilUnlocked(UserData userData, Level level) {
        return levelsUntilUnlocked(userData, levelCategoryMap.get(level));
    }

    /**
     * Checks whether a category is unlocked.
     * @param userData
     * @param category
     * @return Returns 0 if category is unlocked, otherwise the number of levels to complete in the previous one
     */
    public int levelsUntilUnlocked(UserData userData, LevelCategory category) {
        Iterator<LevelCategory> categoryIterator = categories.iterator();
        if (!categoryIterator.hasNext()) {
            throw new IllegalStateException("No level categories loaded!");
        }
        LevelCategory lastCategory = categoryIterator.next();
        if (lastCategory.equals(category)) {
            return 0; //The querried category is the first one.
        }
        while (categoryIterator.hasNext()) {
            LevelCategory nextCategory = categoryIterator.next();
            if (nextCategory.equals(category)) {
                return lastCategory.getLevelsLeftToUnlockNext(userData);
            }

            lastCategory = nextCategory;
        }
        throw new IllegalStateException("Category not in collection");
    }

    public List<LevelCategory> getCategories() {
        return categories;
    }

    public LevelCategory nextCategory(LevelCategory category) {
        Iterator<LevelCategory> categoryIterator = categories.iterator();
        while (categoryIterator.hasNext()) {
            if (categoryIterator.next().equals(category)) {
                if (categoryIterator.hasNext()) {
                    return categoryIterator.next();
                }
                else {
                    return null;
                }
            }
        }
        throw new IllegalStateException("Category now in collection");
    }

    public Level getNextLevel(Level level) {
        LevelCategory category = levelCategoryMap.get(level);
        return category.getNextLevel(level);
    }

    private static List<LevelCategory> parseLevelList(Map<String, String> configFiles) throws LevelParseException, ExpressionParseException {
        String levelList = configFiles.get(LEVEL_LIST_FILENAME);
        String[] lines = levelList.replaceFirst("[\n|\r]+", "").split("[\n|\r]+");
        Iterator<String> lineIterator = Arrays.asList(lines).iterator();

        List<LevelCategory> categories = new ArrayList<>();
        List<Level> categoryLevels = new ArrayList<>();
        String categoryName = NO_CATEGORY_NAME;

        int totalLevels = 0;

        while (lineIterator.hasNext()) {
            String line = lineIterator.next();
            if (line.startsWith(CATEGORY + " ")) {
                categories.add(new LevelCategory(categoryName, categoryLevels));
                categoryLevels = new ArrayList<>();
                categoryName = line.replaceFirst(CATEGORY + " ", "");
            }
            else {
                String levelText = configFiles.get(line);
                if (levelText == null) {
                    Util.log("Level file not found: " + line);
                    continue;
                }
                categoryLevels.add(Level.parseLevel(levelText));
                totalLevels += 1;
            }
        }
        categories.add(new LevelCategory(categoryName, categoryLevels));

        Util.log("Loaded levels! Categories: " + categories.size() + ", levels: " + totalLevels);

        return categories;
    }

}
