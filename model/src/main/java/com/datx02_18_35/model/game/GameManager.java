package com.datx02_18_35.model.game;

import com.datx02_18_35.model.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.soap.SOAPBinding;

/**
 * Created by Jonatan on 2018-03-07.
 */

public class GameManager {
    private final List<Level> levels;
    private UserData userData;

    private Session currentSession;


    public GameManager(List<String> levelStrings) throws LevelParseException {
        levels = new ArrayList<>();
        for (String levelStr : levelStrings) {
            Level level = Level.parseLevel(levelStr);
            levels.add(level);
        }
        userData = new UserData(levels);
    }

    public byte[] saveUserData() {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutput objOut = null;
        byte[] byteArray = null;
        try {
            objOut = new ObjectOutputStream(byteOut);
            objOut.writeObject(userData);
            objOut.flush();
            byteArray = byteOut.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                byteOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Util.Log("Serializing user data, size=" + byteArray.length + "B");

        return byteArray;
    }

    public boolean loadUserData(byte[] data) {
        boolean success = false;
        ByteArrayInputStream byteIn = new ByteArrayInputStream(data);
        ObjectInput objIn = null;
        try {
            objIn = new ObjectInputStream(byteIn);
            Object obj = objIn.readObject();
            if (obj instanceof UserData) {
                userData = (UserData)obj;
                success = true;
            } else {
                throw new IllegalArgumentException("data byte array is not a valid UserData object.");
            }
        } catch (IOException | ClassNotFoundException | IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            if (objIn != null) {
                try {
                    objIn.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return success;
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

    public void quitLevel(boolean finished) throws IllegalGameStateException {
        assertSessionInProgress();
        if (finished) {
            LevelProgression progression = userData.getProgression(currentSession.getLevel());
            if (!progression.completed || progression.stepsApplied > currentSession.getStepsApplied()) {
                progression.stepsApplied = currentSession.getStepsApplied();
            }
            progression.completed = true;

        }
        currentSession = null;
    }

    public UserData getUserData() {
        return userData;
    }

    public Session getSession() throws IllegalGameStateException {
        assertSessionInProgress();
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

    public class LevelNotInListException extends Exception{
        private LevelNotInListException(String s){
            super(s);
        }
    }

}
