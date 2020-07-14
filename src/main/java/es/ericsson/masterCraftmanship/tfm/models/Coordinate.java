package es.ericsson.masterCraftmanship.tfm.models;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;

public class Coordinate {
    private int row;
    private int column;
    private static final int LOWER_LIMIT = 0;
    private static final int UPPER_LIMIT = 7;
    private static final int DIMENSION = UPPER_LIMIT + 1;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }
    
    boolean isOnDiagonal(Coordinate coordinate) {
        return this.getDirection(coordinate) != null;
    }
    
    Direction getDirection(Coordinate coordinate) {
        assert coordinate != null;
        Coordinate substract = coordinate.extract(this);
        for (Direction direction : Direction.values()) {
            if (direction.isOnDirection(substract)) {
            	 return direction;
            }
               
        }
        return null;
    }
    
    Coordinate getBetweenDiagonalCoordinate(Coordinate coordinate) {
        assert this.getDiagonalDistance(coordinate) == 2;
        final Direction direction = this.getDirection(coordinate);
        return this.plus(direction.getDistanceCoordinate(1));
    }
    
    List<Coordinate> getBetweenDiagonalCoordinates(Coordinate coordinate){
        assert this.isOnDiagonal(coordinate);
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        final Direction direction = this.getDirection(coordinate);
        Coordinate cursor = this.plus(direction.getDistanceCoordinate(1));
        while (!cursor.equals(coordinate)){	
            coordinates.add(cursor);
            cursor = cursor.plus(direction.getDistanceCoordinate(1));
        }
        return coordinates;
    }

    private Coordinate extract(Coordinate coordinate) {
        return new Coordinate(this.row - coordinate.row, this.column - coordinate.column);
    }
    
    private Coordinate plus(Coordinate coordinate) {
        return new Coordinate(this.row + coordinate.row, this.column + coordinate.column);
    }
    
    int getDiagonalDistance(Coordinate coordinate) {
        assert this.isOnDiagonal(coordinate);
        return Math.abs(this.extract(coordinate).getRow());
    }

    int getRow() {
        return this.row;
    }

    int getColumn() {
        return this.column;
    }

    public static int getDimension() {
        return Coordinate.DIMENSION;
    }
    
    boolean isBlack() {
        return (this.row + this.column) % 2 != 0;
    }

    public boolean isLast() {
        return this.row == Coordinate.UPPER_LIMIT;
    }

    public boolean isFirst() {
        return this.row == Coordinate.LOWER_LIMIT;
    }

	@Override
	public String toString() {
		return "Coordinate [row=" + row + ", column=" + column + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		Coordinate other = (Coordinate) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
	

}
