package com.datx02_18_35.model.benchmark;

import com.datx02_18_35.model.Util;
import com.datx02_18_35.model.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by robin on 2018-04-16.
 */

public class UserBenchMark {
    private long startedAppTime;
    private long startedLevelTime;
    private Level startedLevel;
    private List<CompletionTime> completionTimes;

    public UserBenchMark() {
        startedAppTime = System.currentTimeMillis();
        completionTimes = new ArrayList<>();
    }

    public void logLevelStarted(Level level) {
        startedLevelTime = System.currentTimeMillis();
        startedLevel = level;
    }

    public void logLevelCompleted() {
        long time = System.currentTimeMillis() - startedLevelTime;
        int id = completionTimes.size();
        completionTimes.add(new CompletionTime(id, startedLevel, time));
        Util.log(toString());
    }

    @Override
    public String toString() {
        long appRunTime = System.currentTimeMillis() - startedAppTime;
        StringBuilder sb = new StringBuilder();
        sb.append("USER BENCHMARK");
        sb.append("\nApp started ").append(appRunTime / 1000).append(" seconds ago");
        for (CompletionTime completionTime : completionTimes) {
            sb.append("\n").append(completionTime);
        }
        return sb.toString();
    }

}
