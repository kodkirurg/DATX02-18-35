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
        levelFilePaths = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(Config.LEVELS_CONFIG_FILENAME));
            while (input.hasNextLine()) {
                levelFilePaths.add(input.nextLine());
            }
            for (String s : levelFilePaths) {
                Level level = Level.createLevel(s);
                levels.add(level);
            }
        }
        catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
        catch (Level.LevelParseException e){
            System.out.println(e.getMessage());
        }
    }


    public List<Level> getLevels(){
        return new ArrayList<Level>(levels);
    }

    public Session startLevel(Level level) throws LevelNotInListException{
        if(levels.contains(level)){
            currentSession = new Session(level);
            return currentSession;
        }
        throw new LevelNotInListException("Level is not in GameManagers list of levels");
    }

    public boolean quitLevel(){
        if(currentSession!=null){
            currentSession=null;
            return true;
        }
        return false;


    }

    public boolean finishCompleteLevel(){
        if(currentSession.getLevel().isLevelComplete()){
            this.quitLevel();
            return true;
        }
        return false;
    }


    private class LevelNotInListException extends Exception{
        private LevelNotInListException(String s){
            super(s);
        }
    }
}
