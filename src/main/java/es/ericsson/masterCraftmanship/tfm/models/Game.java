package es.ericsson.masterCraftmanship.tfm.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "Game")
public class Game {
	
	@Id
	private String id;
	private Board board;
	private Turn turn;
	private List<Player> players;
	
	
	

	Game(Board board) {
		this.turn = new Turn();
		this.board = board;
		this.players = new CopyOnWriteArrayList<>();
	}

	public Game() {
		this(new Board());
		this.reset();
	}
	
	public void addPlayer(Player player) {
		player.setColor(this.getTurn().getColor());
		players.add(player);
	}
	
	public void addMachinePlayer() {
		Player playerMachine = new Player("machine", "machine");
		playerMachine.setColor(this.getOppositeTurnColor());
		players.add(playerMachine);
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
	
	public Error move(Coordinate... coordinates) {
		Error error = null; 
		List<Coordinate> removedCoordinates = new ArrayList<Coordinate>();
		int pair = 0;
		do {
			error = this.isCorrectPairMove(pair, coordinates);
			if (error == null) {
				this.pairMove(removedCoordinates, pair, coordinates);
				pair++;
			}
		} while (pair < coordinates.length - 1 && error == null);
		error = this.isCorrectGlobalMove(error, removedCoordinates, coordinates);
		if (error == null)
			this.turn.change();
		else
			this.unMovesUntilPair(removedCoordinates, pair, coordinates);
		return error;
	}
	
	private Error isCorrectPairMove(int pair, Coordinate... coordinates) {
		assert coordinates[pair] != null;
		assert coordinates[pair + 1] != null;
		if (board.isEmpty(coordinates[pair]))
			return Error.EMPTY_ORIGIN;
		if (this.turn.getOppositeColor() == this.board.getColor(coordinates[pair]))
			return Error.OPPOSITE_PIECE;
		if (!this.board.isEmpty(coordinates[pair + 1]))
			return Error.NOT_EMPTY_TARGET;
		List<Piece> betweenDiagonalPieces = this.board.getBetweenDiagonalPieces(coordinates[pair], coordinates[pair + 1]);
		return this.board.getPiece(coordinates[pair]).isCorrectMovement(betweenDiagonalPieces, pair, coordinates);
	}
	
	private Error isCorrectGlobalMove(Error error, List<Coordinate> removedCoordinates, Coordinate... coordinates){
		if (error != null)
			return error;
		if (coordinates.length > 2 && coordinates.length > removedCoordinates.size() + 1)
			return Error.TOO_MUCH_JUMPS;
		return null;
	}
	
	private void pairMove(List<Coordinate> removedCoordinates, int pair, Coordinate... coordinates) {
		Coordinate forRemoving = this.getBetweenDiagonalPiece(pair, coordinates);
		if (forRemoving != null) {
			removedCoordinates.add(0, forRemoving);
			this.board.remove(forRemoving);
		}
		this.board.move(coordinates[pair], coordinates[pair + 1]);
		if (this.board.getPiece(coordinates[pair + 1]).isLimit(coordinates[pair + 1])) {
			Color color = this.board.getColor(coordinates[pair + 1]);
			this.board.remove(coordinates[pair + 1]);
			this.board.put(coordinates[pair + 1], new Queen(color));
		}
	}
	
	private void unMovesUntilPair(List<Coordinate> removedCoordinates, int pair, Coordinate... coordinates) {
		for (int j = pair; j > 0; j--)
			this.board.move(coordinates[j], coordinates[j - 1]);
		for (Coordinate removedPiece : removedCoordinates)
			this.board.put(removedPiece, new Pawn(this.getOppositeTurnColor()));
	}
	
    List<Piece> getBetweenDiagonalPieces(Coordinate origin, Coordinate target) {
        List<Piece> betweenDiagonalPieces = new ArrayList<Piece>();
        if (origin.isOnDiagonal(target))
            for (Coordinate coordinate : origin.getBetweenDiagonalCoordinates(target)) {
                Piece piece = this.getPiece(coordinate);
                if (piece != null)
                    betweenDiagonalPieces.add(piece);
            }
        return betweenDiagonalPieces;
    }
    
	private Coordinate getBetweenDiagonalPiece(int pair, Coordinate... coordinates) {
		assert coordinates[pair].isOnDiagonal(coordinates[pair + 1]);
		List<Coordinate> betweenCoordinates = coordinates[pair].getBetweenDiagonalCoordinates(coordinates[pair + 1]);
		if (betweenCoordinates.isEmpty())
			return null;
		for (Coordinate coordinate : betweenCoordinates) {
			if (this.getPiece(coordinate) != null)
				return coordinate;
		}
		return null;
	}
	

	private Color getOppositeTurnColor() {
		return this.turn.getOppositeColor();
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

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	
	


}
