package com.datx02_18_35.model.game;

import com.datx02_18_35.model.Config;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Jonatan on 2018-03-07.
 */

public class GameManager {
    private List<String> levelFilePaths;


    public GameManager(){
        try {
            Scanner input = new Scanner(new File(Config.LEVELS_CONFIG_FILENAME));
        }
        catch (FileNotFoundException e){

        }

    }
}
