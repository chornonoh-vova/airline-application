package com.airline.api.services;

import com.airline.api.model.Flights;

import java.util.List;
import java.util.Optional;

public interface FlightsService {
  List<Flights> getAllFlights();

  Optional<Flights> getFlightById(int flightId);

  List<Flights> search(String from, String to, String sort, String order);

  Flights add(Flights body);

  Flights edit(int flightId, Flights body);
}
