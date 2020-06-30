package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ericsson.masterCraftmanship.tfm.businessControllers.CreateGameController;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@RestController
@RequestMapping(CreateGameResource.CREATE_GAME)
public class CreateGameResource {

	static final String CREATE_GAME = "/createGame";

	Logger logger = LogManager.getLogger(CreateGameResource.class);
	
	private CreateGameController createGameController;
	
	@Autowired
	public CreateGameResource(CreateGameController createGameController) {
		this.createGameController = createGameController;
	}
	
	@PostMapping
	public ResponseEntity<ResponseJson> createGame(@RequestBody SessionDto sessionDto) {
		logger.info("Recibido create game" + sessionDto.toString());
		try {
			sessionDto.validate();
			return  ResponseEntity.ok(this.createGameController.createGame(sessionDto));
			
		}
		catch (BadRequestException e) {
			ResponseJson resultCreateGame = new ResponseJson();
			resultCreateGame.setMsg(Message.EMPTY_FIELD);
			resultCreateGame.setError(Error.BAD_REQUEST);
			return new ResponseEntity<ResponseJson>(resultCreateGame,HttpStatus.BAD_REQUEST);
		
		}
		
	}
}
