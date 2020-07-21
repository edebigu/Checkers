package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.models.Session;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class OpenGameController {
	
	private GameDao gameDao;
	private SessionDao sessionDao;
	private PlayerDao playerDao;
	
	Logger logger = LogManager.getLogger(OpenGameController.class);
	
	@Autowired
	public OpenGameController (GameDao gameDao, SessionDao sessionDao, PlayerDao playerDao) {
		this.gameDao = gameDao;
		this.sessionDao = sessionDao;
		this.playerDao = playerDao;
	}
	
	public ResponseJson openGame (SessionDto sessionDto) {
		ResponseJson resultOpenGame = new ResponseJson();
		Player playerFound = playerDao.findByUsername(sessionDto.getUsername());
		Session sessionFound = sessionDao.findByPlayer(playerFound);
		if (sessionFound != null &&  gameDao.findById(sessionDto.getGameName()).isPresent()) {
			resultOpenGame.setMsg(Message.OPEN_GAME_SUCCESS);
			resultOpenGame.setError(Error.OK);
		}
		else {
			resultOpenGame.setMsg(Message.OPEN_GAME_UNSUCCESS);
			resultOpenGame.setError(Error.NOT_FOUND);
		}
		resultOpenGame.setUsername(playerFound.getUsername());
		return resultOpenGame;	
		
	}

}
