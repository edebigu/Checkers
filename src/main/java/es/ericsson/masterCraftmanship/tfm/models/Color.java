package es.ericsson.masterCraftmanship.tfm.models;

public enum Color {
    WHITE,
    BLACK;
	
	 private final int[] LIMITS = new int[]{5, 2};

    static Color getInitialColor(final Coordinate coordinate) {
        if (coordinate.isBlack())
            for(Color color : Color.values())
                if (color.isInitialRow(coordinate.getRow()))
                    return color;
        return null;
    }
    
    boolean isInitialRow(final int row){
        switch(this){
            case WHITE:
                return row >= LIMITS[this.ordinal()];
            case BLACK:
                return row <= LIMITS[this.ordinal()];
        }
        return false;
    }
}