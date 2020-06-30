package es.ericsson.masterCraftmanship.tfm.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ericsson.masterCraftmanship.tfm.dtos.UserDto;

@Repository
public interface UserDao  extends JpaRepository<UserDto, Long> {
	public UserDto findByUsername(String username);

}
