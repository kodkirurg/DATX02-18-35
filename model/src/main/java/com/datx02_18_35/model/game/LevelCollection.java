package com.datx02_18_35.model.game;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by robin on 2018-03-26.
 */

public class LevelCollection {
    private final Map<Integer, LevelCategory> categories;

    public LevelCollection() {
        categories = new HashMap<>();
    }

    public void add(Level level) {
        LevelCategory category = categories.get(level.difficulty);
        if (category == null) {
            category = new LevelCategory(level.difficulty);
            categories.put(level.difficulty, category);
        }
        category.add(level);
    }

    public Iterable<LevelCategory> getCategories() {
        return this.categories.values();
    }

    public LevelCategory getCategory(int difficulty) {
        return categories.get(difficulty);
    }

    public LevelCategory getNextCategory(int difficulty) {
        int lowestDifficulty = Integer.MAX_VALUE;
        for (LevelCategory category : categories.values()) {
            if (category.getDifficulty() > difficulty
                && category.getDifficulty() < lowestDifficulty) {
                lowestDifficulty = category.getDifficulty();
            }
        }
        if (lowestDifficulty == Integer.MAX_VALUE) {
            return null;
        }
        else {
            return getCategory(lowestDifficulty);
        }
    }

    public Level getNextLevel(Level lastLevel) {
        LevelCategory category = categories.get(lastLevel.difficulty);
        Iterator<Level> levelIterator = category.getLevels().iterator();
        while (levelIterator.hasNext()) {
            if (levelIterator.next().equals(lastLevel)) {
                if (levelIterator.hasNext()) {
                    return levelIterator.next();
                }
                else {
                    LevelCategory nextCategory = getNextCategory(lastLevel.difficulty);
                    if (nextCategory == null) {
                        return null;
                    }
                    Iterator<Level> nextLevels = nextCategory.getLevels().iterator();
                    if (!nextLevels.hasNext()) {
                        return null;
                    }
                    return nextLevels.next();
                }
            }
        }
        throw new IllegalStateException("Current level is not in LevelCollection, this should never happen!");
    }

    public boolean contains(Level level) {
        for (Level otherLevel : getAllLevels()) {
            if (otherLevel.equals(level)) {
                return true;
            }
        }
        return false;
    }

    public Iterable<Level> getAllLevels() {
        return new Iterable<Level>() {
            @Override
            public Iterator<Level> iterator() {
                return new Iterator<Level>() {
                    private Iterator<LevelCategory> categoryIterator = getCategories().iterator();
                    private Iterator<Level> levelIterator = null;

                    @Override
                    public boolean hasNext() {
                        if (levelIterator != null && levelIterator.hasNext()) {
                            return true;
                        }
                        else {
                            return categoryIterator.hasNext();
                        }
                    }

                    @Override
                    public Level next() {
                        if (levelIterator == null || !levelIterator.hasNext()) {
                            levelIterator = categoryIterator.next().getLevels().iterator();
                        }
                        return levelIterator.next();
                    }
                };
            }
        };
    }
}
