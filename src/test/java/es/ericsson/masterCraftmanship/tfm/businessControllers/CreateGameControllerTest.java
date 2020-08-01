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
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.services.PlayerDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.CreateGameJson;
import es.ericsson.masterCraftmanship.tfm.views.Result;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CreateGameController.class)
public class CreateGameControllerTest {
	
	   @Autowired
	   private CreateGameController createGameController;
	   
	    @MockBean
	    private PlayerDaoService playerDaoService;
	    
	    @MockBean
		private SessionDaoService sessionDaoService;
	    
	    @Before
	    public void setup() {
	    	Player player = new Player("UserTest", "userTest");
	    	Mockito.when(playerDaoService.findPlayerByUsername("UserTest")).thenReturn(player);
	    	Mockito.when(sessionDaoService.createGameSession(player)).thenReturn(true);
	    	Mockito.when(playerDaoService.findPlayerByUsername("UserNonExistTest")).thenReturn(null);
	    	Mockito.when(sessionDaoService.createGameSession(new Player("UserNonExistTest", "userNonExistTest"))).thenReturn(false);
	    	Mockito.when(playerDaoService.findPlayerByUsername("UserExistTest")).thenReturn(new Player("UserExistTest", "userExistTest"));
	    	Mockito.when(sessionDaoService.createGameSession(new Player("UserExistTest", "userExistTest"))).thenReturn(false);
	    }
	    
	    @Test
	    public void givenExistPlayerAndSession_whenCreate_thenResultOk() {
	    	SessionDto sessionDto = new SessionDto("UserTest", null);
	    	CreateGameJson result = new CreateGameJson();
	    	result.setResult(Result.OK);
	    	result.setUsername(sessionDto.getUsername());
	    	result.setGameName(null);
	    	assertEquals(this.createGameController.createGame(sessionDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenNonExistPlayer_whenCreateGame_thenResultNotFound() {
	    	SessionDto sessionDto = new SessionDto("UserNonExistTest", null);
	    	CreateGameJson result = new CreateGameJson();
	    	result.setResult(Result.NOT_FOUND);
	    	result.setUsername(sessionDto.getUsername());
	    	assertEquals(this.createGameController.createGame(sessionDto).toString(), result.toString());
	    }
	    
	    @Test
	    public void givenExistPlayerAndNotExistSession_whenCreateGame_thenResultNotFound() {
	    	SessionDto sessionDto = new SessionDto("UserExistTest", null);
	    	CreateGameJson result = new CreateGameJson();
	    	result.setResult(Result.NOT_FOUND);
	    	result.setUsername(sessionDto.getUsername());
	    	assertEquals(this.createGameController.createGame(sessionDto).toString(), result.toString());
	    }

}
