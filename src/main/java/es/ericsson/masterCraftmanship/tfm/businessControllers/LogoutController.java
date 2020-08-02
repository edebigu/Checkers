package es.ericsson.masterCraftmanship.tfm.businessControllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.Result;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;

@Controller
public class LogoutController {

	
	private SessionDaoService sessionDaoService;
	
	@Autowired
	public LogoutController (SessionDaoService sessionDaoService) {
		this.sessionDaoService = sessionDaoService;
	}
	
	public ResponseJson logout(SessionDto sessionDto) {
		ResponseJson resultLogout = new ResponseJson();
		if (sessionDaoService.deleteSession(sessionDto.getUsername())) {
			resultLogout.setResult(Result.OK);;
		}
		else {
			resultLogout.setResult(Result.NOT_FOUND);
		}
		
		resultLogout.setUsername(sessionDto.getUsername());
		return resultLogout;
	}

}
