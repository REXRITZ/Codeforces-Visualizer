package com.ritesh.codeforcesportal.model;

import com.squareup.moshi.Json;

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