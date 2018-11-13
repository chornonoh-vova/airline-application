package com.airline.api.search;

import com.airline.api.model.Routes;

import java.util.List;
import java.util.stream.Collectors;

public class RouteFromCriteria implements Criteria<Routes> {
  private String from;

  public RouteFromCriteria(String from) {
    this.from = from;
  }

  @Override
  public List<Routes> meetCriteria(List<Routes> routes) {
    if (from != null) {
      return routes
          .stream()
          .filter(r -> r.getDepartureAirport().equals(from))
          .collect(Collectors.toList());
    } else {
      return routes;
    }
  }
}
