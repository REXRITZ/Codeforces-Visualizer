package com.ritesh.codeforcesportal.model;

public class Tags {

    private final String name;
    private final String percent;
    private final int solvedCount;
    private final int triedCount;

    public Tags(String name, String percent, int solvedCount, int triedCount) {
        this.name = name;
        this.percent = percent;
        this.solvedCount = solvedCount;
        this.triedCount = triedCount;
    }

    public String getName() {
        return name;
    }

    public String getPercent() {
        return percent;
    }

    public int getSolvedCount() {
        return solvedCount;
    }

    public int getTriedCount() {
        return triedCount;
    }
}
