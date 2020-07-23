package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ericsson.masterCraftmanship.tfm.businessControllers.StartController;

@RestController
@RequestMapping(StartResource.START)
public class StartResource {
	
	Logger logger = LogManager.getLogger(StartResource.class);
	
	static final String START = "/start";
	static final String START_LOGIN = "/login";
	static final String START_REGISTER = "/register";
	
	private StartController startController;
	
	@Autowired
	public StartResource(StartController startController) {
		this.startController = startController;
	}
	
	@GetMapping
	public ResponseEntity started() {
		logger.info("Recibido start");
		this.startController.start();
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
