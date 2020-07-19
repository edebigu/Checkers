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

import es.ericsson.masterCraftmanship.tfm.businessControllers.LogoutController;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@RestController
@RequestMapping(LogoutResouce.LOGOUT)
public class LogoutResouce {
	
	static final String LOGOUT = "/logout";
	
	Logger logger = LogManager.getLogger(LogoutResouce.class);
	
	private LogoutController logoutController;
	
	@Autowired
	public LogoutResouce(LogoutController logoutController) {
		this.logoutController = logoutController;
	}
	
	
	@PostMapping
	public ResponseEntity<ResponseJson> logout(@RequestBody SessionDto sessionDto) {
		logger.info("Recibido logout game" + sessionDto.toString());
		try {
			sessionDto.validate();
			return  ResponseEntity.ok(this.logoutController.logout(sessionDto));
			
		}
		catch (BadRequestException e) {
			ResponseJson resultJson = new ResponseJson();
			resultJson.setMsg(Message.EMPTY_FIELD);
			resultJson.setError(Error.BAD_REQUEST);
			return new ResponseEntity<ResponseJson>(resultJson,HttpStatus.BAD_REQUEST);
		
		}
		
	}

}
