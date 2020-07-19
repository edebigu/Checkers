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
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class LogoutController {
	
	Logger logger = LogManager.getLogger(LogoutController.class);
	
	private SessionDao sessionDao;
	private PlayerDao playerDao;
	
	@Autowired
	public LogoutController (PlayerDao playerDao, SessionDao sessionDao) {
		this.playerDao = playerDao;
		this.sessionDao = sessionDao;
	}
	
	public ResponseJson logout(SessionDto sessionDto) {
		ResponseJson resultLogout = new ResponseJson();
		Player playerFound = playerDao.findByUsername(sessionDto.getUsername());
		Session sessionFound = sessionDao.findByPlayer(playerFound);
		resultLogout.setUsername(playerFound.getUsername());
		if (sessionFound != null) {
			sessionDao.delete(sessionFound);
			resultLogout.setMsg(Message.LOGOUT_SUCCESS);
		}
		else {
			resultLogout.setMsg(Message.LOGOUT_UNSUCCESSFULL);
		}

		return resultLogout;
	}

}
