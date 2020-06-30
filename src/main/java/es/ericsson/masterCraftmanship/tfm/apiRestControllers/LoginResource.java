package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ericsson.masterCraftmanship.tfm.businessControllers.LoginController;
import es.ericsson.masterCraftmanship.tfm.daos.UserDao;
import es.ericsson.masterCraftmanship.tfm.dtos.UserDto;
import es.ericsson.masterCraftmanship.tfm.views.LoginJson;

@RestController
@RequestMapping(LoginResource.LOGIN)
public class LoginResource {
	
	static final String LOGIN = "/login";

	Logger logger = LogManager.getLogger(LoginResource.class);
	
	private LoginController loginController;

    @Autowired
	private UserDao userRepository;
	
	@Autowired
	public LoginResource(LoginController loginController) {
		this.loginController = loginController;
	}

	@PostConstruct
	public void init() {

		userRepository.deleteAll();
		UserDto user1 = new UserDto("Debora", "debora");
		userRepository.save(user1);


	}

	@PostMapping
	public ResponseEntity<LoginJson> login(@RequestBody UserDto user) {
		logger.info("Recibido json" + user.toString());
		user.validate();
		return ResponseEntity.ok(this.loginController.login(user));
	}

}
