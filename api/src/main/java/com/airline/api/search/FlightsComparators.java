package com.airline.api.search;

import com.airline.api.model.Flights;

import java.util.Comparator;

public class FlightsComparators {
  public static final Comparator<Flights> departureAsc =
      Comparator.comparing(Flights::getDepartureTime);
  public static final Comparator<Flights> departureDesc = departureAsc.reversed();
  public static final Comparator<Flights> arrivalAsc =
      Comparator.comparing(Flights::getArrivalTime);
  public static final Comparator<Flights> arrivalDesc = arrivalAsc.reversed();
}
