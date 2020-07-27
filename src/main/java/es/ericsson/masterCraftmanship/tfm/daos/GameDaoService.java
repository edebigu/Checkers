package es.ericsson.masterCraftmanship.tfm.daos;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ericsson.masterCraftmanship.tfm.models.Game;

@Service
public class GameDaoService {
	
	@Autowired
	private GameDao gameDao;
	
	public Game getGameByPlayer (String userName, String gameName) {
		List<Game> listGame = gameDao.findByPlayer_username(userName);
		Game game = null;
		for (int i=0; i<listGame.size(); i++) {
			if (listGame.get(i).getName().equals(gameName)) {
				game=listGame.get(i);
			}
		}
		
		return game;
		
	}

}
