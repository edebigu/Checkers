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

import es.ericsson.masterCraftmanship.tfm.businessControllers.OpenGameController;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;
import es.ericsson.masterCraftmanship.tfm.views.Result;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@RestController
@RequestMapping(OpenGameResource.OPEN_GAME)
public class OpenGameResource {
	
	static final String OPEN_GAME = "/openGame";
	
	private OpenGameController openGameController;
	
	Logger logger = LogManager.getLogger(OpenGameResource.class);
	
	@Autowired
	public OpenGameResource(OpenGameController openGameController) {
		this.openGameController = openGameController;
	}
	
	@PostMapping
	public ResponseEntity<ResponseJson> openGame (@RequestBody SessionDto sessionDto) {
		logger.info("Recibido open game" + sessionDto.toString());
		try {
			sessionDto.validate();
			return  ResponseEntity.ok(this.openGameController.openGame(sessionDto));
			
		}
		catch (BadRequestException e) {
			ResponseJson resultOpenGame = new ResponseJson();
			resultOpenGame.setResult(Result.BAD_REQUEST);
			return new ResponseEntity<ResponseJson>(resultOpenGame,HttpStatus.BAD_REQUEST);
		
		}
	}

}
