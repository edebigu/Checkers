package es.ericsson.masterCraftmanship.tfm.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Game")
public class Game {
	
	@Id
	private String id;
	private Board board;
	private Turn turn;

	Game(Board board) {
		this.turn = new Turn();
		this.board = board;
	}

	public Game() {
		this(new Board());
		this.reset();
	}
	
	public Color getColor(Coordinate coordinate) {
		assert coordinate != null;
		return this.board.getColor(coordinate);
	}
	
	public int getDimension() {
		return Coordinate.getDimension();
	}
	
	public Piece getPiece(Coordinate coordinate) {
		assert coordinate != null;
		return this.board.getPiece(coordinate);
	}
	
	public void reset() {
		for (int i = 0; i < Coordinate.getDimension(); i++)
			for (int j = 0; j < Coordinate.getDimension(); j++) {
				Coordinate coordinate = new Coordinate(i, j);
				Color color = Color.getInitialColor(coordinate);
				Piece piece = null;
				if (color != null)
					piece = new Pawn(color);
				this.board.put(coordinate, piece);
			}
		if (this.turn.getColor() != Color.WHITE)
			this.turn.change();
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
