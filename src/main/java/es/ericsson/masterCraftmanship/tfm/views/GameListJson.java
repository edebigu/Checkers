package es.ericsson.masterCraftmanship.tfm.views;

import java.util.ArrayList;
import java.util.List;

import es.ericsson.masterCraftmanship.tfm.models.Game;

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
	public void setListGame(List<Game> listGame) {
		for (Game game : listGame) {
			this.listGame.add(game.getName());
		}
	}
	
	
	
	
	

}
