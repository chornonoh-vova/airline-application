package com.airline.api.services;

import com.airline.api.model.Tickets;
import com.airline.api.repositories.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketsServiceImpl implements TicketsService {
  private final TicketsRepository ticketsRepository;

  @Autowired
  public TicketsServiceImpl(TicketsRepository ticketsRepository) {
    this.ticketsRepository = ticketsRepository;
  }

  @Override
  public List<Tickets> getAllTickets(int passengerId) {
    return ticketsRepository.getAllByPassengerId(passengerId);
  }

  @Override
  public Optional<Tickets> getTicketById(int passengerId, int ticketId) {
    return ticketsRepository.getTicketsByPassengerIdAndTicketId(passengerId, ticketId);
  }

  @Override
  public Tickets buy(int passengerId, int flightId, String seat) {
    Tickets ticket = new Tickets();
    ticket.setPassengerId(passengerId);
    ticket.setFlightId(flightId);
    ticket.setSeat(seat);
    return ticketsRepository.save(ticket);
  }
}
