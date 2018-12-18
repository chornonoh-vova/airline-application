package com.airline.api.services;

import com.airline.api.model.Airplanes;
import com.airline.api.model.Flights;
import com.airline.api.model.Tickets;
import com.airline.api.repositories.AirplanesRepository;
import com.airline.api.repositories.FlightsRepository;
import com.airline.api.repositories.TicketsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TicketsServiceImpl implements TicketsService {
  private final TicketsRepository ticketsRepository;
  private final AirplanesRepository airplanesRepository;
  private final FlightsRepository flightsRepository;


  @Autowired
  public TicketsServiceImpl(TicketsRepository ticketsRepository,
                            AirplanesRepository airplanesRepository,
                            FlightsRepository flightsRepository) {
    this.ticketsRepository = ticketsRepository;
    this.airplanesRepository = airplanesRepository;
    this.flightsRepository = flightsRepository;
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

  @Override
  public boolean checkTicket(int flightId, String seat) {
    Flights flights = flightsRepository.getOne(flightId);
    Airplanes airplanes = airplanesRepository.getOne(flights.getAirplaneId());
    if (airplanes.getSeatNumber() == ticketsRepository.countTicketsByFlightId(flightId)) {
      return false;
    }

    List<Tickets> alreadyPurchased = ticketsRepository.getAllByFlightId(flightId);

    for (Tickets t: alreadyPurchased) {
      if (t.getSeat().equals(seat)) {
        return false;
      }
    }

    Matcher m = Pattern.compile("([0-9]|[0-2][0-9]|30)[A-E]").matcher(seat);
    return m.matches();
  }
}
