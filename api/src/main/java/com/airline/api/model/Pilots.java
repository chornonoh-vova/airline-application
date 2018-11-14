package com.airline.api.model;

import com.airline.api.utils.JsendData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "pilots")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pilots implements JsendData, Serializable {
  @Id
  @Column(name = "pilot_id")
  private int pilotId;

  @OneToOne(mappedBy = "pilots")
  private Flights flight;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "address")
  private String address;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "experience")
  private int experience;

  public Pilots() {
  }

  public int getPilotId() {
    return pilotId;
  }

  public void setPilotId(int pilotId) {
    this.pilotId = pilotId;
  }

  public Flights getFlight() {
    return flight;
  }

  public void setFlight(Flights flight) {
    this.flight = flight;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }
}
