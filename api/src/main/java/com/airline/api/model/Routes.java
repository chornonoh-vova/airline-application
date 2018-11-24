package com.airline.api.model;

import com.airline.api.utils.JsendData;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "routes")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Routes implements Serializable, JsendData {
  @Id
  @Column(name = "route_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int routeId;

  @Column(name = "departure_airport")
  private String departureAirport;

  @Column(name = "destination_airport")
  private String destinationAirport;

  @Column(name = "price")
  private double price;

  @Column(name = "duration")
  private String duration;

  public Routes() {}

  @JsonCreator
  public Routes(
      @JsonProperty(value = "departureAirport") String departureAirport,
      @JsonProperty(value = "destinationAirport") String destinationAirport,
      @JsonProperty(value = "price") double price,
      @JsonProperty(value = "duration") String duration) {
    this.departureAirport = departureAirport;
    this.destinationAirport = destinationAirport;
    this.price = price;
    this.duration = duration;
  }

  public int getRouteId() {
    return routeId;
  }

  public void setRouteId(int routeId) {
    this.routeId = routeId;
  }

  public String getDepartureAirport() {
    return departureAirport;
  }

  public void setDepartureAirport(String departureAirport) {
    this.departureAirport = departureAirport;
  }

  public String getDestinationAirport() {
    return destinationAirport;
  }

  public void setDestinationAirport(String destinationAirport) {
    this.destinationAirport = destinationAirport;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }
}
