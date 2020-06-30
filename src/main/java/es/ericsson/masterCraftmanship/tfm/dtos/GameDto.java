package es.ericsson.masterCraftmanship.tfm.dtos;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import es.ericsson.masterCraftmanship.tfm.models.Board;
import es.ericsson.masterCraftmanship.tfm.models.Turn;

@EntityScan
public class GameDto {
	
	private String id;
	private Board board;
	private Turn turn;
	
	public GameDto() {
		
	}
	
	public GameDto(Board board, Turn turn) {
		this.board = board;
		this.turn = turn;
	}

	public String getId() {
		return id;
	}

	public Board getBoard() {
		return board;
	}

	public Turn getTurn() {
		return turn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	
	
}
