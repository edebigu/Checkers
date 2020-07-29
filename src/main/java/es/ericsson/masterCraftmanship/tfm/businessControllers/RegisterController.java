package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.dtos.PlayerDto;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.services.PlayerDaoService;
import es.ericsson.masterCraftmanship.tfm.views.Result;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class RegisterController {
	
	private PlayerDaoService playerDaoService;
	Logger logger = LogManager.getLogger(RegisterController.class);
	
	@Autowired
	public RegisterController (PlayerDaoService playerDaoService) {
		this.playerDaoService = playerDaoService;
	}
	
	public ResponseJson register (PlayerDto playerDto) {
    	ResponseJson resultRegister = new ResponseJson();
    	Player player = new Player (playerDto.getUsername(), playerDto.getPassword());
    	if (playerDaoService.savePlayer(playerDto.getUsername(), playerDto.getPassword()) != null) {
    		resultRegister.setResult(Result.OK);
    	}
    	else {
    		resultRegister.setResult(Result.CONFLICT);
    	}
    	resultRegister.setUsername(player.getUsername());
    	return resultRegister;
	}

}
