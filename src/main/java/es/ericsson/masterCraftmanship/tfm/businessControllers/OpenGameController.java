package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.Result;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class OpenGameController {

	private GameDaoService gameDaoService;
	private SessionDaoService sessionDaoService;
	
	@Autowired
	public OpenGameController (GameDaoService gameDaoService, SessionDaoService sessionDaoService) {
		this.gameDaoService = gameDaoService;
		this.sessionDaoService = sessionDaoService;
	}
	
	public ResponseJson openGame (SessionDto sessionDto) {
		ResponseJson resultOpenGame = new ResponseJson();
		Game gameFound = gameDaoService.getGameByPlayer(sessionDto.getUsername(), sessionDto.getGameName());
		if (gameFound != null && sessionDaoService.saveSessionGame(sessionDto.getUsername(),gameFound)) {
			resultOpenGame.setResult(Result.OK);
		}
		else {
			resultOpenGame.setResult(Result.NOT_FOUND);
		}
		resultOpenGame.setUsername(sessionDto.getUsername());
		return resultOpenGame;	
		
	}

}
