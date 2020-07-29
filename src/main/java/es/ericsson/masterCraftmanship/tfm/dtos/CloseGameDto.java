package es.ericsson.masterCraftmanship.tfm.dtos;

import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;

public class CloseGameDto {
	
	private String username;
	private String gameName;
	private boolean closeWithoutSave;
	
	public CloseGameDto() {
		
	}

	public CloseGameDto(String username, String gameName, Boolean closeWithoutSave) {
		this.username = username;
		this.gameName = gameName;
		this.closeWithoutSave = closeWithoutSave;
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

	public boolean isCloseWithoutSave() {
		return closeWithoutSave;
	}

	public void setCloseWithoutSave(boolean closeWithoutSave) {
		this.closeWithoutSave = closeWithoutSave;
	}
	
	public void validate() {
		if (username == null || gameName == null ) {
			throw new BadRequestException ("Incomplete CloseGameDto");
		}
	}
	
	

}
