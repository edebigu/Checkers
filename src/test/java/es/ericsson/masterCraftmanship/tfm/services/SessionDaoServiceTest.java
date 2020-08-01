package es.ericsson.masterCraftmanship.tfm.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;
import es.ericsson.masterCraftmanship.tfm.models.Color;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.GameTest;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.models.Session;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SessionDaoService.class)
public class SessionDaoServiceTest extends GameTest{
	
	   @Autowired
	   private SessionDaoService sessionDaoService;
	   
	    @MockBean
	    private SessionDao sessionDao;
	    
	    private Game game;
	    
	    @Before
	    public void setup() {
	    	Player player = new Player ("UserTest", "userTest");
	    	game = new Game();
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
	    	Session session = new Session(player, game);
	    	
	    	Mockito.when(sessionDao.findByPlayer_username("UserTest")).thenReturn(session);
	    }
	    
	    @Test
	    public void givenUsernameInSession_whenSaveSessionGame_thenResultTrue() {
	    	assertTrue(this.sessionDaoService.saveSessionGame("UserTest", game));
	    }
	    
	    @Test
	    public void givenUsernameWithoutSession_whenSaveSessionGame_thenResultFalse() {
	    	assertFalse(this.sessionDaoService.saveSessionGame("UserTest2", game));
	    }
	    
	    @Test
	    public void givenPlayerInSession_whenCreateGameSession_thenResultTrue() {
	    	assertTrue(this.sessionDaoService.createGameSession(new Player("UserTest", "userTest")));
	    }
	    
	    @Test
	    public void givenPlayerWithoutSession_whenCreateGameSession_thenResultTrue() {
	    	assertFalse(this.sessionDaoService.createGameSession(new Player("UserTest2", "userTest2")));
	    }
	    
	    @Test
	    public void givenUserNameInSession_whenGetSessionGame_returnGame() {
	    	assertEquals(this.sessionDaoService.getSessionGame("UserTest").toString(), game.toString());
	    }
	    
	    @Test
	    public void givenUserNameWithoutSession_whenGetSessionGame_returnNull() {
	    	assertNull(this.sessionDaoService.getSessionGame("UserTest2"));
	    }
	    
	    @Test
	    public void givenUsernameWithSessionAndGameName_whenIsSavedGameSession_returnTrue() {
	    	assertTrue(this.sessionDaoService.isSavedGameSession("UserTest"));
	    }
	    
	    @Test
	    public void givenUserNameWithoutSession_whenIsSavedGameSession_returnFalse() {
	    	assertFalse(this.sessionDaoService.isSavedGameSession("UserTest2"));
	    }
	    
	    @Test
	    public void givenUserNameWithSessionAndGameNameEmpty_whenIsSavedGameSession_returnFalse() {
	    	game.setName("");
	    	assertFalse(this.sessionDaoService.isSavedGameSession("UserTest"));
	    }
	    
	    @Test
	    public void givenUserNameWithSession_whenGetSession_returnSession() {
	    	Session session = new Session(new Player ("UserTest", "userTest"), game);
	    	assertEquals(this.sessionDaoService.getSession("UserTest").toString(),session.toString());
	    }
	    
	    @Test
	    public void givenUserNameWithoutSession_whenGetSession_returnNull() {
	    	assertNull(this.sessionDaoService.getSession("UserTest2"));
	    }
	    
	    @Test
	    public void giveUserNameWithSession_whenDeleteSession_returnTrue() {
	    	assertTrue(this.sessionDaoService.deleteSession("UserTest"));
	    }
	    
	    @Test
	    public void giveUserNameWithoutSession_whenDeleteSession_returnFalse() {
	    	assertFalse(this.sessionDaoService.deleteSession("UserTest2"));
	    }

}
