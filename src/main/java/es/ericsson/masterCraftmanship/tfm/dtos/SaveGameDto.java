package es.ericsson.masterCraftmanship.tfm.dtos;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import es.ericsson.masterCraftmanship.tfm.apiRestControllers.BadRequestException;

@EntityScan
public class SaveGameDto {
	
	private String username;
	private String gameName;
	private boolean overwrite;
	
	public SaveGameDto() {
		
	}

	public SaveGameDto(String username, String gameName, Boolean overwrite) {
		this.username = username;
		this.gameName = gameName;
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

	
	public boolean isOverwrite() {
		return overwrite;
	}

	public void setOverwrite(boolean overwrite) {
		this.overwrite = overwrite;
	}

	public void validate() {
		if (username == null || gameName == null ) {
			throw new BadRequestException ("Incomplete SaveGameDto");
		}
	}

	@Override
	public String toString() {
		return "SaveGameDto [username=" + username + ", gameName=" + gameName + ", overwrite=" + overwrite + "]";
	}
	
	
	
	

}
