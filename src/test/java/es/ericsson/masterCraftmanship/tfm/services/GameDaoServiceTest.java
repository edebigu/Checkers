package es.ericsson.masterCraftmanship.tfm.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.models.Color;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.GameTest;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GameDaoService.class)
public class GameDaoServiceTest extends GameTest{
	
	   @Autowired
	   private GameDaoService gameDaoService;
	   
	    @MockBean
	    private GameDao gameDao;
	    
	    private Game game;
	    private List<Game> listGames;
	    
	    @Before
	    public void setup() {
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
	        listGames = new ArrayList<Game>();
	    	listGames.add(game);
	        Game game2 = new Game(game);
	    	game2.setName("userTestGame2");
	    	listGames.add(game2);
	    	Game game3 = new Game(game);
	    	game3.setName("userTestGame3");
	    	listGames.add(game3);

	    	Mockito.when(gameDao.findByPlayer_username("UserTest")).thenReturn(listGames);
	    	Mockito.when(gameDao.save(game)).thenReturn(game);
	    	
	    	
	    }
	    
	    @Test
	    public void givenGameAndPlayer_whenGetGameByPlayer_thenReturnGame() {
	    	assertEquals(this.gameDaoService.getGameByPlayer("UserTest", "userTestGame").toString(), game.toString());
	    }
	    
	    @Test
	    public void giveNonExistGameAndPlayer_whenGetGameByPlayer_thenReturnGame() {
	    	assertNull(this.gameDaoService.getGameByPlayer("UserTest", "GameNonExist"));
	    }
	    
	    @Test
	    public void givenNonSaveGame_whenSaveGameWithName_resultGame() {
	    	Game gameNonExist = new Game();
	    	gameNonExist.setName("GameName");
	        this.setGameWithName(Color.WHITE, "GameName",
	            " n n n n",
	            "n n n n ",
	            "   n n n",
	            "n       ",
	            " b      ",
	            "  b b b ",
	            " b b b b",
	            "b b b b ");
	        Mockito.when(gameDao.save(gameNonExist)).thenReturn(gameNonExist);
	        assertNull(this.gameDaoService.getGameByPlayer("UserTest", "GameNonExist"));
	    	assertEquals(this.gameDaoService.saveGame(gameNonExist, "GameName", "UserTest", false).toString(), gameNonExist.toString());
	    }
	    
	    @Test
	    public void givenNonSaveGame_whenSaveGameWithoutName_thenResulNull() {
	    	Game gameNonExist = new Game();
	        this.setGame(Color.WHITE,
	            " n n n n",
	            "n n n n ",
	            "   n n n",
	            "n       ",
	            " b      ",
	            "  b b b ",
	            " b b b b",
	            "b b b b ");
	        Mockito.when(gameDao.save(gameNonExist)).thenReturn(gameNonExist);
	        assertNull(this.gameDaoService.getGameByPlayer("UserTest", ""));
	        assertNull(this.gameDaoService.saveGame(gameNonExist, "", "UserTest", false));
	    }
	    
	    @Test
	    public void givenSaveGame_whenSaveGameWithOverwrite_resultGame() {
	        assertEquals(this.gameDaoService.saveGame(game, "userTestGame", "UserTest", true).toString(), game.toString());
	    }
	    
	    @Test
	    public void giveUnsaveGame_whenSaveGameWithOverwirte_thenNull() {
	    	Game gameNonExist = new Game();
	    	gameNonExist.setName("GameName");
	        this.setGameWithName(Color.WHITE, "GameName",
	            " n n n n",
	            "n n n n ",
	            "   n n n",
	            "n       ",
	            " b      ",
	            "  b b b ",
	            " b b b b",
	            "b b b b ");
	    	 assertNull(this.gameDaoService.saveGame(gameNonExist, "GameName", "UserTest", true));
	    }
	    
	    @Test
	    public void givePlayerWithSavedGames_whenGetGamesByPlayer_thenResulListGames() {
	    	assertEquals(this.gameDaoService.getGamesByPlayer("UserTest").toString(), listGames.toString());
	    }
	    
	    @Test
	    public void givePlayerWithoutSavedGames_whenGetGamesByPlayer_thenResulNull() {
	    	Mockito.when(gameDao.findByPlayer_username("UserTest2")).thenReturn(null);
	    	assertNull(this.gameDaoService.getGamesByPlayer("UserTest2"));
	    }
	    
	    
	    

}
