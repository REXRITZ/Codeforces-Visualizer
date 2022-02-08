package com.ritesh.codeforcesportal.model;

import java.util.List;

public class Problem{

	private int contestId;
	private String problemsetName;
	private String index;
	private String name;
	private String type;
	private double points;
	private int rating;
	private List<String> tags;

	public int getContestId(){
		return contestId;
	}

	public String getName(){
		return name;
	}

	public int getRating(){
		return rating;
	}

	public String getIndex(){
		return index;
	}

	public String getType(){
		return type;
	}

	public double getPoints(){
		return points;
	}

	public List<String> getTags(){
		return tags;
	}

	public String getProblemsetName() {
		return problemsetName;
	}

	@Override
 	public String toString(){
		return 
			"Problem{" + 
			"contestId = '" + contestId + '\'' + 
			",name = '" + name + '\'' + 
			",rating = '" + rating + '\'' + 
			",index = '" + index + '\'' + 
			",type = '" + type + '\'' + 
			",points = '" + points + '\'' + 
			",tags = '" + tags + '\'' + 
			"}";
		}
}