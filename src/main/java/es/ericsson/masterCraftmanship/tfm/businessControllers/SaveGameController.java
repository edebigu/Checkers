package es.ericsson.masterCraftmanship.tfm.businessControllers;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.dtos.SaveGameDto;
import es.ericsson.masterCraftmanship.tfm.models.Coordinate;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class SaveGameController {
	private PlayerDao playerDao;
	private GameDao gameDao;
	
	Logger logger = LogManager.getLogger(SaveGameController.class);
	
	@Autowired
	public SaveGameController(GameDao gameDao, PlayerDao playerDao) {
		this.gameDao = gameDao;
		this.playerDao = playerDao;
	}
	
	public ResponseJson save(SaveGameDto saveGameDto) {
		ResponseJson response = new ResponseJson();
		Player player = playerDao.findByUsername(saveGameDto.getUsername());
		Game game = gameDao.findById(saveGameDto.getGameName()).get();
		if (gameDao.findById(saveGameDto.getNewGameName()).isPresent() && !saveGameDto.isOverwrite()) {
			response.setMsg(Message.SAVE_GAME_UNSUCCESS);
			response.setError(Error.CONFLICT);
		}
		else {
			if (game.getId().equals("unsaveGame")) {
				gameDao.delete(game);
			}
			game.setId(saveGameDto.getNewGameName());
			gameDao.save(game);
			
			response.setUsername(saveGameDto.getUsername());
			response.setMsg(Message.SAVE_GAME_SUCCESS);
			response.setError(Error.CREATED);
		}
		return response;
		
	}
	
}
