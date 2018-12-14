package com.airline.api.model;

import com.airline.api.utils.JsendData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "flights")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Flights implements JsendData, Serializable {
  @Id
  @Column(name = "flight_id")
  private int flightId;

  @Column(name = "departure_time")
  private String departureTime;

  @Column(name = "arrival_time")
  private String arrivalTime;

  @Column(name = "cancellation_status")
  private boolean cancellationStatus;

  @Column(name = "route_id")
  private int routeId;

  @Column(name = "pilot_id")
  private int pilotId;

  @Column(name = "airplane_id")
  private int airplaneId;

  public Flights() {
  }

  public int getFlightId() {
    return flightId;
  }

  public void setFlightId(int flightId) {
    this.flightId = flightId;
  }

  public int getRouteId() {
    return routeId;
  }

  public void setRouteId(int routeId) {
    this.routeId = routeId;
  }

  public String getDepartureTime() {
    return departureTime;
  }

  public void setDepartureTime(String departureTime) {
    this.departureTime = departureTime;
  }

  public String getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(String arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public boolean isCancellationStatus() {
    return cancellationStatus;
  }

  public void setCancellationStatus(boolean cancellationStatus) {
    this.cancellationStatus = cancellationStatus;
  }

  public int getPilotId() {
    return pilotId;
  }

  public void setPilotId(int pilotId) {
    this.pilotId = pilotId;
  }

  public int getAirplaneId() {
    return airplaneId;
  }

  public void setAirplaneId(int airplaneId) {
    this.airplaneId = airplaneId;
  }
}
