package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.level.LevelCategory;
import com.datx02_18_35.model.userdata.LevelCategoryProgression;
import com.datx02_18_35.model.level.LevelCollection;
import com.datx02_18_35.model.userdata.LevelProgression;

import java.util.Map;

/**
 * Created by robin on 2018-03-20.
 */

public class RefreshLevelsAction extends Action {
    public final LevelCollection levelCollection;
    public final Map<Level, LevelProgression> levelProgressionMap;
    public final Map<LevelCategory, LevelCategoryProgression> categoryProgressionMap;

    public RefreshLevelsAction(
            LevelCollection levelCollection,
            Map<Level, LevelProgression> levelProgressionMap,
            Map<LevelCategory, LevelCategoryProgression> categoryProgressionMap) {
        if (levelCollection == null) {
            throw new IllegalArgumentException("levelCollection can't be null");
        }
        if (levelProgressionMap == null) {
            throw new IllegalArgumentException("levelProgressionMap can't be null");
        }
        if (categoryProgressionMap == null) {
            throw new IllegalArgumentException("categoryProgressionMap can't be null");
        }
        this.levelCollection = levelCollection;
        this.levelProgressionMap = levelProgressionMap;
        this.categoryProgressionMap = categoryProgressionMap;
    }
}
