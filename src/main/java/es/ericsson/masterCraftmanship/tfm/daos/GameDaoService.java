package es.ericsson.masterCraftmanship.tfm.daos;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ericsson.masterCraftmanship.tfm.businessControllers.CloseGameController;
import es.ericsson.masterCraftmanship.tfm.models.Game;

@Service
public class GameDaoService {
	
	@Autowired
	private GameDao gameDao;
	
	public Game getGameByPlayer (String username, String gameName) {
		List<Game> listGame = this.getGamesByPlayer(username);
		Game gameFound = null;
		for (Game game : listGame) {
			if (game.getName().equals(gameName)) {
				gameFound=game;
			}
		}
		
		return gameFound;
		
	}
	
	public Game saveGame(Game game, String gameName, String username, Boolean overwrite) {
		if(overwrite) {
			return this.overwriteGame(gameName,username,game);
		}
		else {
			if (this.getGameByPlayer(username, gameName) == null && !gameName.equals("")) {
				game.setName(gameName);
				gameDao.save(game);
				return game;
			}
			else {
				return null;
			}
		}
		
	}
	private Game overwriteGame (String gameName, String username, Game game) {
		Game gameOverwrited = null;
		gameOverwrited = this.getGameByPlayer(username, gameName);
		if (gameOverwrited != null) {
			gameDao.deleteByGameName(game.getName());
			gameDao.delete(gameOverwrited);
			gameOverwrited = new Game (game);
			gameOverwrited.setName(gameName);
			gameDao.save(gameOverwrited);
		}
		return gameOverwrited;
	}
	
	public List<Game> getGamesByPlayer (String username){
		List<Game> listGame = new ArrayList();
		return gameDao.findByPlayer_username(username);
	}

	
}
