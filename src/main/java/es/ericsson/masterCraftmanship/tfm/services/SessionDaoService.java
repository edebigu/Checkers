package es.ericsson.masterCraftmanship.tfm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;
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
		Session sessionFound = getSession(player.getUsername());
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
		return !this.getSessionGame(username).getName().equals("");

	}
	
	public boolean saveSession (Player player) {
		if (sessionDao.findByPlayer(player) == null) {
			sessionDao.save(new Session(player, null));
			return true;
		}
		return false;
	}
	
	public Session getSession(String username) {
		return sessionDao.findByPlayer_username(username);
	}
	
	public boolean deleteSession(String username) {
		if (getSession(username) != null) {
			sessionDao.delete(getSession(username));
			return true;
		}
		return false;
	}
	
	public void deleteAllSessions() {
		sessionDao.deleteAll();
	}
	
	
	
	
	

}
