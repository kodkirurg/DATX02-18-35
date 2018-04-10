package com.datx02_18_35.model.level;

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

    private static final String CATEGORY = "CATEGORY";
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
        String levelList = configFiles.get(LEVEL_LIST_FILENAME);
        String[] lines = levelList.replaceFirst("[\n|\r]+", "").split("[\n|\r]+");
        Iterator<String> lineIterator = Arrays.asList(lines).iterator();

        List<LevelCategory> categories = new ArrayList<>();
        List<Level> categoryLevels = new ArrayList<>();
        if (!lineIterator.hasNext()) {
            throw new LevelParseException("No categories found in " + LEVEL_LIST_FILENAME);
        }
        String firstLine = lineIterator.next();
        if (!firstLine.startsWith(CATEGORY + " ")) {
            throw new LevelParseException("First line in " + LEVEL_LIST_FILENAME + " must be a category");
        }
        String categoryName = firstLine.replaceFirst(CATEGORY + " ", "");

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
