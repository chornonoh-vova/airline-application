package com.airline.api.services;

import com.airline.api.model.Flights;
import com.airline.api.repositories.FlightsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightsServiceImpl implements FlightsService {
  private final FlightsRepository flightsRepository;

  @Autowired
  public FlightsServiceImpl(FlightsRepository flightsRepository) {
    this.flightsRepository = flightsRepository;
  }

  @Override
  public List<Flights> getAllFlights() {
    return flightsRepository.findAll();
  }

  @Override
  public Optional<Flights> getFlightById(int flightId) {
    return flightsRepository.findById(flightId);
  }

  @Override
  public List<Flights> search(String from, String to, String sort, String order) {
    return null;
  }

  @Override
  public Flights add(Flights body) {
    return flightsRepository.save(body);
  }

  @Override
  public Flights edit(int flightId, Flights body) {
    return null;
  }
}
