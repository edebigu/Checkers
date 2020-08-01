package es.ericsson.masterCraftmanship.tfm.businessControllers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LogoutController.class)
public class LogoutControllerTest {
	
	
	   @Autowired
	   private LogoutController logoutController;
	     
	    @MockBean
		private SessionDaoService sessionDaoService;
	    
	    @Before
	    public void setup() {
	    	Mockito.when(sessionDaoService.deleteSession("UserTest")).thenReturn(true);	
	    	Mockito.when(sessionDaoService.deleteSession("UserTestNonExist")).thenReturn(false);	
	    }
	    
	    @Test
	    public void givenExistSession_whenLogout_thenResultOk() {
	    	SessionDto sessionDto = new SessionDto("UserTest", "existTestGame");
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.OK);
	    	result.setUsername(sessionDto.getUsername());
	    	assertEquals(this.logoutController.logout(sessionDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenNonExistSession_whenLogout_thenResultNotFound() {
	    	SessionDto sessionDto = new SessionDto("UserTestNonExist", "existTestGame");
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.NOT_FOUND);
	    	result.setUsername(sessionDto.getUsername());
	    	assertEquals(this.logoutController.logout(sessionDto).toString(), result.toString());
	    }

}
