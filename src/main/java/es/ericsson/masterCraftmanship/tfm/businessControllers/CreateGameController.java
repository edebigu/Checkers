package es.ericsson.masterCraftmanship.tfm.businessControllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.services.PlayerDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.CreateGameJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@Controller
public class CreateGameController {
	
	private SessionDaoService sessionDaoService;
	private PlayerDaoService playerDaoService;

	
	@Autowired
	public CreateGameController (PlayerDaoService playerDaoService, SessionDaoService sessionDaoService) {
		this.playerDaoService = playerDaoService;
		this.sessionDaoService = sessionDaoService;
	}
	
	public CreateGameJson createGame (SessionDto sessionDto) {
		CreateGameJson resultCreateGame = new CreateGameJson();
		if (sessionDaoService.createGameSession(playerDaoService.findPlayerByUsername(sessionDto.getUsername()))){
			resultCreateGame.setResult(Result.OK);
		}
		else {
			resultCreateGame.setResult(Result.NOT_FOUND);
		}
		resultCreateGame.setUsername(sessionDto.getUsername());
		return resultCreateGame;	
	}
	
	

}
