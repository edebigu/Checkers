package es.ericsson.masterCraftmanship.tfm.views;

import java.util.ArrayList;
import java.util.List;

public class GameListJson {
	
	String username;
	List<String> listGame = new ArrayList();
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getListGame() {
		return listGame;
	}
	public void setListGame(String game) {
		this.listGame.add(game);
	}
	
	
	
	
	

}
