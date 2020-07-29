package es.ericsson.masterCraftmanship.tfm.models;

import java.util.ArrayList;
import java.util.List;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "Game")
public class Game {
	

	@Id
	private String id;
	private String gameName;
	private Board board;
	private Turn turn;
	private Player player;
	
	Game(Board board) {
		this.turn = new Turn();
		this.board = board;
	}

	public Game() {
		this(new Board());
		this.reset();
		this.gameName="";
	}
	
	public Game(Game game) {
		this.gameName = game.getName();
		this.board = game.getBoard();
		this.turn = game.getTurn();
		this.player = game.getPlayer();
	}
	
	public void addPlayer(Player player) {
		player.setColor(this.getTurn().getColor());
		this.player = player;
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
		if (error == null) {
			this.turn.change();
		    error = doMoveMachine();
		}
		else {
			this.unMovesUntilPair(removedCoordinates, pair, coordinates);
		}
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
	
	public boolean isBlocked() {
		for (Coordinate coordinate : this.getCoordinatesWithActualColor())
			if (!this.isBlocked(coordinate))
				return false;
		return true;
	}

	private List<Coordinate> getCoordinatesWithActualColor() {
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		for (int i = 0; i < this.getDimension(); i++) {
			for (int j = 0; j < this.getDimension(); j++) {
				Coordinate coordinate = new Coordinate(i, j);
				Piece piece = this.getPiece(coordinate);
				if (piece != null && piece.getColor() == this.getTurnColor())
					coordinates.add(coordinate);
			}
		}
		return coordinates;
	}

	private boolean isBlocked(Coordinate coordinate) {
		for (int i = 1; i <= 2; i++)
			for (Coordinate target : coordinate.getDiagonalCoordinates(i))
				if (this.isCorrectPairMove(0, coordinate, target) == null)
					return false;
		return true;
	}
	
	private Error doMoveMachine () {
		Error error = null;
		Coordinate origin = getAllowedOrigin();
		if (origin != null) {
			Coordinate target = getAllowedTarget(origin);
			List<Coordinate> removedCoordinates = new ArrayList<Coordinate>();
			Coordinate[] coordinates = {origin, target};
			int pair = 0;
			do {
				error = this.isCorrectPairMove(pair, coordinates);
				if (error == null) {
					this.pairMove(removedCoordinates, pair, coordinates);
					pair++;
				}
			} while (pair < coordinates.length - 1 && error == null);
			this.turn.change();
		}
		return error;
		
	}
	
	private Coordinate getAllowedOrigin() {
		List<Coordinate> listUnblocked = new ArrayList<Coordinate>();
		Coordinate allowedOrigin = null;
		for (Coordinate coordinate : this.getCoordinatesWithActualColor()) {
			if (!this.isBlocked(coordinate)) {
				listUnblocked.add(coordinate);
			}
		}
		
		if (listUnblocked.size() > 0) {
			allowedOrigin = listUnblocked.get( (int) (Math.random() * listUnblocked.size()));
		}
		return allowedOrigin;
	}
	
	private Coordinate getAllowedTarget(Coordinate origin) {
		List<Coordinate> targets = new ArrayList<Coordinate>();
		for (int i = 1; i <= 2; i++) {
			for (Coordinate target : origin.getDiagonalCoordinates(i)) {
				if (this.isCorrectPairMove(0, origin, target) == null) {
					targets.add(target);
				}
			}
		}
		return  targets.get((int) (Math.random() * targets.size()));
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

	public Player getPlayer() {
		return player;
	}

	public void setPlayers(Player player) {
		this.player = player;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}
	
	public Color getTurnColor() {
		return this.turn.getColor();
	}

	public String getName() {
		return gameName;
	}

	public void setName(String gameName) {
		this.gameName = gameName;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		result = prime * result + ((gameName == null) ? 0 : gameName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((turn == null) ? 0 : turn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		if (gameName == null) {
			if (other.gameName != null)
				return false;
		} else if (!gameName.equals(other.gameName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (turn == null) {
			if (other.turn != null)
				return false;
		} else if (!turn.equals(other.turn))
			return false;
		return true;
	}


}
