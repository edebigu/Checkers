package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ericsson.masterCraftmanship.tfm.businessControllers.StartController;

@RestController
@RequestMapping(StartResource.START)
public class StartResource {

	
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
		this.startController.start();
		return ResponseEntity.ok().build();
	}

	
	
	

}
