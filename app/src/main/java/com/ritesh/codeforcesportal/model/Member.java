package com.ritesh.codeforcesportal.model;

public class Member {

	private String handle;
	public String getHandle(){
		return handle;
	}

	@Override
 	public String toString(){
		return 
			"MembersItem{" + 
			"handle = '" + handle + '\'' + 
			"}";
		}
}