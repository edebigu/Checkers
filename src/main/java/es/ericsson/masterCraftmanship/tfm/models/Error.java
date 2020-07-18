package es.ericsson.masterCraftmanship.tfm.models;

public enum Error {
    BAD_FORMAT("BAD_FORMAT"),
    EMPTY_ORIGIN("EMPTY_ORIGIN"), 
    OPPOSITE_PIECE("OPPOSITE_PIECE"), 
    NOT_DIAGONAL("NOT_DIAGONAL"), 
    NOT_EMPTY_TARGET("NOT_EMPTY_TARGET"), 
    NOT_ADVANCED("NOT_ADVANCED"), 
    WITHOUT_EATING("WITHOUT_EATING"),
    COLLEAGUE_EATING("COLLEAGUE_EATING"),
    TOO_MUCH_ADVANCED("TOO_MUCH_ADVANCED"),
    TOO_MUCH_EATINGS("TOO_MUCH_EATINGS"),
    TOO_MUCH_JUMPS("TOO_MUCH_JUMPS"),
    LOST_MESSAGE("LOST_MESSAGE"),
    LOST_MESSAGE_MACHINE("LOST_MESSAGE_MACHINE");
	
    private String value;

    private Error(String value) {
        this.value = value;
    }

    public String toString() {
        return "" + value;
    }
	
	

}
