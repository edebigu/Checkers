package es.ericsson.masterCraftmanship.tfm.businessControllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import es.ericsson.masterCraftmanship.tfm.daos.GameDao;
import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.models.Game;
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

}
