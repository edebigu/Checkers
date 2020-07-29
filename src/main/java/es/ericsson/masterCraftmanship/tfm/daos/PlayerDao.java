package es.ericsson.masterCraftmanship.tfm.daos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.ericsson.masterCraftmanship.tfm.models.Player;

@Repository
public interface PlayerDao  extends MongoRepository<Player, String> {
	public Player findByUsername(String username);
}
