package es.ericsson.masterCraftmanship.tfm.daos;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import es.ericsson.masterCraftmanship.tfm.models.Game;
import es.ericsson.masterCraftmanship.tfm.models.Player;

@Repository
public interface GameDao extends MongoRepository<Game, String>{
	
	public List<Game> findByPlayer(Player player);

}
