package com.ritesh.codeforcesportal.model;

public class Tags {

    private String name;
    private String percent;
    private int solvedCount;
    private int triedCount;

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
