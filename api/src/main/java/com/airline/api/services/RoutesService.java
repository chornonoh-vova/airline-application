package com.airline.api.services;

import com.airline.api.model.Routes;
import com.airline.api.model.Flights;

import java.util.List;
import java.util.Optional;

public interface RoutesService {
  List<Routes> getAllRoutes();

  List<Flights> getFlightsForRoute(int routeId);

  Optional<Routes> getRouteById(int routeId);

  List<Routes> search(String from, String to, String sort, String order);

  Routes add(Routes body);

  Routes edit(int routeId, Routes body);
}
