package com.airline.api.search;

import com.airline.api.model.Routes;

import java.util.Comparator;

public class RouteComparators {
  public static final Comparator<Routes> priceAsc = Comparator.comparingDouble(Routes::getPrice);
  public static final Comparator<Routes> durationAsc = Comparator.comparing(Routes::getDuration);
  public static final Comparator<Routes> priceDesc = priceAsc.reversed();
  public static final Comparator<Routes> durationDesc = durationAsc.reversed();
}
