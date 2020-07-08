package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ericsson.masterCraftmanship.tfm.businessControllers.PlayGameController;
import es.ericsson.masterCraftmanship.tfm.views.TurnJson;

@RestController
@RequestMapping(PlayGameResource.IN_GAME)
public class PlayGameResource {
	
	static final String IN_GAME = "/game";
	static final String GET_TURN = "/getTurn";
	
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
	

}
