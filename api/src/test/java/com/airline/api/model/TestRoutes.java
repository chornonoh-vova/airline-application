package com.airline.api.model;

import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
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
        new String[] {
            res.getDestinationAirport(), res.getDepartureAirport(), res.getDuration()
        });
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
    routes.add(route);
    routes.add(route);

    JsendData data =
        new JsendData() {
          @JsonSerialize(using = RoutesListSerializer.class)
          List<Routes> route = routes;
        };

    System.out.println(
    new ObjectMapper().writeValueAsString(new JsendResponse("success", data)));
  }

  public static class RoutesListSerializer extends StdSerializer<List<Routes>> {
    public RoutesListSerializer() {
      this(null);
    }

    public RoutesListSerializer(Class<List<Routes>> t) {
      super(t);
    }
    @Override
    public void serialize(List<Routes> value, JsonGenerator gen, SerializerProvider provider) throws IOException {
      gen.writeStartArray();
      for (Routes i : value) {
        gen.writeObject(i);
      }
      gen.writeEndArray();
    }
  }
}
