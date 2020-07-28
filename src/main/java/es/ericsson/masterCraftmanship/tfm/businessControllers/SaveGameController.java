package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.dtos.SaveGameDto;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class SaveGameController {
	private GameDaoService gameDaoService;
	private SessionDaoService sessionDaoService;

	Logger logger = LogManager.getLogger(SaveGameController.class);

	@Autowired
	public SaveGameController(GameDaoService gameDaoService, SessionDaoService sessionDaoService) {

		this.gameDaoService = gameDaoService;
		this.sessionDaoService = sessionDaoService;
	}

	public ResponseJson save(SaveGameDto saveGameDto) {
		ResponseJson response = new ResponseJson();
		Boolean overwrite = saveGameDto.isOverwrite();
		if (sessionDaoService.isSavedSession(saveGameDto.getUsername())) {
			overwrite = true;
		}
		Game game = gameDaoService.saveGame(sessionDaoService.getSessionGame(saveGameDto.getUsername()), saveGameDto.getGameName(), saveGameDto.getUsername(), overwrite);
		if (game != null) {
			response.setMsg(Message.SAVE_GAME_SUCCESS);
			response.setError(Error.CREATED);
			sessionDaoService.saveSessionGame(saveGameDto.getUsername(), game);
		} else {
			response.setMsg(Message.SAVE_GAME_UNSUCCESS);
			if (saveGameDto.getGameName().equals("")) {
				response.setError(Error.NOT_FOUND);
			} else {
				response.setError(Error.CONFLICT);
			}
		}
		response.setUsername(saveGameDto.getUsername());
		return response;

	}

}
