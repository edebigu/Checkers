package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.services.PlayerDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.CreateGameJson;
import es.ericsson.masterCraftmanship.tfm.views.ErrorView;
import es.ericsson.masterCraftmanship.tfm.views.Message;

@Controller
public class CreateGameController {
	
	private SessionDaoService sessionDaoService;
	private PlayerDaoService playerDaoService;
	
	Logger logger = LogManager.getLogger(CreateGameController.class);
	
	@Autowired
	public CreateGameController (PlayerDaoService playerDaoService, SessionDaoService sessionDaoService) {
		this.playerDaoService = playerDaoService;
		this.sessionDaoService = sessionDaoService;
	}
	
	public CreateGameJson createGame (SessionDto sessionDto) {
		CreateGameJson resultCreateGame = new CreateGameJson();
		if (sessionDaoService.createGameSession(playerDaoService.findPlayerByUsername(sessionDto.getUsername()))){
			resultCreateGame.setMsg(Message.CREATE_GAME_SUCCESSFULL);
			resultCreateGame.setError(ErrorView.CREATED);
			resultCreateGame.setGameName("unsavedGame");
		}
		else {
			resultCreateGame.setMsg(Message.CREATE_GAME_UNSUCCESSFULL);
			resultCreateGame.setError(ErrorView.NOT_FOUND);
		}
		resultCreateGame.setUsername(sessionDto.getUsername());
		return resultCreateGame;	
	}
	
	

}
