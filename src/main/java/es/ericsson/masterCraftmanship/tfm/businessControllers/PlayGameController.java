package es.ericsson.masterCraftmanship.tfm.businessControllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.dtos.MoveDto;
import es.ericsson.masterCraftmanship.tfm.models.Coordinate;
import es.ericsson.masterCraftmanship.tfm.models.Error;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Piece;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.views.MoveJson;
import es.ericsson.masterCraftmanship.tfm.views.SquareJson;
import es.ericsson.masterCraftmanship.tfm.views.TurnJson;

@Controller
public class PlayGameController {
	
	private PlayerDao playerDao;
	private GameDao gameDao;
	
	Logger logger = LogManager.getLogger(PlayGameController.class);
	
	@Autowired
	public PlayGameController (GameDao gameDao, PlayerDao playerDao) {
		this.gameDao = gameDao;
		this.playerDao = playerDao;
	}
	
	public TurnJson getTurn (String gameId) {
		TurnJson resultTurn = new TurnJson();
		Game game = gameDao.findById(gameId).get();
		resultTurn.setColor(game.getTurn().getColor().name());
		return resultTurn;
		
	}
	
	public List<SquareJson> getStatus (String gameId) {
		Game game = gameDao.findById(gameId).get();
        List<SquareJson> listSquare = new ArrayList<SquareJson>();
		Piece[][] pieces= game.getBoard().getPieces();
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
	
	public MoveJson move (String gameId, String playerName, MoveDto movement) {
		MoveJson moveResult = new MoveJson();
		Game game = gameDao.findById(gameId).get();
		Player player = playerDao.findByUsername(playerName);
		Coordinate coordOrigin = new Coordinate (Integer.parseInt(movement.getOriginRow()), Integer.parseInt(movement.getOriginCol()));
		Coordinate coordTarget = new Coordinate (Integer.parseInt(movement.getTargetRow()), Integer.parseInt(movement.getTargetCol()));
		Coordinate[] coordinates = {coordOrigin, coordTarget};
		Error error = game.move(coordinates);
		moveResult.setUsername(player.getUsername());
		if (error != null) {
			moveResult.setError(error.toString());
		}
		return moveResult;
	}
}
