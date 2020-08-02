package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ericsson.masterCraftmanship.tfm.businessControllers.LogoutController;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.views.Result;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@RestController
@RequestMapping(LogoutResource.LOGOUT)
public class LogoutResource {
	
	static final String LOGOUT = "/logout";

	
	private LogoutController logoutController;
	
	@Autowired
	public LogoutResource(LogoutController logoutController) {
		this.logoutController = logoutController;
	}
	
	
	@PostMapping
	public ResponseEntity<ResponseJson> logout(@RequestBody SessionDto sessionDto) {

		try {
			sessionDto.validate();
			return  ResponseEntity.ok(this.logoutController.logout(sessionDto));
			
		}
		catch (BadRequestException e) {
			ResponseJson resultJson = new ResponseJson();
			resultJson.setResult(Result.BAD_REQUEST);
			return new ResponseEntity<ResponseJson>(resultJson,HttpStatus.BAD_REQUEST);
		
		}
		
	}

}
