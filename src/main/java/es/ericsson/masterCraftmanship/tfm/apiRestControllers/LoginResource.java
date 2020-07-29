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

import es.ericsson.masterCraftmanship.tfm.businessControllers.LoginController;
import es.ericsson.masterCraftmanship.tfm.dtos.PlayerDto;
import es.ericsson.masterCraftmanship.tfm.exceptions.BadRequestException;
import es.ericsson.masterCraftmanship.tfm.views.ErrorView;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@RestController
@RequestMapping(LoginResource.LOGIN)
public class LoginResource {
	
	static final String LOGIN = "/login";

	Logger logger = LogManager.getLogger(LoginResource.class);
	
	private LoginController loginController;

	@Autowired
	public LoginResource(LoginController loginController) {
		this.loginController = loginController;
	}

	
	@PostMapping
	public ResponseEntity<ResponseJson> login(@RequestBody PlayerDto playerDto) {
		logger.info("Recibido json" + playerDto.toString());
		try {
			playerDto.validate();
			return ResponseEntity.ok(this.loginController.login(playerDto));
		}
		catch (BadRequestException e) {
			ResponseJson resultLogin = new ResponseJson();
			resultLogin.setMsg(Message.EMPTY_FIELD);
			resultLogin.setError(ErrorView.BAD_REQUEST);
			return new ResponseEntity<ResponseJson>(resultLogin,HttpStatus.BAD_REQUEST);
		}
		
	}

}
