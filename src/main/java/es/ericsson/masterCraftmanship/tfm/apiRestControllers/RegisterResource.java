package es.ericsson.masterCraftmanship.tfm.apiRestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ericsson.masterCraftmanship.tfm.businessControllers.RegisterController;
import es.ericsson.masterCraftmanship.tfm.dtos.PlayerDto;
import es.ericsson.masterCraftmanship.tfm.views.Result;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@RestController
@RequestMapping(RegisterResource.REGISTER)
public class RegisterResource {
	
	static final String REGISTER = "/register";

	
	private RegisterController registerController;
	
	@Autowired
	public RegisterResource(RegisterController registerController) {
		this.registerController = registerController;
	}
	
	@PostMapping
	public ResponseEntity<ResponseJson> login(@RequestBody PlayerDto playerDto) {
		try {
			playerDto.validate();
			return ResponseEntity.ok(this.registerController.register(playerDto));
		}
		catch (BadRequestException e) {
			ResponseJson resultRegister = new ResponseJson();
			resultRegister.setResult(Result.BAD_REQUEST);
			return new ResponseEntity<ResponseJson>(resultRegister,HttpStatus.BAD_REQUEST);
		}
		
	}

}
