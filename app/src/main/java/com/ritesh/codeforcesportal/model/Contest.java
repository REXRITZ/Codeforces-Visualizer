package com.ritesh.codeforcesportal.model;

import androidx.annotation.Nullable;

public class Contest{
    private int id;
    private String name;
    private String type;
    private String phase;
    private boolean frozen;
    private int durationSeconds;
    private int startTimeSeconds;
    private int relativeTimeSeconds;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getPhase() {
        return phase;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public int getStartTimeSeconds() {
        return startTimeSeconds;
    }

    public int getRelativeTimeSeconds() {
        return relativeTimeSeconds;
    }


    //    @Override
//    public String toString() {
//        return "Contest{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", type='" + type + '\'' +
//                ", phase='" + phase + '\'' +
//                ", frozen=" + frozen +
//                ", durationSeconds=" + durationSeconds +
//                ", startTimeSeconds=" + startTimeSeconds +
//                ", relativeTimeSeconds=" + relativeTimeSeconds +
//                '}';
//    }
}

