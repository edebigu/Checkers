package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.views.CreateGameJson;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;

@Controller
public class CreateGameController {
	
	private SessionDaoService sessionDaoService;
	private PlayerDao playerDao;
	
	Logger logger = LogManager.getLogger(CreateGameController.class);
	
	@Autowired
	public CreateGameController (SessionDaoService sessionDaoService, PlayerDao playerDao) {
		this.sessionDaoService = sessionDaoService;
		this.playerDao = playerDao;
	}
	
	public CreateGameJson createGame (SessionDto sessionDto) {
		CreateGameJson resultCreateGame = new CreateGameJson();
		//Session sessionFound = sessionDao.findByPlayer_username(sessionDto.getUsername());
		if (sessionDaoService.createGameSession(playerDao.findByUsername(sessionDto.getUsername()))) {
			/*Game game = new Game();
			game.addPlayer(playerDao.findByUsername(sessionDto.getUsername()));
			Game gameSaved = gameDao.save(game);
			sessionFound.setGame(gameSaved);
		    sessionDao.save(sessionFound);*/
			resultCreateGame.setMsg(Message.CREATE_GAME_SUCCESSFULL);
			resultCreateGame.setError(Error.CREATED);
			resultCreateGame.setGameName("unsavedGame");
		}
		else {
			resultCreateGame.setMsg(Message.CREATE_GAME_UNSUCCESSFULL);
			resultCreateGame.setError(Error.NOT_FOUND);
		}
		resultCreateGame.setUsername(sessionDto.getUsername());
		return resultCreateGame;	
	}
	
	

}
