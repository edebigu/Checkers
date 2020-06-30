package es.ericsson.masterCraftmanship.tfm.businessControllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;
import es.ericsson.masterCraftmanship.tfm.dtos.PlayerDto;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.models.Session;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class LoginController {

	private PlayerDao playerDao;
	private SessionDao sessionDao;
	
	Logger logger = LogManager.getLogger(LoginController.class);
	
	@Autowired
	public LoginController (PlayerDao playerDao, SessionDao sessionDao) {
		this.playerDao = playerDao;
		this.sessionDao = sessionDao;
	}
    
    public ResponseJson login (PlayerDto playerDto) {
    	ResponseJson resultLogin = new ResponseJson();
    	Player player = new Player (playerDto.getUsername(), playerDto.getPassword());
    	Player userFound = this.playerDao.findByUsername(player.getUsername());
    	if (userFound != null && userFound.getPassword().equals(player.getPassword())) {
			resultLogin.setMsg(Message.LOGIN_SUCCESSFULL);
			resultLogin.setError(Error.OK);
			if (this.sessionDao.findByPlayer(userFound) == null) {
				sessionDao.save(new Session (userFound, null));
			}
					
        } 
	   else {
        	resultLogin.setMsg(Message.LOGIN_UNSUCCESSFULL);
        	resultLogin.setError(Error.NOT_FOUND);
        }
    	resultLogin.setUsername(player.getUsername());
    	return resultLogin;
    }
	

}
