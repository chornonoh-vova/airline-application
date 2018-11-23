package com.airline.api.search;

import com.airline.api.model.Flights;

import java.util.List;
import java.util.stream.Collectors;

public class FlightToCriteria implements Criteria<Flights> {
  private String to;

  public FlightToCriteria(String to) {
    this.to = to;
  }

  @Override
  public List<Flights> meetCriteria(List<Flights> flights) {
    if (to != null) {
      return flights
          .stream()
          .filter(f -> f.getArrivalTime().compareToIgnoreCase(to) <= 0)
          .collect(Collectors.toList());
    } else {
      return flights;
    }
  }
}
