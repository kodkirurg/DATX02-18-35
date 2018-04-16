package com.datx02_18_35.model.level;

import com.datx02_18_35.model.Config;
import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.expression.ExpressionParseException;
import com.datx02_18_35.model.game.LevelNotAllowedException;
import com.datx02_18_35.model.userdata.LevelCategoryProgression;
import com.datx02_18_35.model.userdata.UserData;

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

    public void assertLevelIsUnlocked(UserData userData, Level level) throws LevelNotAllowedException {
        LevelCategory category = getCategoryFromLevel(level);
        LevelCategoryProgression categoryProgression = userData.getCategoryProgression(category);
        if (categoryProgression.status == LevelCategoryProgression.Status.LOCKED) {
            throw new LevelNotAllowedException(
                    level,
                    "Level is not unlocked. Category: " + category.getName() +
                            ", status: " + categoryProgression.status);
        }
    }

    public LevelCategory getCategoryFromLevel(Level level) {
        return levelCategoryMap.get(level);
    }

    public List<LevelCategory> getCategories() {
        return categories;
    }

    public LevelCategory previousCategory(LevelCategory category) {
        Iterator<LevelCategory> categoryIterator = categories.iterator();
        if (!categoryIterator.hasNext()) {
            throw new IllegalStateException("No categories in collection");
        }
        LevelCategory lastCategory = categoryIterator.next();
        if (lastCategory.equals(category)) {
            return null; //First category
        }
        while (categoryIterator.hasNext()) {
            LevelCategory currentCategory = categoryIterator.next();
            if (currentCategory.equals(category)) {
                return lastCategory;
            }
            lastCategory = currentCategory;
        }
        throw new IllegalArgumentException("Category not in collection");
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
        throw new IllegalArgumentException("Category not in collection");
    }

    public Level getNextLevel(Level level) {
        LevelCategory category = levelCategoryMap.get(level);
        return category.getNextLevel(level);
    }

    private static List<LevelCategory> parseLevelList(Map<String, String> configFiles) throws LevelParseException, ExpressionParseException {
        List<LevelCategory> categoryList = new ArrayList<>();

        String categoryListFile = configFiles.get(Config.ASSET_PATH_CATEGORIES);
        if (categoryListFile == null) {
            throw new LevelParseException("Could not find file: " + Config.ASSET_PATH_CATEGORIES);
        }
        String[] lines = categoryListFile.replaceFirst("[\n|\r]*", "").split("[\n|\r]+");

        for (String line : lines) {
            final String[] tokens = line.split("\\s+");
            if (tokens.length != 2) {
                throw new LevelParseException("Wrong number of arguments in category list file: " + Config.ASSET_PATH_CATEGORIES);
            }
            if (!tokens[0].equals("CATEGORY")) {
                throw new LevelParseException("Unknown token: " + tokens[0] + " in file: " + Config.ASSET_PATH_CATEGORIES);
            }
            final String categoryDirectory = Config.ASSET_PATH_LEVELS + "/" + tokens[1];
            categoryList.add(parseCategory(configFiles, categoryDirectory));
        }

        return categoryList;

    }

    private static LevelCategory parseCategory(final Map<String, String> configFiles, final String categoryDirectory) throws LevelParseException, ExpressionParseException {
        final String categoryInfoPath = categoryDirectory + "/" + Config.ASSET_FILE_CATEGORY_INFO;
        final String categoryInfo = configFiles.get(categoryInfoPath);
        if (categoryInfo == null) {
            throw new LevelParseException("Could not find file: " + categoryInfoPath);
        }
        String[] lines = categoryInfo.replaceFirst("[\n|\r]*", "").split("[\n|\r]+");
        String title = null;
        List<Level> levelList = new ArrayList<>();
        for (String line : lines) {
            if (line.startsWith("TITLE ")) {
                if (title != null) {
                    throw new LevelParseException("Encountered multiple TITLE tokens in file: " + categoryInfoPath);
                }
                title = line.replaceFirst("TITLE ", "");
            }
            else {
                String levelPath = categoryDirectory + "/" + line;
                String levelStr = configFiles.get(levelPath);
                if (levelStr == null) {
                    throw new LevelParseException("Could not find file: " + levelPath);
                }
                Level level = Level.parseLevel(levelStr);
                levelList.add(level);
            }
        }
        if (title == null) {
            throw new LevelParseException("No TITLE token found in file: " + categoryInfoPath);
        }
        return new LevelCategory(title, levelList);

    }
}