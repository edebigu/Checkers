package es.ericsson.masterCraftmanship.tfm.dtos;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;
import es.ericsson.masterCraftmanship.tfm.models.Session;

@EntityScan
public class SessionDto {
	
	private String id;
	private String username;
	private String gameName;

	public SessionDto() {
		
	}
	
	public SessionDto(String username, String gameName) {
		this.username = username;
		this.gameName = gameName;
	}
	
	public SessionDto(Session session) {
		this.username = session.getPlayer().getUsername();
		this.gameName = session.getGame().getId();
	}

	public String getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getGameName() {
		return gameName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public void validate() {
		if (username == null || username.isEmpty() || gameName == null) {
			throw new BadRequestException ("Incomplete SessionDto");
		}
	}

	@Override
	public String toString() {
		return "SessionDto [id=" + id + ", username=" + username + ", gameName=" + gameName + "]";
	}
	
	
}
