package com.airline.api.repositories;

import com.airline.api.model.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketsRepository extends JpaRepository<Tickets, Integer> {
  List<Tickets> getAllByPassengerId(int passengerId);
  Optional<Tickets> getTicketsByPassengerIdAndTicketId(int passengerId, int ticketId);
}
