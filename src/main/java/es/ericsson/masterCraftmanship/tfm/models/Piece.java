package es.ericsson.masterCraftmanship.tfm.models;

public abstract class Piece {
	protected Color color;
	private static String[] CODES = {"b", "n"};

	Piece(Color color) {
		assert color != null;
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}

}
