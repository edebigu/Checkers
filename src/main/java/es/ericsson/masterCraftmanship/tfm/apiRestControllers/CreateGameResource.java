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
import es.ericsson.masterCraftmanship.tfm.views.CreateGameJson;
import es.ericsson.masterCraftmanship.tfm.views.ErrorView;
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
	public ResponseEntity<CreateGameJson> createGame(@RequestBody SessionDto sessionDto) {
		logger.info("Recibido create game" + sessionDto.toString());
		try {
			sessionDto.validate();
			return  ResponseEntity.ok(this.createGameController.createGame(sessionDto));
			
		}
		catch (BadRequestException e) {
			CreateGameJson resultCreateGame = new CreateGameJson();
			resultCreateGame.setMsg(Message.EMPTY_FIELD);
			resultCreateGame.setError(ErrorView.BAD_REQUEST);
			resultCreateGame.setUsername(sessionDto.getUsername());
			return new ResponseEntity<CreateGameJson>(resultCreateGame,HttpStatus.BAD_REQUEST);
		
		}
		
	}
	

}
