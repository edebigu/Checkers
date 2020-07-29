package es.ericsson.masterCraftmanship.tfm.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.ericsson.masterCraftmanship.tfm.daos.PlayerDao;
import es.ericsson.masterCraftmanship.tfm.models.Player;

@Service
public class PlayerDaoService {
	
	@Autowired
	private PlayerDao playerDao;
	
	public Player findPlayer(String username, String password) {
		Player player = findPlayerByUsername(username);
		if (player == null || !player.getPassword().equals(password)) {
			return null;
		}
		return player;
	}
	
	public Player findPlayerByUsername(String username) {
		return playerDao.findByUsername(username);

	}
	
	public Player savePlayer(String username, String password) {
		if (findPlayer(username,password) == null) {
			return playerDao.save(new Player(username, password));
		}
		return null;
		
	}
	
}
