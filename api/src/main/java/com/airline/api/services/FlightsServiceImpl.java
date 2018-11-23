package com.airline.api.services;

import com.airline.api.model.Flights;
import com.airline.api.repositories.FlightsRepository;
import com.airline.api.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
    Criteria<Flights> fromCriteria = new FlightFromCriteria(from);
    Criteria<Flights> toCriteria = new FlightToCriteria(to);
    Criteria<Flights> and = new AndCriteria<>(fromCriteria, toCriteria);
    List<Flights> res = and.meetCriteria(flightsRepository.findAll());
    if (sort.equals("to") || sort.equals("from")) {
      Comparator<Flights> comp = null;
      switch (sort) {
        case "to":
          switch (order) {
            case "asc":
              comp = FlightsComparators.departureAsc;
              break;
            default:
              comp = FlightsComparators.departureDesc;
              break;
          }
          break;
        case "from":
          switch (order) {
            case "asc":
              comp = FlightsComparators.arrivalAsc;
              break;
            default:
              comp = FlightsComparators.arrivalDesc;
              break;
          }
      }
      res.sort(comp);
    }
    return res;
  }

  @Override
  public Flights add(Flights body) {
    return flightsRepository.save(body);
  }

  @Override
  public Flights edit(int flightId, Flights body) {
    Flights elem = flightsRepository.getOne(flightId);
    if (body.getDepartureTime() != null) {
      elem.setDepartureTime(body.getDepartureTime());
    }
    if (body.getArrivalTime() != null) {
      elem.setArrivalTime(body.getArrivalTime());
    }
    return flightsRepository.save(elem);
  }
}
