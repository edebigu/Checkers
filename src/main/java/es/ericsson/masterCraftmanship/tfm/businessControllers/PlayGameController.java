package es.ericsson.masterCraftmanship.tfm.businessControllers;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.daos.SessionDao;
import es.ericsson.masterCraftmanship.tfm.dtos.MoveDto;
import es.ericsson.masterCraftmanship.tfm.dtos.SessionDto;
import es.ericsson.masterCraftmanship.tfm.models.Color;
import es.ericsson.masterCraftmanship.tfm.models.Coordinate;
import es.ericsson.masterCraftmanship.tfm.models.Error;
import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Piece;
import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.models.Session;
import es.ericsson.masterCraftmanship.tfm.views.GameListJson;
import es.ericsson.masterCraftmanship.tfm.views.MoveJson;
import es.ericsson.masterCraftmanship.tfm.views.SquareJson;
import es.ericsson.masterCraftmanship.tfm.views.TurnJson;

@Controller
public class PlayGameController {

	private PlayerDao playerDao;
	private GameDao gameDao;
	private SessionDao sessionDao;

	Logger logger = LogManager.getLogger(PlayGameController.class);

	@Autowired
	public PlayGameController(GameDao gameDao, PlayerDao playerDao, SessionDao sessionDao) {
		this.gameDao = gameDao;
		this.playerDao = playerDao;
		this.sessionDao = sessionDao;
	}

	public TurnJson getTurn(String gameName) {
		TurnJson resultTurn = new TurnJson();
		Game game = sessionDao.findByGame_gameName(gameName).getGame();
		resultTurn.setColor(game.getTurnColor().name());
		return resultTurn;

	}

	public List<SquareJson> getStatus(String gameName) {
		Game game = sessionDao.findByGame_gameName(gameName).getGame();
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

	public MoveJson move(String gameId, String playerName, MoveDto movement) {
		MoveJson moveResult = new MoveJson();
		Session sessionFound = sessionDao.findByPlayer_username(playerName);
		Game game = sessionFound.getGame();
		Coordinate coordOrigin = new Coordinate(Integer.parseInt(movement.getOriginRow()),
				Integer.parseInt(movement.getOriginCol()));
		Coordinate coordTarget = new Coordinate(Integer.parseInt(movement.getTargetRow()),
				Integer.parseInt(movement.getTargetCol()));
		Coordinate[] coordinates = { coordOrigin, coordTarget };
		Error error = game.move(coordinates);
		moveResult.setUsername(playerName);
		if (error != null) {
			moveResult.setError(error.toString());
		} else {
			if (error == null && game.isBlocked()) {
				if (game.getTurnColor() == Color.WHITE) {
					moveResult.setError(Error.LOST_MESSAGE.toString());

				} else {
					moveResult.setError(Error.LOST_MESSAGE_MACHINE.toString());
					moveResult.setUsername("machine");
				}
			}
			gameDao.save(game);
			sessionFound.setGame(game);
			sessionDao.save(sessionFound);
		}

		return moveResult;
	}
	
	public GameListJson getGames(String playerName) {
		GameListJson result = new GameListJson();
		Player player = playerDao.findByUsername(playerName);
		result.setUsername(player.getUsername());
		List<Game> listGame = gameDao.findByPlayer(player);
		for (int i = 0; i<listGame.size(); i++) {
			if (!listGame.get(i).getName().equals("unsavedGame")) {
				result.setListGame(listGame.get(i).getName());
			}
		}
		return result;
	}
}
