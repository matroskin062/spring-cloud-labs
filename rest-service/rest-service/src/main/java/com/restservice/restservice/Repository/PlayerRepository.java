package com.restservice.restservice.Repository;

import com.restservice.restservice.Model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface PlayerRepository extends CrudRepository<Player, Integer>, Repo {
}
