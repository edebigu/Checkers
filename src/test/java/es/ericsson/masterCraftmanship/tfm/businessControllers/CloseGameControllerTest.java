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

import es.ericsson.masterCraftmanship.tfm.dtos.CloseGameDto;
import es.ericsson.masterCraftmanship.tfm.models.Color;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.GameTest;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CloseGameController.class)
public class CloseGameControllerTest extends GameTest {
	
	   @Autowired
	   private CloseGameController closeGameController;
	     
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
	        
	        Game gameSession = new Game();
	        gameSession.setName("userTestGame");
	        this.setExpectedGame(Color.WHITE,
	            " n n n n",
	            "n n n n ",
	            " n n n n",
	            "        ",
	            "        ",
	            "b b b b ",
	            " b b b b",
	            "b b b b ");

	    	Mockito.when(gameDaoService.getGameByPlayer("UserTest", "userTestGame")).thenReturn(game);
	    	Mockito.when(sessionDaoService.getSessionGame("UserTest")).thenReturn(game);
	    	Mockito.when(sessionDaoService.isSavedGameSession("UserTest")).thenReturn(true);
	    	
	    	Mockito.when(sessionDaoService.isSavedGameSession("UserNonExisTest")).thenReturn(false);
	    	
	    	Mockito.when(gameDaoService.saveGame(game, "userTestGame", "UserTest", true)).thenReturn(game);
	    	Mockito.when(sessionDaoService.saveSessionGame("UserTest", null)).thenReturn(true);
	    	
	    }
	    
	    @Test
	    public void givenGameSavedSession_whenCloseGame_thenResultOk() {
	    	CloseGameDto closeGameDto = new CloseGameDto("UserTest", "userTestGame",false);
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.OK);
	    	result.setUsername(closeGameDto.getUsername());
	    	assertEquals(this.closeGameController.closeGame(closeGameDto).toString(), result.toString());
	    }
	    @Test
	    public void givenGameSavedSessionAndNotCloseWithoutSave_whenCloseGame_thenResultOk() {
	    	CloseGameDto closeGameDto = new CloseGameDto("UserTest", "userTestGame",true);
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.OK);
	    	result.setUsername(closeGameDto.getUsername());
	    	assertEquals(this.closeGameController.closeGame(closeGameDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenGameSavedSessionWithNonExistPlayer_whenCloseGame_thenResultNotFound() {
	    	CloseGameDto closeGameDto = new CloseGameDto("UserNonExisTest", "userTestGame",false);
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.NOT_FOUND);
	    	result.setUsername(closeGameDto.getUsername());
	    	assertEquals(this.closeGameController.closeGame(closeGameDto).toString(), result.toString());
	    }
	    
	    


}
