package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.dtos.PlayerDto;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class RegisterController {
	
	private PlayerDao userDao;
	Logger logger = LogManager.getLogger(RegisterController.class);
	
	@Autowired
	public RegisterController (PlayerDao userDao) {
		this.userDao = userDao;
	}
	
	public ResponseJson register (PlayerDto playerDto) {
    	ResponseJson resultRegister = new ResponseJson();
    	Player player = new Player (playerDto.getUsername(), playerDto.getPassword());
    	if (this.userDao.findByUsername(player.getUsername()) == null) {
    		this.userDao.save(player);
    		resultRegister.setMsg(Message.REGISTER_SUCCESSFULL);
    		resultRegister.setError(Error.CREATED);
    	}
    	else {
    		resultRegister.setMsg(Message.REGISTER_UNSUCCESSFULL);
    		resultRegister.setError(Error.CONFLICT);
    	}
    	resultRegister.setUsername(player.getUsername());
    	return resultRegister;
	}

}
