package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(StartResource.START)
public class StartResource {
	
	Logger logger = LogManager.getLogger(StartResource.class);
	
	static final String START = "/start";
	static final String START_LOGIN = "/login";
	static final String START_REGISTER = "/register";
	
	@GetMapping
	public ResponseEntity started() {
		logger.info("Recibido start");
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(StartResource.START_LOGIN)
	public ResponseEntity startLogin() {
		logger.info("Recibido start login");
		return ResponseEntity.ok().build();
	}
	
	@GetMapping(StartResource.START_REGISTER)
	public ResponseEntity startRegister() {
		logger.info("Recibido start register");
		return ResponseEntity.ok().build();
	}
	
	
	

}
