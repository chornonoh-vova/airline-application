package com.airline.api.repositories;

import com.airline.api.model.Flights;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightsRepository extends JpaRepository<Flights, Integer> {
	List<Flights> getAllByRouteId(int routeId);
}
