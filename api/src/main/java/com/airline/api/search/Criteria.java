package com.airline.api.search;

import java.util.List;

public interface Criteria<T> {
  List<T> meetCriteria(List<T> routes);
}
