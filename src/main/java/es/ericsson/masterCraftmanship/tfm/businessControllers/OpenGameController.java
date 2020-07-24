package es.ericsson.masterCraftmanship.tfm.businessControllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.models.Session;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class OpenGameController {
	
	private GameDao gameDao;
	private SessionDao sessionDao;
	
	Logger logger = LogManager.getLogger(OpenGameController.class);
	
	@Autowired
	public OpenGameController (GameDao gameDao, SessionDao sessionDao) {
		this.gameDao = gameDao;
		this.sessionDao = sessionDao;
	}
	
	public ResponseJson openGame (SessionDto sessionDto) {
		ResponseJson resultOpenGame = new ResponseJson();
		Session session = sessionDao.findByPlayer_username(sessionDto.getUsername());
		List<Game> listGame = gameDao.findByPlayer_username(sessionDto.getUsername());
		if (session != null &&  getGameByName(listGame, sessionDto.getGameName()) != -1) {
			session.setGame(listGame.get(getGameByName(listGame, sessionDto.getGameName())));
			sessionDao.save(session);
			resultOpenGame.setMsg(Message.OPEN_GAME_SUCCESS);
			resultOpenGame.setError(Error.OK);
		}
		else {
			resultOpenGame.setMsg(Message.OPEN_GAME_UNSUCCESS);
			resultOpenGame.setError(Error.NOT_FOUND);
		}
		resultOpenGame.setUsername(sessionDto.getUsername());
		return resultOpenGame;	
		
	}
	
	private int getGameByName (List<Game> listGame, String gameName) {
		int index=-1;
		for (int i=0; i<listGame.size(); i++) {
			if (listGame.get(i).getName().equals(gameName)) {
				index=i;
			}
		}
		
		return index;
	}

}
