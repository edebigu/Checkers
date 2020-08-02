package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ericsson.masterCraftmanship.tfm.businessControllers.CloseGameController;
import es.ericsson.masterCraftmanship.tfm.dtos.CloseGameDto;
import es.ericsson.masterCraftmanship.tfm.views.Result;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@RestController
@RequestMapping(CloseGameResource.CLOSE_GAME)
public class CloseGameResource {
	
	static final String CLOSE_GAME = "/closeGame";
	
	private CloseGameController closeGameController;
	
	@Autowired
	public CloseGameResource(CloseGameController closeGameController) {
		this.closeGameController = closeGameController;
	}
	
	@PostMapping
	public ResponseEntity<ResponseJson> close (@RequestBody CloseGameDto operationGameDto ) {

		try {
			operationGameDto.validate();
			return  ResponseEntity.ok(this.closeGameController.closeGame(operationGameDto));
		}
		catch (BadRequestException e) {
			ResponseJson response = new ResponseJson();
			response.setResult(Result.BAD_REQUEST);
			response.setUsername(operationGameDto.getUsername());
			return new ResponseEntity<ResponseJson>(response,HttpStatus.BAD_REQUEST);
		}
		
		
	}

}
