package es.ericsson.masterCraftmanship.tfm.daos;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import es.ericsson.masterCraftmanship.tfm.models.Game;

@Repository
public interface GameDao extends MongoRepository<Game, String>{

}
