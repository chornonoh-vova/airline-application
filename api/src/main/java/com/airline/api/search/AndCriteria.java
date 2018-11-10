package com.airline.api.search;

import com.airline.api.model.Routes;

import java.util.List;

public class AndCriteria implements RouteCriteria {
  private RouteCriteria criteria1;
  private RouteCriteria criteria2;

  public AndCriteria(RouteCriteria criteria1, RouteCriteria criteria2) {
    this.criteria1 = criteria1;
    this.criteria2 = criteria2;
  }

  @Override
  public List<Routes> meetCriteria(List<Routes> routes) {
    return criteria2.meetCriteria(criteria1.meetCriteria(routes));
  }
}
