package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

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
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RestController
@RequestMapping(CreateGameResource.CREATE_GAME)
public class CreateGameResource {

	static final String CREATE_GAME = "/createGame";

	
	private CreateGameController createGameController;
	
	@Autowired
	public CreateGameResource(CreateGameController createGameController) {
		this.createGameController = createGameController;
	}
	
	@PostMapping
	public ResponseEntity<CreateGameJson> createGame(@RequestBody SessionDto sessionDto) {
		try {
			sessionDto.validate();
			return  ResponseEntity.ok(this.createGameController.createGame(sessionDto));
			
		}
		catch (BadRequestException e) {
			CreateGameJson resultCreateGame = new CreateGameJson();
			resultCreateGame.setResult(Result.BAD_REQUEST);
			resultCreateGame.setUsername(sessionDto.getUsername());
			return new ResponseEntity<CreateGameJson>(resultCreateGame,HttpStatus.BAD_REQUEST);
		
		}
		
	}
	

}
