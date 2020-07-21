package es.ericsson.masterCraftmanship.tfm.views;

public enum Message {
	STARTED("Started"),
	LOGIN_SUCCESSFULL("Loggin success"), 
	LOGIN_UNSUCCESSFULL("User or password not exist"),
	REGISTER_SUCCESSFULL("Register success"), 
	REGISTER_UNSUCCESSFULL("User exist"),
	CREATE_GAME_SUCCESSFULL("Game created"),
	CREATE_GAME_UNSUCCESSFULL("Game not created. Session user is no found"),
	EMPTY_FIELD("All field must be espicified"),
	MOVE_SUCCESS("Movement success"),
	MOVE_UNSUCCESSFULL("Movement not permit"),
	LOGOUT_SUCCESS("Logout success"),
	LOGOUT_UNSUCCESSFULL("Player is not logged"),
	SAVE_GAME_SUCCESS("Game saved"),
	SAVE_GAME_UNSUCCESS ("Game not saved"),
	OPEN_GAME_SUCCESS("Game opened"),
	OPEN_GAME_UNSUCCESS ("Game not exist");
	
	
	private String message;
	
	private Message(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
