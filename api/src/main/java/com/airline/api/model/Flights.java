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

  @OneToOne
  @JoinColumn(name = "pilot_id")
  private Pilots pilot;

  @OneToOne
  @JoinColumn(name = "airplane_id")
  private Airplanes airplane;

  public Flights() {
  }

  public int getFlightId() {
    return flightId;
  }

  public void setFlightId(int flightId) {
    this.flightId = flightId;
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

  public Pilots getPilot() {
    return pilot;
  }

  public void setPilot(Pilots pilot) {
    this.pilot = pilot;
  }

  public Airplanes getAirplane() {
    return airplane;
  }

  public void setAirplane(Airplanes airplane) {
    this.airplane = airplane;
  }
}
