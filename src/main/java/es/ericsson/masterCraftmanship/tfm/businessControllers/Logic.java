package es.ericsson.masterCraftmanship.tfm.businessControllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.State;
import es.ericsson.masterCraftmanship.tfm.models.StateValue;


public class Logic {
	
	private Game game;
	private State state;
	private Map<StateValue, OperationController> controllers;
	
	
    public Logic() {
		this.game = new Game();
		this.state = new State();
		this.controllers = new HashMap<StateValue, OperationController>();
		//this.controllers.put(StateValue.INITIAL, new LoginController(this.game, this.state));

    }
    
	public OperationController getController() {
		return this.controllers.get(this.state.getValueState());
    }

}
