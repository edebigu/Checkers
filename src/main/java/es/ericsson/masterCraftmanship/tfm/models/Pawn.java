package es.ericsson.masterCraftmanship.tfm.models;

public class Pawn extends Piece{
	
    private static char[] CHARACTERS = {'b', 'n'};
    private static final int MAX_DISTANCE = 2;

    Pawn(Color color) {
        super(color);
    }

}
