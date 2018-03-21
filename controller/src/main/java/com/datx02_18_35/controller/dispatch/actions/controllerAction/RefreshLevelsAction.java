package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;
import com.datx02_18_35.model.game.Level;
import com.datx02_18_35.model.game.LevelProgression;

import java.util.List;
import java.util.Map;

/**
 * Created by robin on 2018-03-20.
 */

public class RefreshLevelsAction extends Action {
    public final List<Map.Entry<Level,LevelProgression>> levelList;

    public RefreshLevelsAction(List<Map.Entry<Level,LevelProgression>> levelList) {
        if (levelList == null) {
            throw new IllegalArgumentException("levelList can't be null");
        }
        this.levelList = levelList;
    }
}
