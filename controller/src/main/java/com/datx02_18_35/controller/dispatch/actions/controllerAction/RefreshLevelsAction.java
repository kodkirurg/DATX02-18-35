package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.level.LevelCollection;
import com.datx02_18_35.model.level.LevelProgression;

import java.util.Map;

/**
 * Created by robin on 2018-03-20.
 */

public class RefreshLevelsAction extends Action {
    public final LevelCollection levelCollection;
    public final Map<Level, LevelProgression> progressionMap;

    public RefreshLevelsAction(LevelCollection levelCollection, Map<Level, LevelProgression> progressionMap) {
        if (levelCollection == null) {
            throw new IllegalArgumentException("levelList can't be null");
        }
        if (progressionMap == null) {
            throw new IllegalArgumentException("progressionMap can't be null");
        }
        this.levelCollection = levelCollection;
        this.progressionMap = progressionMap;
    }
}
