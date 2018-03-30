package com.datx02_18_35.model.level;

import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.expression.ExpressionParseException;

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


    public List<LevelCategory> getCategories() {
        return categories;
    }

    public Level getNextLevel(Level level) {
        LevelCategory category = levelCategoryMap.get(level);
        Level nextLevel = category.getNextLevel(level);
        if (nextLevel != null) {
            return nextLevel;
        }

        for (int categoryIndex = categories.indexOf(category) + 1;
             categoryIndex < categories.size();
             categoryIndex++) {

            nextLevel = categories.get(categoryIndex).getFirstLevel();
            if (nextLevel != null) {
                return nextLevel;
            }
        }
        return null;


    }

    private static List<LevelCategory> parseLevelList(Map<String, String> configFiles) throws LevelParseException, ExpressionParseException {
        String levelList = configFiles.get(LEVEL_LIST_FILENAME);
        String[] lines = levelList.replaceFirst("[\n\r]+", "").split("[\n|\r]+");
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
                    Util.Log("Level file not found: " + line);
                    continue;
                }
                categoryLevels.add(Level.parseLevel(levelText));
                totalLevels += 1;
            }
        }
        categories.add(new LevelCategory(categoryName, categoryLevels));

        Util.Log("Loaded levels! Categories: " + categories.size() + ", levels: " + totalLevels);

        return categories;
    }

}
