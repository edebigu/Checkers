package es.ericsson.masterCraftmanship.tfm.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ericsson.masterCraftmanship.tfm.models.Game;
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
	
	

}
