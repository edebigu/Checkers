package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.dtos.CloseGameDto;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.ErrorView;
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
			response.setError(ErrorView.NOT_FOUND);

		}
		else {
			
			if (!operationGameDto.isCloseWithoutSave()) {
				gameDaoService.saveGame(sessionGame, game.getName(), operationGameDto.getUsername(), true);
			}
			sessionDaoService.saveSessionGame(operationGameDto.getUsername(), null);
			response.setError(ErrorView.OK);
			
		}

		response.setUsername(operationGameDto.getUsername());
		return response;	
		
	}

	
	
	

}
