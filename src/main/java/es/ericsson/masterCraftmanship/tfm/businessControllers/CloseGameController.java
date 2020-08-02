package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.dtos.CloseGameDto;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.Result;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class CloseGameController {
	
	private GameDaoService gameDaoService;
	private SessionDaoService sessionDaoService;

	
	@Autowired
	public CloseGameController (GameDaoService gameDaoService, SessionDaoService sessionDaoService) {
		this.gameDaoService = gameDaoService;
		this.sessionDaoService = sessionDaoService;
	}
	
	public ResponseJson closeGame (CloseGameDto closeGameDto) {
		ResponseJson response = new ResponseJson();
		if (!sessionDaoService.isSavedGameSession(closeGameDto.getUsername()) && !closeGameDto.isCloseWithoutSave())
		{
			response.setResult(Result.NOT_FOUND);

		}
		else {
			Game game = gameDaoService.getGameByPlayer(closeGameDto.getUsername(), closeGameDto.getGameName());
			Game sessionGame = sessionDaoService.getSessionGame(closeGameDto.getUsername());
			
			if (!closeGameDto.isCloseWithoutSave()) {
				gameDaoService.saveGame(sessionGame, game.getName(), closeGameDto.getUsername(), true);
			}
			sessionDaoService.saveSessionGame(closeGameDto.getUsername(), null);
			response.setResult(Result.OK);
			
		}

		response.setUsername(closeGameDto.getUsername());
		return response;	
		
	}

	
	
	

}
