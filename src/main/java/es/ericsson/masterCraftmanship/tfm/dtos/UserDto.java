package es.ericsson.masterCraftmanship.tfm.dtos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;

@Entity
@Table(name="users")
//@JsonInclude(Include.NON_NULL)
public class UserDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;
	
	public UserDto() {
		
	}
	
	public UserDto(String username, String password) {
		this.username = username;
		this.password = password;
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
	
	public Long getId() {
		return this.id;
	}
	
	public void validate() {
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			throw new BadRequestException ("Incomplete UserDto");
		}
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	

}
