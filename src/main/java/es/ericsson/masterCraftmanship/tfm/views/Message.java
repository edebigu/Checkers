package es.ericsson.masterCraftmanship.tfm.views;

public enum Message {
	STARTED("Started"),
	LOGIN_SUCCESSFULL("Loggin success"), 
	LOGIN_UNSUCCESSFULL("User or password not exist"),
	REGISTER_SUCCESSFULL("Register success"), 
	REGISTER_UNSUCCESSFULL("User exist"),
	CREATE_GAME_SUCCESSFULL("Game created"),
	CREATE_GAME_UNSUCCESSFULL("Game not created. Session user is no found"),
	EMPTY_FIELD("User and password must be espicified");
	
	
	private String message;
	
	private Message(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
