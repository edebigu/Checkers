package es.ericsson.masterCraftmanship.tfm.businessControllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;

@Controller
public class StartController {
	
	private SessionDao sessionDao;
	
	@Autowired
	public StartController (SessionDao sessionDao) {
		this.sessionDao = sessionDao;
	}

	
	public void start() {
		sessionDao.deleteAll();
	}

}
