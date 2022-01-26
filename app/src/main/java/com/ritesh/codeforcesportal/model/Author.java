package com.ritesh.codeforcesportal.model;

import java.util.List;

public class Author{

	private int contestId;
	private List<Member> members;
	private String participantType;
	private int teamId;
	private String teamName;
	private boolean ghost;
	private int room;
	private int startTimeSeconds;

	public int getContestId(){
		return contestId;
	}

	public boolean isGhost(){
		return ghost;
	}

	public List<Member> getMembers(){
		return members;
	}

	public String getParticipantType(){
		return participantType;
	}

	public int getStartTimeSeconds(){
		return startTimeSeconds;
	}

	public int getRoom(){
		return room;
	}

	@Override
 	public String toString(){
		return 
			"Author{" + 
			"contestId = '" + contestId + '\'' + 
			",ghost = '" + ghost + '\'' + 
			",members = '" + members + '\'' + 
			",participantType = '" + participantType + '\'' + 
			",startTimeSeconds = '" + startTimeSeconds + '\'' + 
			",room = '" + room + '\'' + 
			"}";
		}
}