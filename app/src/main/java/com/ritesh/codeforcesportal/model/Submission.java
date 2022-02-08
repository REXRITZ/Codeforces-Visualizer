package com.ritesh.codeforcesportal.model;

public class Submission {

	private int id;
	private int contestId;
	private int creationTimeSeconds;
	private int relativeTimeSeconds;
	private Problem problem;
	private Author author;
	private String programmingLanguage;
	private String verdict;
	private String testset;
	private int passedTestCount;
	private int memoryConsumedBytes;
	private int timeConsumedMillis;
	private Float points;

	public int getContestId(){
		return contestId;
	}

	public int getTimeConsumedMillis(){
		return timeConsumedMillis;
	}

	public int getRelativeTimeSeconds(){
		return relativeTimeSeconds;
	}

	public Problem getProblem(){
		return problem;
	}

	public int getCreationTimeSeconds(){
		return creationTimeSeconds;
	}

	public Author getAuthor(){
		return author;
	}

	public String getProgrammingLanguage(){
		return programmingLanguage;
	}

	public String getVerdict(){
		return verdict;
	}

	public String getTestset(){
		return testset;
	}

	public int getPassedTestCount(){
		return passedTestCount;
	}

	public int getMemoryConsumedBytes(){
		return memoryConsumedBytes;
	}

	public int getId(){
		return id;
	}

	public Float getPoints() {
		return points;
	}

	@Override
	public String toString() {
		return "Submission{" +
				"id=" + id +
				", contestId=" + contestId +
				", creationTimeSeconds=" + creationTimeSeconds +
				", relativeTimeSeconds=" + relativeTimeSeconds +
				", problem=" + problem +
				", author=" + author +
				", programmingLanguage='" + programmingLanguage + '\'' +
				", verdict='" + verdict + '\'' +
				", testset='" + testset + '\'' +
				", passedTestCount=" + passedTestCount +
				", memoryConsumedBytes=" + memoryConsumedBytes +
				", timeConsumedMillis=" + timeConsumedMillis +
				", points=" + points +
				'}';
	}
}