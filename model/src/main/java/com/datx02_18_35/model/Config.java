package com.datx02_18_35.model;

/**
 * Created by robin on 2018-04-09.
 */

public class Config {
    public static final double CATEGORY_UNLOCK_THRESHOLD_RATIO = 0.7;

    public static final String ASSET_PATH_MODEL = "modelAssets";
    public static final String ASSET_PATH_CATEGORIES = ASSET_PATH_MODEL + "/category_names.txt";
    public static final String ASSET_PATH_LEVELS = ASSET_PATH_MODEL + "/levels";

    public static final boolean DEBUG_UNLOCK_ALL = false;
    public static final boolean DEBUG_RESET_PROGRESS = true;
}
