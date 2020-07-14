package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ericsson.masterCraftmanship.tfm.businessControllers.PlayGameController;
import es.ericsson.masterCraftmanship.tfm.dtos.MoveDto;
import es.ericsson.masterCraftmanship.tfm.views.MoveJson;
import es.ericsson.masterCraftmanship.tfm.views.SquareJson;
import es.ericsson.masterCraftmanship.tfm.views.TurnJson;

@RestController
@RequestMapping(PlayGameResource.IN_GAME)
public class PlayGameResource {
	
	static final String IN_GAME = "/game";
	static final String GET_TURN = "/getTurn";
	static final String GET_STATUS = "/getStatus";
	static final String MOVE= "/move";
	
	private PlayGameController playGameController;
	
	
	Logger logger = LogManager.getLogger(PlayGameResource.class);
	
	@Autowired
	public PlayGameResource(PlayGameController playGameController) {
		this.playGameController = playGameController;
	}
	
	@GetMapping("/{id}/" + PlayGameResource.GET_TURN)
	public ResponseEntity<TurnJson> getTurn (@PathVariable(name="id") String gameId) {
		logger.info("Recibido get turn");
		return  ResponseEntity.ok(this.playGameController.getTurn(gameId));
	}
	
	@GetMapping("/{id}/" + PlayGameResource.GET_STATUS)
	public ResponseEntity<SquareJson> getStatus (@PathVariable(name="id") String gameId) {
		logger.info("Recibido get status");
		return new ResponseEntity(this.playGameController.getStatus(gameId), HttpStatus.OK);
	}
	
	@PostMapping("/{id}/" + PlayGameResource.MOVE + "/{player}")
	public ResponseEntity<MoveJson> getTurn (@PathVariable(name="id") String gameId, @PathVariable(name="player") String player, @RequestBody MoveDto movementDto ) {
		logger.info("Recibido move");
		return  ResponseEntity.ok(this.playGameController.move(gameId, player, movementDto));
		
	}

}
