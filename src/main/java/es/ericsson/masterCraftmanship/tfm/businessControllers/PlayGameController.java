package es.ericsson.masterCraftmanship.tfm.businessControllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.dtos.MoveDto;
import es.ericsson.masterCraftmanship.tfm.models.Color;
import es.ericsson.masterCraftmanship.tfm.models.Coordinate;
import es.ericsson.masterCraftmanship.tfm.models.Error;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Piece;
import es.ericsson.masterCraftmanship.tfm.services.GameDaoService;
import es.ericsson.masterCraftmanship.tfm.services.SessionDaoService;
import es.ericsson.masterCraftmanship.tfm.views.GameListJson;
import es.ericsson.masterCraftmanship.tfm.views.ResponseJson;
import es.ericsson.masterCraftmanship.tfm.views.SquareJson;

@Controller
public class PlayGameController {

	private SessionDaoService sessionDaoService;
	private GameDaoService gameDaoService;

	Logger logger = LogManager.getLogger(PlayGameController.class);

	@Autowired
	public PlayGameController(SessionDaoService sessionDaoService, GameDaoService gameDaoService) {
		this.sessionDaoService = sessionDaoService;
		this.gameDaoService = gameDaoService;
	}

	public List<SquareJson> getStatus(String username) {
		Game game = sessionDaoService.getSessionGame(username);
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

	public ResponseJson move(String playerName, MoveDto movement) {
		ResponseJson moveResult = new ResponseJson();
		Game game = sessionDaoService.getSessionGame(playerName);
		Coordinate coordOrigin = new Coordinate(Integer.parseInt(movement.getOriginRow()),
				Integer.parseInt(movement.getOriginCol()));
		Coordinate coordTarget = new Coordinate(Integer.parseInt(movement.getTargetRow()),
				Integer.parseInt(movement.getTargetCol()));
		Coordinate[] coordinates = { coordOrigin, coordTarget };
		Error error = game.move(coordinates);
		moveResult.setUsername(playerName);
		if (error != null) {
			moveResult.setError(error);
		} else {
			if (error == null && game.isBlocked()) {
				if (game.getTurnColor() == Color.WHITE) {
					moveResult.setError(Error.LOST_MESSAGE);

				} else {
					moveResult.setError(Error.LOST_MESSAGE_MACHINE);
					moveResult.setUsername("machine");
				}
			}
			sessionDaoService.saveSessionGame(playerName,game);
		}

		return moveResult;
	}
	
	public GameListJson getGames(String username) {
		GameListJson result = new GameListJson();
		result.setUsername(username);
		result.setListGame(gameDaoService.getGamesByPlayer(username));
		return result;
	}
}
