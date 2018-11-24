package com.airline.api.model;

import com.airline.api.utils.JsendData;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "passengers")
public class Passengers implements Serializable, JsendData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "passenger_id")
  private int passengerId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "address")
  private String address;

  @Column(name = "phone_number")
  private String phoneNumber;

  @OneToOne()
  @JoinColumn(name = "user_id")
  private Users user;

  public Passengers() {}

  public int getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(int passengerId) {
    this.passengerId = passengerId;
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

  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }
}
