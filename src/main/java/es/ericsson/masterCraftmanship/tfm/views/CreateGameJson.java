package es.ericsson.masterCraftmanship.tfm.views;

public class CreateGameJson {
	
	String username;
	String gameName;
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
	public void setResult(Result result) {
		this.result = result.getResult();
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
}
