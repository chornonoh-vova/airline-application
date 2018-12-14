package com.airline.api.model;

import com.airline.api.utils.JsendData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "airplanes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Airplanes implements JsendData, Serializable {
  @Id
  @Column(name = "airplane_id")
  private int airplaneId;

  @Column(name = "flight_id")
  private int flightId;

  @Column(name = "airplane_number")
  private int airplaneNumber;

  @Column(name = "model")
  private String model;

  @Column(name = "manufacture_date")
  private String manufactureDate;

  @Column(name = "lifetime")
  private int lifetime;

  @Column(name = "seat_number")
  private int seatNumber;

  public Airplanes() {
  }

  public int getAirplaneId() {
    return airplaneId;
  }

  public void setAirplaneId(int airplaneId) {
    this.airplaneId = airplaneId;
  }

  public int getFlightId() {
    return flightId;
  }

  public void setFlightId(int flightId) {
    this.flightId = flightId;
  }

  public int getAirplaneNumber() {
    return airplaneNumber;
  }

  public void setAirplaneNumber(int airplaneNumber) {
    this.airplaneNumber = airplaneNumber;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getManufactureDate() {
    return manufactureDate;
  }

  public void setManufactureDate(String manufactureDate) {
    this.manufactureDate = manufactureDate;
  }

  public int getLifetime() {
    return lifetime;
  }

  public void setLifetime(int lifetime) {
    this.lifetime = lifetime;
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }
}
