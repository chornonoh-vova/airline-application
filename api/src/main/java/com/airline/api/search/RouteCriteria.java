package com.airline.api.search;

import com.airline.api.model.Routes;

import java.util.List;

public interface RouteCriteria {
  List<Routes> meetCriteria(List<Routes> routes);
}
