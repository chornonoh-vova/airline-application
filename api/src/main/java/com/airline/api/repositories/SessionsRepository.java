package com.airline.api.repositories;

import com.airline.api.model.Sessions;
import com.airline.api.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionsRepository extends JpaRepository<Sessions, Integer> {
  Sessions findBySessionKey(String sessionKey);
  Sessions findAllByUsers(Users user);
}
