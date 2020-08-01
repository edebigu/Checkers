package es.ericsson.masterCraftmanship.tfm.businessControllers;

import static org.junit.Assert.assertEquals;

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

import es.ericsson.masterCraftmanship.tfm.dtos.MoveDto;
import es.ericsson.masterCraftmanship.tfm.models.Color;
import es.ericsson.masterCraftmanship.tfm.models.Coordinate;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.GameTest;
import es.ericsson.masterCraftmanship.tfm.models.Piece;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.GameListJson;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.SquareJson;
import es.ericsson.masterCraftmanship.tfm.models.Error;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PlayGameController.class)
public class PlayGameControllerTest extends GameTest{
	
	
	   @Autowired
	   private PlayGameController playGameController;
	     
	    @MockBean
		private SessionDaoService sessionDaoService;
	    @MockBean
		private GameDaoService gameDaoService;
	    
	    private List<Game> listGames;
	    private Game game;
	    
	    
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

	    	Mockito.when(gameDaoService.getGamesByPlayer("UserTest")).thenReturn(listGames);
	    	Mockito.when(gameDaoService.getGamesByPlayer("UserNonExitTest")).thenReturn(new ArrayList<Game>());
	    	Mockito.when(sessionDaoService.getSessionGame("UserTest")).thenReturn(game);
	    	Mockito.when(sessionDaoService.saveSessionGame("UserTest", game)).thenReturn(true);

	    }
	    
	    @Test
	    public void givenPlayer_whenGetGames_thenResultGames() {
	    	GameListJson result = new GameListJson();
	    	result.setListGame(this.listGames);
	    	result.setUsername("UserTest");
	    	assertEquals(this.playGameController.getGames("UserTest").toString(), result.toString());
	    }
	    
	    @Test
	    public void givenPlayerAndNotExistGames_whenGetGames_thenResultGames() {
	    	GameListJson result = new GameListJson();
	    	result.setListGame(new ArrayList<Game>());
	    	result.setUsername("UserNonExitTest");
	    	assertEquals(this.playGameController.getGames("UserNonExitTest").toString(),result.toString() );
	    }
	    
	    @Test
	    public void givenPlayer_whenGetStatus_thenResultOK() {
	    	List<SquareJson> listSquare = this.getListSquares();
	    	assertEquals(this.playGameController.getStatus("UserTest").toString(), listSquare.toString());
	    	
	    }
	    
	    @Test
	    public void givenPlayerAndCorrectMovement_whenMove_thenResult() {
	    	MoveDto moveDto = new MoveDto("5", "6", "4", "7");
	    	ResponseJson result = new ResponseJson();
	    	result.setUsername("UserTest");
	    	assertEquals(this.playGameController.move("UserTest", moveDto).toString(),result.toString());
	    }
	    
	    @Test
	    public void givenPlayerAndNotCorrectMovement_whenMove_thenNotResult() {
	    	MoveDto moveDto = new MoveDto("5", "6", "3", "7");
	    	ResponseJson result = new ResponseJson();
	    	result.setUsername("UserTest");
	    	result.setError(Error.NOT_DIAGONAL);
	    	assertEquals(this.playGameController.move("UserTest", moveDto).toString(),result.toString());
	    }
	    
	    
	    private List<SquareJson> getListSquares(){
	    	List<SquareJson> listSquare = new ArrayList<SquareJson>();
	    	Piece[][] pieces = game.getBoard().getPieces();
			for (int i = 0; i < Coordinate.getDimension(); i++) {
				for (int j = 0; j < Coordinate.getDimension(); j++) {
					SquareJson square = new SquareJson();
					square.setCoordX(i);
					square.setCoordY(j);
					if (pieces[i][j] != null) {
						square.setPiece(pieces[i][j].toString());
						square.setColor(pieces[i][j].getColor().toString());
					}
					listSquare.add(square);
				}
			}
			return listSquare;
	    	
	    }
	    
	    
	    

}
