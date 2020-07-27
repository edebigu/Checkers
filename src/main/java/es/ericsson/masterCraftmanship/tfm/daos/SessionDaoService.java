package es.ericsson.masterCraftmanship.tfm.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.models.Session;


@Service
public class SessionDaoService {
	
	@Autowired
	private SessionDao sessionDao;
	
	public boolean saveSessionGame (String username, Game game) {
		Session session = sessionDao.findByPlayer_username(username);
		if (session != null) {
			session.setGame(game);
			sessionDao.save(session);
			return true;
		}
		return false;
	}
	
	public boolean createGameSession (Player player) {
		Session sessionFound = sessionDao.findByPlayer_username(player.getUsername());
	    if (sessionFound != null){
	    	Game game = new Game();
	    	game.addPlayer(player);
	    	sessionFound.setGame(game);
	    	sessionDao.save(sessionFound);
	    	return true;
	    }
		return false;
	}
	
	public Game getSessionGame(String username) {
		return sessionDao.findByPlayer_username(username).getGame();
		
	}
	
	public boolean isSavedSession(String username) {
		//return this.getSessionGame(username).equals("unsavedGame");
		if (this.getSessionGame(username).getName().equals("unsavedGame")) {
			return false;
		}
		return true;
	}
	
	
	
	
	

}
