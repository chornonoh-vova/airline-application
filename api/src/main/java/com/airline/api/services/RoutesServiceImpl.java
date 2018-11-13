package com.airline.api.services;

import com.airline.api.model.Routes;
import com.airline.api.repositories.RoutesRepository;
import com.airline.api.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RoutesServiceImpl implements RoutesService {
  private final RoutesRepository routesRepository;

  @Autowired
  public RoutesServiceImpl(RoutesRepository routesRepository) {
    this.routesRepository = routesRepository;
  }

  @Override
  public List<Routes> getAllRoutes() {
    return routesRepository.findAll();
  }

  @Override
  public Optional<Routes> getRouteById(int routeId) {
    return routesRepository.findById(routeId);
  }

  @Override
  public List<Routes> search(String from, String to, String sort, String order) {
    Criteria<Routes> fromCriteria = new RouteFromCriteria(from);
    Criteria<Routes> toCriteria = new RouteToCriteria(to);
    Criteria<Routes> andCriteria = new AndCriteria<>(fromCriteria, toCriteria);
    List<Routes> res = andCriteria.meetCriteria(routesRepository.findAll());
    if (sort.equals("price") || sort.equals("duration")) {
      Comparator<Routes> comp = null;
      switch (sort) {
        case "price":
          switch (order) {
            case "asc":
              comp = RouteComparators.priceAsc;
              break;
            default:
              comp = RouteComparators.priceDesc;
              break;
          }
          break;
        case "duration":
          switch (order) {
            case "asc":
              comp = RouteComparators.durationAsc;
              break;
            default:
              comp = RouteComparators.durationDesc;
              break;
          }
      }
      res.sort(comp);
    }
    return res;
  }

  @Override
  public Routes add(Routes body) {
    return routesRepository.save(body);
  }

  @Override
  public Routes edit(int routeId, Routes body) {
    Routes elem = routesRepository.getOne(routeId);
    if (body.getDepartureAirport() != null) {
      elem.setDepartureAirport(body.getDepartureAirport());
    }
    if (body.getDestinationAirport() != null) {
      elem.setDestinationAirport(body.getDestinationAirport());
    }
    if (body.getDuration() != null) {
      elem.setDuration(body.getDuration());
    }
    if (body.getPrice() != 0.0) {
      elem.setPrice(body.getPrice());
    }
    return routesRepository.save(elem);
  }
}
