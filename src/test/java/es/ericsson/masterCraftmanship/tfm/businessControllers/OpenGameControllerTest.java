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
import es.ericsson.masterCraftmanship.tfm.models.Color;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.GameTest;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenGameController.class)
public class OpenGameControllerTest extends GameTest{
	
	   @Autowired
	   private OpenGameController openGameController;
	     
	    @MockBean
		private SessionDaoService sessionDaoService;
	    @MockBean
		private GameDaoService gameDaoService;
	    
	    @Before
	    public void setup() {
	    	Game game = new Game();
	    	game.setName("userTestGame");
	        this.setGameWithName(Color.WHITE, "userTestGame",
	            " n n n n",
	            "n n n n ",
	            "   n n n",
	            "n       ",
	            " b      ",
	            "  b b b ",
	            " b b b b",
	            "b b b b ");
	    	Mockito.when(gameDaoService.getGameByPlayer("UserTest", "userTestGame")).thenReturn(game);	
	    	Mockito.when(sessionDaoService.saveSessionGame("UserTest", game)).thenReturn(true);
	    	Mockito.when(gameDaoService.getGameByPlayer("UserTest", "userTestGameNonExist")).thenReturn(null);
	    	Mockito.when(sessionDaoService.saveSessionGame("UserNonExistTest", game)).thenReturn(false);
	    }
	    
	    @Test
	    public void givenExistGame_whenOpenGame_thenResultOk() {
	    	SessionDto sessionDto = new SessionDto("UserTest", "userTestGame");
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.OK);
	    	result.setUsername(sessionDto.getUsername());
	    	assertEquals(this.openGameController.openGame(sessionDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenNonExistGame_whenOpenGame_thenResultNotFound() {
	    	SessionDto sessionDto = new SessionDto("UserTest", "userTestGameNonExist");
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.NOT_FOUND);
	    	result.setUsername(sessionDto.getUsername());
	    	assertEquals(this.openGameController.openGame(sessionDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenNonExistSessionPlayer_whenOpenGame_thenResultNotFound() {
	    	SessionDto sessionDto = new SessionDto("UserNonExisTest", "userTestGame");
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.NOT_FOUND);
	    	result.setUsername(sessionDto.getUsername());
	    	assertEquals(this.openGameController.openGame(sessionDto).toString(), result.toString());
	    }
	    

	
}
