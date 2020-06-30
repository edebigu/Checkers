package es.ericsson.masterCraftmanship.tfm.businessControllers;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.apiRestControllers.LoginResource;
import es.ericsson.masterCraftmanship.tfm.daos.UserDao;
import es.ericsson.masterCraftmanship.tfm.dtos.UserDto;
import es.ericsson.masterCraftmanship.tfm.views.LoginJson;

//public class LoginController extends OperationController {
@Controller
public class LoginController {

	private UserDao userDao;
	Logger logger = LogManager.getLogger(LoginResource.class);
	
	@Autowired
	public LoginController (UserDao userDao) {
		this.userDao = userDao;
	}
	
	/*protected LoginController (Game game, State state) {
		super (game, state);
	}
	
    @Override
    public void accept(OperationControllerVisitor operationControllerVisitor) {
        operationControllerVisitor.visit(this);
    }*/
	
    
    public LoginJson login (UserDto user) {
    	LoginJson resultLogin = new LoginJson();
    	UserDto userFound = userDao.findByUsername(user.getUsername());
    	if (userFound != null && userFound.getPassword().equals(user.getPassword())) {
			resultLogin.setMsg("User found");
        } 
	   else  {
        	resultLogin.setMsg("User not found");
        }
    	resultLogin.setUsername(user.getUsername());
    	return resultLogin;
    }
	

}
