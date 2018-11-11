package com.airline.api.repositories;

import com.airline.api.model.Passengers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengersRepository extends JpaRepository<Passengers, Integer> {}
