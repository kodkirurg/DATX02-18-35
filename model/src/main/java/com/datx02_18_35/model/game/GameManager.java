package com.datx02_18_35.model.game;

import com.datx02_18_35.model.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Jonatan on 2018-03-07.
 */

public class GameManager {
    private List<Level> levels;
    private List<String> levelFilePaths;
    private Session currentSession;


    public GameManager(){
        levels = new ArrayList<>();
        levelFilePaths = new ArrayList<>();
        levels.add(Level.exampleLevel);
        try {
            Scanner input = new Scanner(new File(Config.LEVELS_CONFIG_FILENAME));
            while (input.hasNextLine()) {
                String levelPath = Config.LEVELS_PATH + input.nextLine();
                levelFilePaths.add(levelPath);
            }
            for (String s : levelFilePaths) {
                Level level = Level.createLevel(s);
                levels.add(level);
            }
        } catch (Level.LevelParseException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
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
