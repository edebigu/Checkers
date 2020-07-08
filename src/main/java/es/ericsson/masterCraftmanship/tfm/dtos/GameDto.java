package es.ericsson.masterCraftmanship.tfm.dtos;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import es.ericsson.masterCraftmanship.tfm.models.Board;
import es.ericsson.masterCraftmanship.tfm.models.Color;
import es.ericsson.masterCraftmanship.tfm.models.Turn;

@EntityScan
public class GameDto {
	
	private String id;
	private String username;
	
	public GameDto() {
		
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	
}
