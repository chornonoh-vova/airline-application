package com.airline.api.search;

import com.airline.api.model.Routes;

import java.util.List;

public class OrCriteria implements RouteCriteria {
  private RouteCriteria criteria1;
  private RouteCriteria criteria2;

  public OrCriteria(RouteCriteria criteria1, RouteCriteria criteria2) {
    this.criteria1 = criteria1;
    this.criteria2 = criteria2;
  }

  @Override
  public List<Routes> meetCriteria(List<Routes> routes) {
    List<Routes> meets = criteria1.meetCriteria(routes);
    List<Routes> meets1 = criteria2.meetCriteria(routes);

    for (Routes r : meets1) {
      if (!meets.contains(r)) {
        meets.add(r);
      }
    }

    return meets;
  }
}
