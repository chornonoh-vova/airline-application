package com.airline.api.repositories;

import com.airline.api.model.Sessions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionsRepository extends JpaRepository<Sessions, Integer> {
}
