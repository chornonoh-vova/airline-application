package com.airline.api.model;

import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestRoutes {

  @Test
  public void testSerialization() throws JsonProcessingException {
    Routes route = new Routes();
    route.setDepartureAirport("A");
    route.setDestinationAirport("B");
    route.setPrice(500);
    route.setDuration("05:30:00");
    String json = new ObjectMapper().writeValueAsString(route);
    Assert.assertEquals(
        "{\"departureAirport\":\"A\",\"destinationAirport\":\"B\",\"price\":500.0,\"duration\":\"05:30:00\",\"routeId\":0}",
        json);
  }

  @Test
  public void testDeserialization() throws IOException {
    Routes route = new Routes();
    route.setDepartureAirport("A");
    route.setDestinationAirport("B");
    route.setPrice(500);
    route.setDuration("05:30:00");
    String json =
        "{\"departureAirport\":\"A\",\"destinationAirport\":\"B\",\"price\":500.0,\"duration\":\"05:30:00\"}";
    Routes res = new ObjectMapper().readValue(json, Routes.class);
    Assert.assertArrayEquals(
        new String[] {
          route.getDestinationAirport(), route.getDepartureAirport(), route.getDuration()
        },
        new String[] {res.getDestinationAirport(), res.getDepartureAirport(), res.getDuration()});
  }

  @Test
  public void testJsendSend() throws JsonProcessingException {
    Routes route = new Routes();
    route.setDepartureAirport("A");
    route.setDestinationAirport("B");
    route.setPrice(500);
    route.setDuration("05:30:00");

    List<Routes> routes = new ArrayList<>();
    routes.add(route);

    JsendData data =
        new JsendData() {
          public List<Routes> route = routes;
        };

    String json = new ObjectMapper().writeValueAsString(new JsendResponse("success", data));
    Assert.assertEquals(
        "{\"status\":\"success\",\"data\":{\"route\":[{\"departureAirport\":\"A\","
            + "\"destinationAirport\":\"B\",\"price\":500.0,\"duration\":\"05:30:00\","
            + "\"routeId\":0}]}}",
        json);
  }
}
