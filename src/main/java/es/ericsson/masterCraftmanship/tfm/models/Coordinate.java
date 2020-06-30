package es.ericsson.masterCraftmanship.tfm.models;

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

}
