package es.ericsson.masterCraftmanship.tfm.businessControllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;

@Controller
public class StartController {
	
	private SessionDao sessionDao;
	private GameDao gameDao;
	
	@Autowired
	public StartController (SessionDao sessionDao, GameDao gameDao) {
		this.sessionDao = sessionDao;
		this.gameDao = gameDao;
	}

	
	public void start() {
		sessionDao.deleteAll();
         gameDao.deleteByGameName("unsavedGame");

	}

}
