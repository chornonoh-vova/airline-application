package com.airline.api.services;

import com.airline.api.model.Routes;

import java.util.List;
import java.util.Optional;

public interface RoutesService {
  List<Routes> getAllRoutes();

  Optional<Routes> getRouteById(int routeId);

  List<Routes> search(String from, String to, String sort, String order);

  Routes add(Routes body);

  Routes edit(int routeId, Routes body);
}
