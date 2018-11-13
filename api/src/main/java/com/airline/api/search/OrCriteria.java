package com.airline.api.search;

import com.airline.api.model.Routes;

import java.util.List;

public class OrCriteria<T> implements Criteria<T> {
  private Criteria<T> criteria1;
  private Criteria<T> criteria2;

  public OrCriteria(Criteria<T> criteria1, Criteria<T> criteria2) {
    this.criteria1 = criteria1;
    this.criteria2 = criteria2;
  }

  @Override
  public List<T> meetCriteria(List<T> routes) {
    List<T> meets = criteria1.meetCriteria(routes);
    List<T> meets1 = criteria2.meetCriteria(routes);

    for (T r : meets1) {
      if (!meets.contains(r)) {
        meets.add(r);
      }
    }

    return meets;
  }
}
