package es.ericsson.masterCraftmanship.tfm.daos;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ericsson.masterCraftmanship.tfm.models.Player;

@Service
public class PlayerDaoService {
	
	@Autowired
	private PlayerDao playerDao;
	
	public Player findPlayer(String username, String password) {
		Player player = playerDao.findByUsername(username);
		if (player == null || !player.getPassword().equals(password)) {
			LogManager.getLogger(PlayerDaoService.class).info("el player o password no existen");
			return null;
		}
		return player;
	}
	
	public Player savePlayer(String username, String password) {
		if (findPlayer(username,password) == null) {
			return playerDao.save(new Player(username, password));
		}
		return null;
		
	}
	
}
