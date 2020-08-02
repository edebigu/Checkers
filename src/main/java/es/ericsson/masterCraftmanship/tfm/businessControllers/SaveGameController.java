package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.dtos.SaveGameDto;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.Result;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class SaveGameController {
	private GameDaoService gameDaoService;
	private SessionDaoService sessionDaoService;

	@Autowired
	public SaveGameController(GameDaoService gameDaoService, SessionDaoService sessionDaoService) {

		this.gameDaoService = gameDaoService;
		this.sessionDaoService = sessionDaoService;
	}

	public ResponseJson save(SaveGameDto saveGameDto) {
		ResponseJson response = new ResponseJson();
		Boolean overwrite = saveGameDto.isOverwrite();
		if (sessionDaoService.isSavedGameSession(saveGameDto.getUsername())) {
			overwrite = true;
		}
		Game game = gameDaoService.saveGame(sessionDaoService.getSessionGame(saveGameDto.getUsername()), saveGameDto.getGameName(), saveGameDto.getUsername(), overwrite);
		if (game != null) {
			response.setResult(Result.OK);
			sessionDaoService.saveSessionGame(saveGameDto.getUsername(), game);
		} else {
			if (saveGameDto.getGameName().equals("")) {
				response.setResult(Result.NOT_FOUND);
			} else {
				response.setResult(Result.CONFLICT);
			}
		}
		response.setUsername(saveGameDto.getUsername());
		return response;

	}

}
