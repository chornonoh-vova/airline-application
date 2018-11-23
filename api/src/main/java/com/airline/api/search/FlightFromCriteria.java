package com.airline.api.search;

import com.airline.api.model.Flights;

import java.util.List;
import java.util.stream.Collectors;

public class FlightFromCriteria implements Criteria<Flights> {
  private final String from;

  public FlightFromCriteria(String from) {
    this.from = from;
  }

  @Override
  public List<Flights> meetCriteria(List<Flights> flights) {
    if (from != null) {
      return flights
          .stream()
          .filter(f -> f.getDepartureTime().compareToIgnoreCase(from) >= 0)
          .collect(Collectors.toList());
    } else {
      return flights;
    }
  }
}
