package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.models.Session;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class CloseGameController {
	
	private GameDao gameDao;
	private SessionDao sessionDao;
	
	@Autowired
	public CloseGameController (GameDao gameDao, SessionDao sessionDao) {
		this.gameDao = gameDao;
		this.sessionDao = sessionDao;
	}
	
	public ResponseJson closeGame (SessionDto sessionDto) {
		ResponseJson result = new ResponseJson();
		Session session = sessionDao.findByPlayer_username(sessionDto.getUsername());
		if (sessionDto.getGameName().equals("")) {
			gameDao.delete(session.getGame());
			session.setGame(null);
			sessionDao.save(session);
		}
		else if (session.getGame().getName().equals("unsavedGame"))
		{
			result.setMsg(Message.CLOSE_GAME_UNSUCCESS);
			result.setError(Error.NOT_FOUND);
		}
		else {
			session.setGame(null);
			sessionDao.save(session);
			result.setMsg(Message.CLOSE_GAME_SUCCESS);
			result.setError(Error.OK);
		}

		result.setUsername(sessionDto.getUsername());
		return result;	
		
	}

	
	
	

}
