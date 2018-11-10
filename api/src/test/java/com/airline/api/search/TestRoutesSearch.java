package com.airline.api.search;

import com.airline.api.model.Routes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestRoutesSearch {
  private List<Routes> input = new ArrayList<>();
  private List<Routes> fromA = new ArrayList<>();
  private List<Routes> toB = new ArrayList<>();
  private List<Routes> fromAAndToB = new ArrayList<>();
  private List<Routes> fromAorB = new ArrayList<>();

  @Before
  public void setup() {
    Routes A = new Routes();
    A.setDepartureAirport("A");
    A.setDestinationAirport("B");
    A.setDuration("05:30:00");
    A.setPrice(500.50);
    Routes B = new Routes();
    B.setDepartureAirport("B");
    B.setDestinationAirport("A");
    B.setDuration("06:30:00");
    B.setPrice(400.99);
    input.add(A);
    input.add(B);
    fromA.add(A);
    toB.add(A);
    fromAAndToB.add(A);
    fromAorB.addAll(input);
  }

  @Test
  public void testFrom() {
    RouteCriteria c = new FromCriteria("A");
    Assert.assertEquals(fromA, c.meetCriteria(input));
  }

  @Test
  public void testTo() {
    RouteCriteria c = new ToCriteria("B");
    Assert.assertEquals(toB, c.meetCriteria(input));
  }

  @Test
  public void testFromAndTo() {
    RouteCriteria c1 = new FromCriteria("A");
    RouteCriteria c2 = new ToCriteria("B");
    RouteCriteria c = new AndCriteria(c1, c2);
    Assert.assertEquals(fromAAndToB, c.meetCriteria(input));
  }

  @Test
  public void testFromOrFrom() {
    RouteCriteria c1 = new FromCriteria("A");
    RouteCriteria c2 = new FromCriteria("B");
    RouteCriteria c = new OrCriteria(c1, c2);
    Assert.assertEquals(fromAorB, c.meetCriteria(input));
  }

  @Test
  public void testSort() {
    input.sort(RouteComparators.priceAsc);
    input.sort(RouteComparators.priceDesc);
    input.sort(RouteComparators.durationAsc);
    input.sort(RouteComparators.durationDesc);
  }
}
