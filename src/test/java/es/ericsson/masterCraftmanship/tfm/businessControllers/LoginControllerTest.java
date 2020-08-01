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

import es.ericsson.masterCraftmanship.tfm.dtos.PlayerDto;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.services.PlayerDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LoginController.class)
public class LoginControllerTest {
	
	   @Autowired
	   private LoginController loginController;
	   
	    @MockBean
	    private PlayerDaoService playerDaoService;
	    
	    @MockBean
		private SessionDaoService sessionDaoService;
	    
	    @Before
	    public void setup() {
	    	Player player = new Player("Test", "test");
	    	Mockito.when(playerDaoService.findPlayer("Test", "test")).thenReturn(player);
	    	Mockito.when(sessionDaoService.saveSession(player)).thenReturn(true);
	    	Mockito.when(playerDaoService.findPlayer("TestNonExist", "testNonExist")).thenReturn(null);	    	
	    }
	    
	    @Test
	    public void givenExistPlayer_whenLogin_thenResultOk() {
	    	PlayerDto playerDto = new PlayerDto("Test", "test");
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.OK);
	    	result.setUsername(playerDto.getUsername());
	    	assertEquals(this.loginController.login(playerDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenNonExistPlayer_whenLogin_thenResultNotFound() {
	    	PlayerDto playerDto = new PlayerDto("TestNonExist", "testNonExist");
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.NOT_FOUND);
	    	result.setUsername(playerDto.getUsername());
	    	assertEquals(this.loginController.login(playerDto).toString(), result.toString());
	    }

}
