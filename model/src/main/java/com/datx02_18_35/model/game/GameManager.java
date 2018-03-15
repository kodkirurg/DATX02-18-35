package com.datx02_18_35.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jonatan on 2018-03-07.
 */

public class GameManager {
    private List<Level> levels;
    private Session currentSession;


    public GameManager(List<String> levelStrings) throws LevelParseException {
        levels = new ArrayList<>();
        for (String levelStr : levelStrings) {
            levels.add(Level.parseLevel(levelStr));
        }

    }


    public List<Level> getLevels(){
        return new ArrayList<Level>(levels);
    }

    public void startLevel(Level level) throws LevelNotInListException, IllegalGameStateException {
        assertSessionNotInProgress();
        if(!levels.contains(level)) {
            throw new LevelNotInListException("Level is not in GameManagers list of levels");
        }
        currentSession = new Session(level);
    }

    public void quitLevel() throws IllegalGameStateException {
        assertSessionInProgress();
        currentSession = null;
    }

    public Session getSession() throws IllegalGameStateException {
        assertSessionInProgress();
        return currentSession;
    }

    public void assertSessionNotInProgress() throws IllegalGameStateException {
        if (currentSession != null) {
            throw new IllegalGameStateException("Session already in progress!");
        }
    }

    public void assertSessionInProgress() throws IllegalGameStateException {
        if (currentSession == null) {
            throw new IllegalGameStateException("No session in progress!");
        }
    }

    public class LevelNotInListException extends Exception{
        private LevelNotInListException(String s){
            super(s);
        }
    }
}
