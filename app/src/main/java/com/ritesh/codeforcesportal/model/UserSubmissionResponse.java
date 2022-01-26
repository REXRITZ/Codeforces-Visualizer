package com.ritesh.codeforcesportal.model;

import java.util.List;
import com.squareup.moshi.Json;

public class UserSubmissionResponse{

	@Json(name = "result")
	private List<Submission> result;
	@Json(name = "status")
	private String status;

	public List<Submission> getResult(){
		return result;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"UserSubmissionResponse{" + 
			"result = '" + result + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}