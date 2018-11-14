package com.airline.api.search;

import com.airline.api.model.Flights;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestFlightsSearch {
  private List<Flights> input = new ArrayList<>();
  private List<Flights> from10 = new ArrayList<>();
  private List<Flights> to15 = new ArrayList<>();
  private List<Flights> from10AndTo15 = new ArrayList<>();

  @Before
  public void setup() {
    Flights flights1 = new Flights();
    flights1.setDepartureTime("09:00:00");
    flights1.setArrivalTime("16:00:00");
    Flights flights2 = new Flights();
    flights2.setDepartureTime("10:00:00");
    flights2.setArrivalTime("15:00:00");
    Flights flights3 = new Flights();
    flights3.setDepartureTime("11:00:00");
    flights3.setArrivalTime("14:00:00");
    input.add(flights1);
    input.add(flights2);
    input.add(flights3);
    //expected for 1st
    from10.add(flights2);
    from10.add(flights3);
    //expected for 2nd
    to15.add(flights2);
    to15.add(flights3);
    //expected for 3rd
    from10AndTo15.add(flights2);
    from10AndTo15.add(flights3);
  }

  @Test
  public void testFlightsFrom() {
    Criteria<Flights> from = new FlightFromCriteria("10:00:00");
    Assert.assertEquals(from10, from.meetCriteria(input));
    Assert.assertEquals(input, new FlightFromCriteria(null).meetCriteria(input));
  }

  @Test
  public void testFlightsTo() {
    Criteria<Flights> to = new FlightToCriteria("15:00:00");
    Assert.assertEquals(to15, to.meetCriteria(input));
    Assert.assertEquals(input, new FlightToCriteria(null).meetCriteria(input));
  }

  @Test
  public void testFlightFromAndTo() {
    Criteria<Flights> from = new FlightFromCriteria("10:00:00");
    Criteria<Flights> to = new FlightToCriteria("15:00:00");
    Criteria<Flights> and = new AndCriteria<>(from, to);
    Assert.assertEquals(from10AndTo15, and.meetCriteria(input));
  }
}
