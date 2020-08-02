package es.ericsson.masterCraftmanship.tfm.dtos;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import es.ericsson.masterCraftmanship.tfm.apiRestControllers.BadRequestException;
import es.ericsson.masterCraftmanship.tfm.models.Player;

@EntityScan
public class PlayerDto {
	
	private String username;
	private String password;
	
	public PlayerDto() {
		
	}
	
	public PlayerDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public PlayerDto(Player user) {
		this.username = user.getUsername();
		this.password = user.getPassword();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	public void validate() {
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			throw new BadRequestException ("Incomplete UserDto");
		}
	}

	@Override
	public String toString() {
		return "UserDto [username=" + username + ", password=" + password + "]";
	}
	

}
