package es.ericsson.masterCraftmanship.tfm.models;

public class Turn {
	
	  private Color color;

	  Turn() {
	    this.color = Color.WHITE;
	  }

	  void change() {
	    this.color = this.getOppositeColor();
	  }

	  Color getColor() {
	    return this.color;
	  }

	  Color getOppositeColor() {
	    return Color.values()[(this.color.ordinal() + 1) % 2];
	  }

}
