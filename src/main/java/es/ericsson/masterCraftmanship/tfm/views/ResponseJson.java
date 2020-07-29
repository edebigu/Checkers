package es.ericsson.masterCraftmanship.tfm.views;

import es.ericsson.masterCraftmanship.tfm.models.Error;

public class ResponseJson {
	String username;
	String error;
	String msg;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(Message msg) {
		this.msg = msg.getMessage();
	}
	
	public String getError() {
		return error;
	}
	public void setError (ErrorView error) {
		this.error = error.getError();
	}
	
	public void setError (Error error) {
		this.error = this.error.toString();
	}

}
