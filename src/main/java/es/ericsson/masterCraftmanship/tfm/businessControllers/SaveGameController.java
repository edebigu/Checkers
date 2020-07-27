package es.ericsson.masterCraftmanship.tfm.businessControllers;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;
import es.ericsson.masterCraftmanship.tfm.dtos.SaveGameDto;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Session;
import es.ericsson.masterCraftmanship.tfm.views.Error;
import es.ericsson.masterCraftmanship.tfm.views.Message;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class SaveGameController {
	private SessionDao sessionDao;
	private GameDao gameDao;
	
	Logger logger = LogManager.getLogger(SaveGameController.class);
	
	@Autowired
	public SaveGameController(GameDao gameDao, SessionDao sessionDao) {
		this.gameDao = gameDao;
		this.sessionDao = sessionDao;
	}
	
	public ResponseJson save(SaveGameDto saveGameDto) {
		ResponseJson response = new ResponseJson();
		Session session = sessionDao.findByPlayer_username(saveGameDto.getUsername());
			if (!session.getGame().getName().equals("unsavedGame")) {
				response.setMsg(Message.SAVE_GAME_SUCCESS);
				response.setError(Error.OK);
			}
			else {
				
				List<Game> listGames = gameDao.findByPlayer_username(saveGameDto.getUsername());
				if (saveGameDto.getGameName().equals("unsavedGame")) {
					response.setMsg(Message.SAVE_GAME_UNSUCCESS);
					response.setError(Error.NOT_FOUND);
				}
				else if (getGameByName(listGames, saveGameDto.getGameName()) == -1 && saveGameDto.getGameName() != "") {
					response.setMsg(Message.SAVE_GAME_SUCCESS);
					response.setError(Error.CREATED);
					Game game = session.getGame();
					gameDao.delete(game);
					game.setName(saveGameDto.getGameName());
					gameDao.save(game);
					session.setGame(game);
					sessionDao.save(session);
				}
				else if (saveGameDto.isOverwrite()) {
					Game oldGame = listGames.get(getGameByName(listGames, saveGameDto.getGameName()));
					response.setMsg(Message.SAVE_GAME_SUCCESS);
					response.setError(Error.CREATED);
					Game game = session.getGame();
					gameDao.delete(game);
					game.setName(saveGameDto.getGameName());
					game.setId(oldGame.getId());
					gameDao.save(game);
					session.setGame(game);
					sessionDao.save(session);
				}
				else {
						response.setMsg(Message.SAVE_GAME_UNSUCCESS);
						response.setError(Error.CONFLICT);
				}
				
			}

		response.setUsername(saveGameDto.getUsername());
		return response;
		
	}
	
	private int getGameByName (List<Game> listGame, String gameName) {
		int index=-1;
		for (int i=0; i<listGame.size(); i++) {
			if (listGame.get(i).getName().equals(gameName)) {
				index=i;
			}
		}
		
		return index;
	}
	
}
