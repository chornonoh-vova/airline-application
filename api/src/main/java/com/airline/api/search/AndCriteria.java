package com.airline.api.search;

import java.util.List;

public class AndCriteria<T> implements Criteria<T> {
  private Criteria<T> criteria1;
  private Criteria<T> criteria2;

  public AndCriteria(Criteria<T> criteria1, Criteria<T> criteria2) {
    this.criteria1 = criteria1;
    this.criteria2 = criteria2;
  }

  @Override
  public List<T> meetCriteria(List<T> routes) {
    return criteria2.meetCriteria(criteria1.meetCriteria(routes));
  }
}
