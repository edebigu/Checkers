package es.ericsson.masterCraftmanship.tfm.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Player")
public class Player {
	
	@Id
	private String id;
	
	private String username;
	private String password;
	private Direction direction;

	public Player() {
		
	}
	
	public Player (String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getId() {
		return id;
	}


	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", username=" + username + ", password=" + password + "]";
	}



	
	
	

}
