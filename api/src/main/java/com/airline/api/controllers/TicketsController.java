package com.airline.api.controllers;

import com.airline.api.model.Tickets;
import com.airline.api.model.Users;
import com.airline.api.services.AuthService;
import com.airline.api.services.TicketsService;
import com.airline.api.utils.HeaderUtils;
import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import com.airline.api.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TicketsController {
  private final TicketsService ticketsService;
  private final AuthService authService;

  @Autowired
  public TicketsController(TicketsService ticketsService, AuthService authService) {
    this.ticketsService = ticketsService;
    this.authService = authService;
  }

  @GetMapping("/tickets")
  @ResponseBody
  public JsendResponse getAllTickets(@RequestHeader(name = "Authorization") String authorization) {
    String sessionKey = HeaderUtils.getSessionKey(authorization);
    Users user = authService.getUserBySessionKey(sessionKey);
    if (user != null) {
      if (user.getPassenger() == null) {
        return new JsendResponse("fail", new Message("Add passenger info before"));
      }
      JsendData data =
          new JsendData() {
            public List<Tickets> tickets =
                ticketsService.getAllTickets(user.getPassenger().getPassengerId());
          };
      return new JsendResponse("success", data);
    } else {
      return new JsendResponse("fail", new Message("Wrong authorization"));
    }
  }

  @GetMapping("/tickets/{ticketId}")
  @ResponseBody
  public JsendResponse getTicketById(
      @RequestHeader(name = "Authorization") String authorization, @PathVariable int ticketId) {
    String sessionKey = HeaderUtils.getSessionKey(authorization);
    Users user = authService.getUserBySessionKey(sessionKey);
    if (user != null) {
      if (user.getPassenger() == null) {
        return new JsendResponse("fail", new Message("Add passenger info before"));
      }
      return ticketsService
          .getTicketById(user.getPassenger().getPassengerId(), ticketId)
          .map(
              tickets ->
                  new JsendResponse(
                      "success",
                      new JsendData() {
                        public Tickets ticket = tickets;
                      }))
          .orElseGet(() -> new JsendResponse("fail", new Message("Ticket not found")));
    } else {
      return new JsendResponse("fail", new Message("Wrong authorization"));
    }
  }

  @PostMapping("/tickets/{flightId}")
  @ResponseBody
  public JsendResponse buyTicket(
      @RequestHeader(name = "Authorization") String authorization, @PathVariable int flightId, @RequestBody BuyRequest body) {
    String sessionKey = HeaderUtils.getSessionKey(authorization);
    Users user = authService.getUserBySessionKey(sessionKey);
    if (user != null) {
      if (user.getPassenger() == null) {
        return new JsendResponse("fail", new Message("Add passenger info before"));
      }
      return new JsendResponse("success", ticketsService.buy(user.getPassenger().getPassengerId(), flightId, body.seat));
    } else {
      return new JsendResponse("fail", new Message("Wrong authorization"));
    }
  }

  private static class BuyRequest {
    public String seat;
  }
}
