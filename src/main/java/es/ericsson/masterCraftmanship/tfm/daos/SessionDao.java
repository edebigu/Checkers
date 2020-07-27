package es.ericsson.masterCraftmanship.tfm.daos;



import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.ericsson.masterCraftmanship.tfm.models.Player;
import es.ericsson.masterCraftmanship.tfm.models.Session;

@Repository
public interface SessionDao extends MongoRepository<Session, String> {
	public Session findByPlayer(Player player);
	public Session findByPlayer_username(String username);
	public Session findByGame_gameName(String gameName);

}
