package es.ericsson.masterCraftmanship.tfm.businessControllers;

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
public class CloseGameController {
	
	private GameDao gameDao;
	private SessionDao sessionDao;
	private PlayerDao playerDao;
	
	@Autowired
	public CloseGameController (GameDao gameDao, SessionDao sessionDao, PlayerDao playerDao) {
		this.gameDao = gameDao;
		this.sessionDao = sessionDao;
		this.playerDao = playerDao;
	}
	
	public ResponseJson closeGame (SessionDto sessionDto) {
		ResponseJson result = new ResponseJson();
		Player playerFound = playerDao.findByUsername(sessionDto.getUsername());
		Session sessionFound = sessionDao.findByPlayer(playerFound);

		if (!sessionDto.getGameName().equals("unsaveGame") && sessionFound != null &&  gameDao.findById(sessionDto.getGameName()).isPresent()) {
			result.setMsg(Message.CLOSE_GAME_SUCCESS);
			result.setError(Error.OK);
		}
		else {
			result.setMsg(Message.CLOSE_GAME_UNSUCCESS);
			result.setError(Error.NOT_FOUND);
		}

		result.setUsername(playerFound.getUsername());
		return result;	
		
	}
	
	
	

}
