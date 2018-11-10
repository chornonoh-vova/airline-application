package com.airline.api.search;

import com.airline.api.model.Routes;

import java.util.List;
import java.util.stream.Collectors;

public class ToCriteria implements RouteCriteria {
  private String to;

  public ToCriteria(String to) {
    this.to = to;
  }

  @Override
  public List<Routes> meetCriteria(List<Routes> routes) {
    if (to == null) {
      return routes;
    } else {
      return routes
          .stream()
          .filter(r -> r.getDestinationAirport().equals(to))
          .collect(Collectors.toList());
    }
  }
}
