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

import es.ericsson.masterCraftmanship.tfm.businessControllers.SaveGameController;
import es.ericsson.masterCraftmanship.tfm.dtos.SaveGameDto;
import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;
import es.ericsson.masterCraftmanship.tfm.views.ErrorView;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@RestController
@RequestMapping(SaveGameResource.SAVE_GAME)
public class SaveGameResource {
	
	static final String SAVE_GAME = "/saveGame";
	
	private SaveGameController saveGameController;
	
	Logger logger = LogManager.getLogger(PlayGameResource.class);
	
	@Autowired
	public SaveGameResource(SaveGameController saveGameController) {
		this.saveGameController = saveGameController;
	}
	
	
	@PostMapping
	public ResponseEntity<ResponseJson> save (@RequestBody SaveGameDto saveGameDto ) {
		logger.info("Recibido save game");
		try {
			saveGameDto.validate();
			return ResponseEntity.ok(this.saveGameController.save(saveGameDto));
		}
		catch (BadRequestException e) {
			ResponseJson responseSave = new ResponseJson();
			responseSave.setError(ErrorView.BAD_REQUEST);
			return new ResponseEntity<ResponseJson>(responseSave,HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	
	

}
