package es.ericsson.masterCraftmanship.tfm.dtos;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;

@EntityScan
public class SaveGameDto {
	
	private String username;
	private String gameName;
	private String newGameName;
	private boolean overwrite;
	
	public SaveGameDto() {
		
	}

	public SaveGameDto(String username, String gameName, String newGameName, Boolean overwrite) {
		this.username = username;
		this.gameName = gameName;
		this.newGameName = newGameName;
		this.overwrite = overwrite;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getNewGameName() {
		return newGameName;
	}

	public void setNewGameName(String newGameName) {
		this.newGameName = newGameName;
	}
	
	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public void validate() {
		if (username == null || gameName == null || newGameName == null) {
			throw new BadRequestException ("Incomplete SaveGameDto");
		}
	}
	
	

}
