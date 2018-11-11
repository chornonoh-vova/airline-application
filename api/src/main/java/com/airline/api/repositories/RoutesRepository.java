package com.airline.api.repositories;

import com.airline.api.model.Routes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutesRepository extends JpaRepository<Routes, Integer> {
}
