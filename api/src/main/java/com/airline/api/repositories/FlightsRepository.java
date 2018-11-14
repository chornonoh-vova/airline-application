package com.airline.api.repositories;

import com.airline.api.model.Flights;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightsRepository extends JpaRepository<Flights, Integer> {
}
