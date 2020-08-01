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

import es.ericsson.masterCraftmanship.tfm.dtos.SaveGameDto;
import es.ericsson.masterCraftmanship.tfm.models.Color;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.GameTest;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SaveGameController.class)
public class SaveGameControllerTest extends GameTest{
	
	   @Autowired
	   private SaveGameController saveGameController;
	     
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
	        Mockito.when(sessionDaoService.isSavedGameSession("UserTest")).thenReturn(true);
	        Mockito.when(sessionDaoService.getSessionGame("UserTest")).thenReturn(game);
	        Mockito.when(gameDaoService.saveGame(game, "userTestGame", "UserTest", true)).thenReturn(game);
	        Mockito.when(sessionDaoService.saveSessionGame("UserTest", game)).thenReturn(true);
	        
	        Mockito.when(sessionDaoService.isSavedGameSession("UserTest2")).thenReturn(false);
	        Mockito.when(sessionDaoService.getSessionGame("UserTest2")).thenReturn(game);
	        Mockito.when(gameDaoService.saveGame(game, "userTestGame2", "UserTest2", false)).thenReturn(game);
	        Mockito.when(sessionDaoService.saveSessionGame("UserTest2", game)).thenReturn(true);
	        
	        Mockito.when(gameDaoService.saveGame(game, "userTestGame2", "", false)).thenReturn(null);
	        Mockito.when(gameDaoService.saveGame(game, "userTestGame", "UserTest2", false)).thenReturn(null);
	        Mockito.when(gameDaoService.saveGame(game, "userTestGame", "UserTest2", true)).thenReturn(null);

	    }  
	    
	    
	    @Test
	    public void givenSavedGame_whenSaveGame_thenResultOk() {
	    	SaveGameDto saveGameDto = new SaveGameDto("UserTest", "userTestGame", false);
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.OK);
	    	result.setUsername(saveGameDto.getUsername());
	    	assertEquals(this.saveGameController.save(saveGameDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenUnsavedGameAndNonExistGameNameToSave_whenSaveGame_thenResultOk() {
	    	SaveGameDto saveGameDto = new SaveGameDto("UserTest2", "userTestGame2", false);
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.OK);
	    	result.setUsername(saveGameDto.getUsername());
	    	assertEquals(this.saveGameController.save(saveGameDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenUnsavedGameAndExistGameNameToSave_whenSaveGame_thenResultConflict() {
	    	SaveGameDto saveGameDto = new SaveGameDto("UserTest2", "userTestGame", false);
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.CONFLICT);
	    	result.setUsername(saveGameDto.getUsername());
	    	assertEquals(this.saveGameController.save(saveGameDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenUnsavedGameAndExistGameNameToSaveAndOverwrite_whenSaveGame_thenResultConflict() {
	    	SaveGameDto saveGameDto = new SaveGameDto("UserTest2", "userTestGame", true);
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.CONFLICT);
	    	result.setUsername(saveGameDto.getUsername());
	    	assertEquals(this.saveGameController.save(saveGameDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenUnsavedGameAndEmptyGameName_whenSaveGame_thenResultNotFound() {
	    	SaveGameDto saveGameDto = new SaveGameDto("UserTest2", "", false);
	    	ResponseJson result = new ResponseJson();
	    	result.setResult(Result.NOT_FOUND);
	    	result.setUsername(saveGameDto.getUsername());
	    	assertEquals(this.saveGameController.save(saveGameDto).toString(), result.toString());
	    }
	    
	    
	
	

}
