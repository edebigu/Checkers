package es.ericsson.masterCraftmanship.tfm.views;

import es.ericsson.masterCraftmanship.tfm.models.Error;

public class ResponseJson {
	String username;
	String result;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getResult() {
		return result;
	}
	public void setResult (Result result) {
		this.result = result.getResult();
	}
	
	public void setError (Error error) {
		this.result = error.toString();
	}

}
