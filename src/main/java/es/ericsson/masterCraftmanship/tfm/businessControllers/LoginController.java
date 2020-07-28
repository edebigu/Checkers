package es.ericsson.masterCraftmanship.tfm.businessControllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.PlayerDaoService;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.dtos.PlayerDto;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class LoginController {

	private PlayerDaoService playerDaoService;
	private SessionDaoService sessionDaoService;
	
	Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	public LoginController (PlayerDaoService playerDaoService, SessionDaoService sessionDaoService) {
		this.playerDaoService = playerDaoService;
		this.sessionDaoService = sessionDaoService;
	}
    
    public ResponseJson login (PlayerDto playerDto) {
    	ResponseJson resultLogin = new ResponseJson();
    	Player userFound = playerDaoService.findPlayer(playerDto.getUsername(), playerDto.getPassword());
    	if (userFound != null && sessionDaoService.saveSession(userFound)) {
			resultLogin.setMsg(Message.LOGIN_SUCCESSFULL);
			resultLogin.setError(Error.OK);					
        } 
	   else {
        	resultLogin.setMsg(Message.LOGIN_UNSUCCESSFULL);
        	resultLogin.setError(Error.NOT_FOUND);
        }
    	resultLogin.setUsername(playerDto.getUsername());
    	return resultLogin;
    }
	

}
