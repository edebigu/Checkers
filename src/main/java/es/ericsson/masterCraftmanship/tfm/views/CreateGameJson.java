package es.ericsson.masterCraftmanship.tfm.views;

public class CreateGameJson {
	
	String username;
	String gameName;
	String error;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getError() {
		return error;
	}
	public void setError(ErrorView error) {
		this.error = error.getError();
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
}
