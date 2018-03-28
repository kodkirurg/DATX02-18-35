package com.datx02_18_35.controller.dispatch.actions.controllerAction;

import com.datx02_18_35.controller.dispatch.actions.Action;
import java.util.Map;

/**
 * Created by raxxor on 2018-03-22.
 */

public class RefreshSymbolMap extends Action {
    public Map<String,String> symbolMap;

    public RefreshSymbolMap(Map<String,String> symbolMap) {
        this.symbolMap=symbolMap;
    }
}
