package com.airline.api.repositories;

import com.airline.api.model.Pilots;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PilotsRepository extends JpaRepository<Pilots, Integer> {
}
