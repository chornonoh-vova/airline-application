package com.airline.api.controllers;

import com.airline.api.model.Flights;
import com.airline.api.services.AuthService;
import com.airline.api.services.FlightsService;
import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FlightsController extends BaseController {
  private final FlightsService flightsService;
  private final AuthHelper authHelper;

  @Autowired
  public FlightsController(FlightsService flightsService, AuthService authService) {
    this.flightsService = flightsService;
    this.authHelper = new AuthHelper(authService);
  }

  @GetMapping("/flights")
  @ResponseBody
  public JsendResponse getAllFlights() {
    JsendData data =
        new JsendData() { public List<Flights> flights = flightsService.getAllFlights(); };
    return success(data);
  }

  @GetMapping("/flights/{flightId}")
  @ResponseBody
  public JsendResponse getFlightById(@PathVariable int flightId) {
    return flightsService.getFlightById(flightId)
        .map(f -> success(new JsendData() { public Flights flight = f; }))
        .orElseGet(() -> fail("Unable to find flight"));
  }

  @GetMapping("/search/flights")
  @ResponseBody
  public JsendResponse searchFlights(@RequestParam(name = "from", required = false) String from,
      @RequestParam(name = "to", required = false) String to,
      @RequestParam(name = "sort", defaultValue = "none") String sort,
      @RequestParam(name = "order", defaultValue = "desc") String order) {
    JsendData data = new JsendData() {
      public List<Flights> flights = flightsService.search(from, to, sort, order);
    };
    return success(data);
  }

  @PostMapping("/flights/add")
  @ResponseBody
  public JsendResponse addFlight(
      @RequestBody Flights body, @RequestHeader(name = "Authorization") String authorization) {
    if (authHelper.isAdminUser(authorization)) {
      try {
        return success(
            new JsendData() { public int flightId = flightsService.add(body).getFlightId(); });
      } catch (Exception e) {
        return fail(e.getMessage());
      }
    } else {
      return authHelper.UNAUTHORIZED;
    }
  }

  @PutMapping("/flights/{flightId}")
  @ResponseBody
  public JsendResponse editFlight(@PathVariable int flightId, @RequestBody Flights body,
      @RequestHeader(name = "Authorization") String authorization) {
    if (authHelper.isAdminUser(authorization)) {
      try {
        body = flightsService.edit(flightId, body);
        Flights finalBody = body;
        return success(new JsendData() { public int flightId = finalBody.getFlightId(); });
      } catch (Exception e) {
        return fail(e.getMessage());
      }
    } else {
      return authHelper.UNAUTHORIZED;
    }
  }
}
