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

import es.ericsson.masterCraftmanship.tfm.businessControllers.CloseGameController;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@RestController
@RequestMapping(CloseGameResource.CLOSE_GAME)
public class CloseGameResource {
	
	static final String CLOSE_GAME = "/closeGame";
	
	private CloseGameController closeGameController;
	
	Logger logger = LogManager.getLogger(CloseGameResource.class);
	
	@Autowired
	public CloseGameResource(CloseGameController closeGameController) {
		this.closeGameController = closeGameController;
	}
	
	@PostMapping
	public ResponseEntity<ResponseJson> save (@RequestBody SessionDto sessionDto ) {
		logger.info("Recibido close game");
		try {
			sessionDto.validate();
			return  ResponseEntity.ok(this.closeGameController.closeGame(sessionDto));
		}
		catch (BadRequestException e) {
			ResponseJson response = new ResponseJson();
			response.setMsg(Message.EMPTY_FIELD);
			response.setError(Error.BAD_REQUEST);
			return new ResponseEntity<ResponseJson>(response,HttpStatus.BAD_REQUEST);
		}
		
		
	}

}
