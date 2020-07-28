package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.views.CloseGameDto;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class CloseGameController {
	
	private GameDaoService gameDaoService;
	private SessionDaoService sessionDaoService;
	
	Logger logger = LogManager.getLogger(CloseGameController.class);
	
	@Autowired
	public CloseGameController (GameDaoService gameDaoService, SessionDaoService sessionDaoService) {
		this.gameDaoService = gameDaoService;
		this.sessionDaoService = sessionDaoService;
	}
	
	public ResponseJson closeGame (CloseGameDto operationGameDto) {
		ResponseJson response = new ResponseJson();
		Game game = gameDaoService.getGameByPlayer(operationGameDto.getUsername(), operationGameDto.getGameName());
		Game sessionGame = sessionDaoService.getSessionGame(operationGameDto.getUsername());
		if (!sessionDaoService.isSavedSession(operationGameDto.getUsername()) && !operationGameDto.isCloseWithoutSave())
		{
			response.setMsg(Message.CLOSE_GAME_UNSUCCESS);
			response.setError(Error.NOT_FOUND);

		}
		else {
			logger.info("else saved session");
			
			if (!operationGameDto.isCloseWithoutSave()) {
				logger.info("salvando juego de session");
				gameDaoService.saveGame(sessionGame, game.getName(), operationGameDto.getUsername(), true);
			}
			sessionDaoService.saveSessionGame(operationGameDto.getUsername(), null);
			response.setMsg(Message.CLOSE_GAME_SUCCESS);
			response.setError(Error.OK);
			
		}

		response.setUsername(operationGameDto.getUsername());
		return response;	
		
	}

	
	
	

}
