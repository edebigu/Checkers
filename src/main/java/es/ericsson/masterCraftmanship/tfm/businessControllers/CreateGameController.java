package es.ericsson.masterCraftmanship.tfm.businessControllers;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Session;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class CreateGameController {
	
	private GameDao gameDao;
	private SessionDao sessionDao;
	private PlayerDao playerDao;
	
	Logger logger = LogManager.getLogger(CreateGameController.class);
	
	@Autowired
	public CreateGameController (GameDao gameDao, SessionDao sessionDao, PlayerDao playerDao) {
		this.gameDao = gameDao;
		this.sessionDao = sessionDao;
		this.playerDao = playerDao;
	}
	
	public ResponseJson createGame (SessionDto sessionDto) {
		ResponseJson resultCreateGame = new ResponseJson();
		Session sessionFound = sessionDao.findByPlayer(playerDao.findByUsername(sessionDto.getUsername()));
		if (sessionFound != null) {
			Game game = new Game();
			game.setId("activeGame");
			Game gameSaved = gameDao.save(game);
			sessionFound.setGame(gameSaved);
		    sessionDao.save(sessionFound);
			resultCreateGame.setMsg(Message.CREATE_GAME_SUCCESSFULL);
			resultCreateGame.setError(Error.CREATED);
		}
		else {
			resultCreateGame.setMsg(Message.CREATE_GAME_IMSUCCESSFULL);
			resultCreateGame.setError(Error.NOT_FOUND);
		}
		resultCreateGame.setUsername(sessionDto.getUsername());
		return resultCreateGame;	
	}
	
	

}
