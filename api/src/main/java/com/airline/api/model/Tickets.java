package com.airline.api.model;

import com.airline.api.utils.JsendData;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "tickets")
public class Tickets implements Serializable, JsendData {
  @Id
  @Column(name = "ticket_id")
  private int ticketId;
  @Column(name = "flightId")
  private int flightId;
  @Column(name = "passenger_id")
  private int passengerId;
  @Column(name = "seat")
  private String seat;

  public Tickets() {
  }

  public int getTicketId() {
    return ticketId;
  }

  public void setTicketId(int ticketId) {
    this.ticketId = ticketId;
  }

  public int getFlightId() {
    return flightId;
  }

  public void setFlightId(int flightId) {
    this.flightId = flightId;
  }

  public int getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(int passengerId) {
    this.passengerId = passengerId;
  }

  public String getSeat() {
    return seat;
  }

  public void setSeat(String seat) {
    this.seat = seat;
  }
}
