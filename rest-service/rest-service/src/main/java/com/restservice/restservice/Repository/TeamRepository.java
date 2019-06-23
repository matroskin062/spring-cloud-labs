package com.restservice.restservice.Repository;

import com.restservice.restservice.Model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Integer>, Repo {
}
