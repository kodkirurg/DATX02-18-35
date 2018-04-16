package com.datx02_18_35.model.game;

import com.datx02_18_35.model.Config;
import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.benchmark.UserBenchMark;
import com.datx02_18_35.model.level.Level;
import com.datx02_18_35.model.level.LevelCategory;
import com.datx02_18_35.model.level.LevelCollection;
import com.datx02_18_35.model.level.LevelParseException;
import com.datx02_18_35.model.userdata.LevelProgression;
import com.datx02_18_35.model.expression.ExpressionParseException;
import com.datx02_18_35.model.userdata.UserData;

import java.util.Map;

/**
 * Created by Jonatan on 2018-03-07.
 */

public class GameManager {
    private final LevelCollection levelCollection;
    private final UserData userData;
    private Session currentSession;

    private UserBenchMark userBenchMark;

    public GameManager(Map<String, String> configFiles, byte[] userDataBytes) throws LevelParseException, ExpressionParseException {
        if (configFiles == null) {
            throw new IllegalArgumentException("configFiles can't be null");
        }
        if (userDataBytes == null) {
            throw new IllegalArgumentException("userDataBytes can't be null");
        }
        this.levelCollection = new LevelCollection(configFiles);
        UserData loadedUserData = UserData.loadUserData(levelCollection, userDataBytes);
        if (loadedUserData != null) {
            userData = loadedUserData;
        }
        else {
            userData = new UserData(levelCollection);
        }
        userData.logCategoryProgression();

        if (Config.USER_BENCHMARK) {
            userBenchMark = new UserBenchMark();
        }
        else {
            userBenchMark = null;
        }
    }

    public GameManager(Map<String, String> configFiles) throws LevelParseException, ExpressionParseException {
        if (configFiles == null) {
            throw new IllegalArgumentException("configFiles can't be null");
        }
        levelCollection = new LevelCollection(configFiles);
        userData = new UserData(levelCollection);
        userData.logCategoryProgression();
    }




    public LevelCollection getLevelCollection() {
        return levelCollection;
    }

    public void startLevel(Level level) throws LevelNotInListException, IllegalGameStateException, LevelNotAllowedException {
        assertSessionNotInProgress();
        levelCollection.assertLevelIsUnlocked(userData, level);
        if(!levelCollection.contains(level)) {
            throw new LevelNotInListException("Level is not in GameManager's list of levels");
        }
        currentSession = new Session(level);
        Util.log("Starting new level...\nTitle=" + level.title + ",\nDescription=\n" + level.description);

        if (Config.USER_BENCHMARK) {
            userBenchMark.logLevelStarted(level);
        }
    }

    public void quitLevel() throws IllegalGameStateException {
        assertSessionInProgress();

        currentSession = null;
    }

    public VictoryInformation checkWin() {
        if (!currentSession.checkWin()) {
            return null;
        }

        if (Config.USER_BENCHMARK) {
            userBenchMark.logLevelCompleted();
        }

        LevelProgression previousProgression = userData.getLevelProgression(currentSession.getLevel());
        int previousScore = previousProgression.stepsApplied;
        int newScore = currentSession.getStepsApplied();

        LevelCategory unlockedCategory = userData.markLevelCompleted(
                levelCollection,
                currentSession.getLevel(),
                newScore);

        VictoryInformation victoryInformation = new VictoryInformation(
                previousScore,
                newScore,
                hasNextLevel(),
                currentSession.getLevel().goal,
                unlockedCategory);

        return victoryInformation;
    }




    public boolean hasNextLevel(){
        Level lastLevel = currentSession.getLevel();
        return levelCollection.getNextLevel(lastLevel) != null;
    }

    public void startNextLevel() throws IllegalGameStateException, LevelNotAllowedException {
        assertSessionInProgress();
        if (!currentSession.checkWin()) {
            throw new IllegalGameStateException("Can't proceed to next level unless the current session is finished");
        }
        Level nextLevel = levelCollection.getNextLevel(currentSession.getLevel());
        if (nextLevel == null) {
            throw new IllegalGameStateException("Can't proceed to next level as there are no more left");
        }
        levelCollection.assertLevelIsUnlocked(userData, nextLevel);

        quitLevel();
        try {
            startLevel(nextLevel);
        } catch (LevelNotInListException e) {
            throw new IllegalGameStateException("Unknown level fetched from LevelCollection. This really should not happen");
        }

    }

    public UserData getUserData() {
        return userData;
    }

    public Session getSession() throws IllegalGameStateException {
        return currentSession;
    }

    public boolean isSessionInProgress() {
        return currentSession != null;
    }

    public void assertSessionNotInProgress() throws IllegalGameStateException {
        if (currentSession != null) {
            throw new IllegalGameStateException("Session in progress!");
        }
    }

    public void assertSessionInProgress() throws IllegalGameStateException {
        if (currentSession == null) {
            throw new IllegalGameStateException("No session in progress!");
        }
    }

}
