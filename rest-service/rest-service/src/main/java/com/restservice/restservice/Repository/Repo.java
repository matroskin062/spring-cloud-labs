package com.restservice.restservice.Repository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface Repo<T> {
    Optional<T> findByIdAndPresenceFlagTrue(Integer id);
    List<T> findByPresenceFlagTrue();
}
