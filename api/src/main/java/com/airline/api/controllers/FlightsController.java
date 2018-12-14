package com.airline.api.controllers;

import com.airline.api.model.Flights;
import com.airline.api.services.FlightsService;
import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import com.airline.api.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FlightsController {

  private final FlightsService flightsService;

  @Autowired
  public FlightsController(FlightsService flightsService) {
    this.flightsService = flightsService;
  }

  @GetMapping("/flights")
  @ResponseBody
  public JsendResponse getAllFlights() {
    JsendData data =
        new JsendData() {
          public List<Flights> flights = flightsService.getAllFlights();
        };
    return new JsendResponse("success", data);
  }

  @GetMapping("/flights/{flightId}")
  @ResponseBody
  public JsendResponse getFlightById(@PathVariable int flightId) {
    return flightsService
        .getFlightById(flightId)
        .map(
            f ->
                new JsendResponse(
                    "success",
                    new JsendData() {
                      public Flights flight = f;
                    }))
        .orElseGet(() -> new JsendResponse("fail", new Message("Unable to find flight")));
  }

  @GetMapping("/search/flights")
  @ResponseBody
  public JsendResponse searchFlights(
      @RequestParam(name = "from", required = false) String from,
      @RequestParam(name = "to", required = false) String to,
      @RequestParam(name = "sort", defaultValue = "none") String sort,
      @RequestParam(name = "order", defaultValue = "desc") String order) {
    JsendData data =
        new JsendData() {
          public List<Flights> flights = flightsService.search(from, to, sort, order);
        };
    return new JsendResponse("success", data);
  }

  @PostMapping("/flights/add")
  @ResponseBody
  public JsendResponse addFlight(@RequestBody Flights body
      /*, @RequestHeader(name = "Authorization") String authorization*/ ) {
    // TODO: add admin authorization
    try {
      return new JsendResponse(
          "success",
          new JsendData() {
            public int flightId = flightsService.add(body).getFlightId();
          });
    } catch (Exception e) {
      return new JsendResponse("fail", new Message(e.getMessage()));
    }
  }

  @PutMapping("/flights/{flightId}")
  @ResponseBody
  public JsendResponse editFlight(@PathVariable int flightId, @RequestBody Flights body
      /*, @RequestHeader(name = "Authorization") String authorization*/ ) {
    // TODO: add admin authorization
    try {
      body = flightsService.edit(flightId, body);
      Flights finalBody = body;
      return new JsendResponse(
          "success",
          new JsendData() {
            public int flightId = finalBody.getFlightId();
          });
    } catch (Exception e) {
      return new JsendResponse("fail", new Message(e.getMessage()));
    }
  }
}
