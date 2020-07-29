package es.ericsson.masterCraftmanship.tfm.businessControllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;

@Controller
public class StartController {
	
	private SessionDaoService sessionDaoService;

	
	@Autowired
	public StartController (SessionDaoService sessionDaoService) {
		this.sessionDaoService = sessionDaoService;
	}

	
	public void start() {
		sessionDaoService.deleteAllSessions();

	}

}
