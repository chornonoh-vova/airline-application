package com.airline.api.repositories;

import com.airline.api.model.Airplanes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirplanesRepository extends JpaRepository<Airplanes, Integer> {
}
