package com.airline.api.controllers;

import com.airline.api.model.Routes;
import com.airline.api.services.RoutesService;
import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class RoutesController {
  private final RoutesService routesService;

  @Autowired
  public RoutesController(RoutesService routesService) {
    this.routesService = routesService;
  }

  @GetMapping("/routes")
  @ResponseBody
  public JsendResponse getAllRoutes() {
    JsendData data =
        new JsendData() {
          @JsonSerialize(using = RoutesListSerializer.class)
          List<Routes> routes = routesService.getAllRoutes();
        };
    return new JsendResponse("success", data);
  }

  @GetMapping("/routes/{routeId}")
  @ResponseBody
  public JsendResponse getRouteById(@PathVariable int routeId) {
    return routesService
        .getRouteById(routeId)
        .map(routes -> new JsendResponse("success", new RouteResponse(routes)))
        .orElseGet(
            () -> new JsendResponse("fail", new UsersController.Message("Unable to find route")));
  }

  @GetMapping("/search/routes")
  @ResponseBody
  public JsendResponse searchRoutes(
      @RequestParam(name = "from", required = false) String from,
      @RequestParam(name = "to", required = false) String to,
      @RequestParam(name = "sort", defaultValue = "none") String sort,
      @RequestParam(name = "order", defaultValue = "desc") String order) {
    JsendData data =
        new JsendData() {
          @JsonSerialize(using = RoutesListSerializer.class)
          List<Routes> routes = routesService.search(from, to, sort, order);
        };
    return new JsendResponse("success", data);
  }

  @PostMapping("/routes/add")
  @ResponseBody
  public JsendResponse addRoute(@RequestBody Routes body
      /*, @RequestHeader(name = "Authorization") String authorization*/ ) {
    // TODO: add admin authorization
    try {
      body = routesService.add(body);
      return new JsendResponse("success", new IdResponse(body.getRouteId()));
    } catch (Exception e) {
      return new JsendResponse("fail", new UsersController.Message(e.getMessage()));
    }
  }

  @PutMapping("/routes/{routeId}")
  @ResponseBody
  public JsendResponse editRoute(@PathVariable int routeId, @RequestBody Routes body
      /*, @RequestHeader(name = "Authorization") String authorization*/ ) {
    // TODO: add admin authorization
    try {
      body = routesService.edit(routeId, body);
      return new JsendResponse("success", new IdResponse(body.getRouteId()));
    } catch (Exception e) {
      return new JsendResponse("fail", new UsersController.Message(e.getMessage()));
    }
  }

  public static class IdResponse implements JsendData {
    public int routeId;

    public IdResponse(int id) {
      this.routeId = id;
    }
  }

  public static class RouteResponse implements JsendData {
    public Routes route;

    public RouteResponse(Routes r) {
      this.route = r;
    }
  }

  private static class RoutesListSerializer extends StdSerializer<List<Routes>> {
    public RoutesListSerializer() {
      this(null);
    }

    public RoutesListSerializer(Class<List<Routes>> t) {
      super(t);
    }

    @Override
    public void serialize(List<Routes> value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
      gen.writeStartArray();
      for (Routes i : value) {
        gen.writeObject(i);
      }
      gen.writeEndArray();
    }
  }
}
