package com.airline.api.services;

import com.airline.api.model.Tickets;

import java.util.List;
import java.util.Optional;

public interface TicketsService {
  List<Tickets> getAllTickets(int passengerId);
  Optional<Tickets> getTicketById(int passengerId, int ticketId);
  Tickets buy(int passengerId, int flightId, String seat);
  boolean checkTicket(int flightId, String seat);
}
