package es.ericsson.masterCraftmanship.tfm.businessControllers;

import es.ericsson.masterCraftmanship.tfm.models.Coordinate;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Piece;
import es.ericsson.masterCraftmanship.tfm.models.State;

public abstract class OperationController extends Controllers {
	
	protected OperationController (Game game, State state) {
		super(game, state);
	}

	public Piece getPiece(Coordinate coordinate) {
		return this.game.getPiece(coordinate);
	}

	public abstract void accept(OperationControllerVisitor controllersVisitor);

}
