package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.models.Session;
import es.ericsson.masterCraftmanship.tfm.services.PlayerDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.ErrorView;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class LogoutController {
	
	Logger logger = LogManager.getLogger(LogoutController.class);
	
	private SessionDaoService sessionDaoService;
	
	@Autowired
	public LogoutController (SessionDaoService sessionDaoService) {
		this.sessionDaoService = sessionDaoService;
	}
	
	public ResponseJson logout(SessionDto sessionDto) {
		ResponseJson resultLogout = new ResponseJson();
		if (sessionDaoService.deleteSession(sessionDto.getUsername())) {
			resultLogout.setMsg(Message.LOGOUT_SUCCESS);
		}
		else {
			resultLogout.setMsg(Message.LOGOUT_UNSUCCESSFULL);
		}
		
		resultLogout.setUsername(sessionDto.getUsername());
		return resultLogout;
	}

}
