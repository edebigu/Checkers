package es.ericsson.masterCraftmanship.tfm.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Session")
public class Session {
	
	@Id
	private String id;
	
	private Player player;
	private Game game;
	
	public Session (Player player, Game game) {
		this.player = player;
		this.game = game;
	}

	public String getId() {
		return id;
	}

	public Player getPlayer() {
		return player;
	}

	public Game getGame() {
		return game;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", player=" + player + ", game=" + game.toString() + "]";
	}

	public void setId(String string) {
		this.id = string;
		
	}
	
	
	

}
