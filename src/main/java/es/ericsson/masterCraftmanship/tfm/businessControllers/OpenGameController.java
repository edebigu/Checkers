package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class OpenGameController {

	private GameDaoService gameDaoService;
	private SessionDaoService sessionDaoService;
	
	Logger logger = LogManager.getLogger(OpenGameController.class);
	
	@Autowired
	public OpenGameController (GameDaoService gameDaoService, SessionDaoService sessionDaoService) {
		this.gameDaoService = gameDaoService;
		this.sessionDaoService = sessionDaoService;
	}
	
	public ResponseJson openGame (SessionDto sessionDto) {
		ResponseJson resultOpenGame = new ResponseJson();
		Game gameFound = gameDaoService.getGameByPlayer(sessionDto.getUsername(), sessionDto.getGameName());
		if (gameFound != null && sessionDaoService.saveSessionGame(sessionDto.getUsername(),gameFound)) {
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

}
