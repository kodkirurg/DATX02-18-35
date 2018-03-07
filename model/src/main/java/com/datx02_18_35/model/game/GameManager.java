package com.datx02_18_35.model.game;

import com.datx02_18_35.model.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Jonatan on 2018-03-07.
 */

public class GameManager {
    private List<Level> levels;
    private List<String> levelFilePaths;


    public GameManager(){
        levelFilePaths = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(Config.LEVELS_CONFIG_FILENAME));
            while (input.hasNextLine()) {
                levelFilePaths.add(input.nextLine());
            }
            for (String s : levelFilePaths) {
                levels.add(Level.createLevel(s));
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
        return levels;
    }

    public Session startLevel(){
        throw new NotImplementedException();
    }

    public boolean quitLevel(){
        throw new NotImplementedException();
    }

}
