package es.ericsson.masterCraftmanship.tfm.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.services.PlayerDaoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlayerDaoService.class)
public class PlayerDaoServiceTest {
	
	   @Autowired
	   private PlayerDaoService playerDaoService;
	   
	    @MockBean
	    private PlayerDao playerDao;
	    
	    @Before
	    public void setup() {
	    	Player player = new Player ("UserTest", "userTest");
	    	Mockito.when(playerDao.findByUsername("UserTest")).thenReturn(player);
	    	Mockito.when(playerDao.save(player)).thenReturn(player);
	    }
	    
	    
	    @Test
	    public void givenExistPlayer_whenFindPlayer_thenResult() {
	    	Player player = new Player ("UserTest", "userTest");
	    	assertEquals(this.playerDaoService.findPlayer("UserTest", "userTest").toString(), player.toString());
	    }
	    
	    @Test
	    public void givenNonExistPlayer_whenFindPlayer_thenNotResult() {
	    	assertNull(this.playerDaoService.findPlayer("UserTestNonExist", "userTest"));
	    }
	    
	    @Test
	    public void givenExistPlayerWithWrongPassword_whenFindPlayer_thenNotResult() {
	    	assertNull(this.playerDaoService.findPlayer("UserTest", "passwordWrong"));
	    }
	    
	    @Test
	    public void givenExistPlayer_whenFindPlayerByUsername_thenResult() {
	    	Player player = new Player ("UserTest", "userTest");
	    	assertEquals(this.playerDaoService.findPlayerByUsername("UserTest").toString(), player.toString());
	    }
	    
	    @Test
	    public void givenNonExistPlayer_whenFindPlayerByUsername_thenNotResult() {
	    	assertNull(this.playerDaoService.findPlayerByUsername("UserTestNonExist"));
	    }
	    
	    @Test
	    public void givenNonExistPlayer_whenSavePlayer_thenResult() {
	    	Player player = new Player ("UserNonExitTest", "password");
	    	Mockito.when(playerDao.save(player)).thenReturn(player);
	    	assertEquals(this.playerDaoService.savePlayer("UserNonExitTest", "password").toString(), player.toString());
	    }
	    
	    @Test
	    public void givenExistPlayer_whenSavePlayer_thenNotResult() {
	    	assertNull(this.playerDaoService.savePlayer("UserTest", "userTest"));
	    }

	    @Test
	    public void givenExisPlyaer_whenDelete_thenResul() {
	    	Player player = new Player ("UserNonExitTest", "password");
	    	this.playerDaoService.deletePlayer(player);
	    	verify(this.playerDao, times(1)).delete(player);;
	    	
	    }
	    
	    

}
