package com.airline.api.model;

import com.airline.api.utils.JsendData;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class Users implements Serializable, JsendData {
  @Id
  @Column(name = "user_id")
  private int userId;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @OneToOne
  @JoinColumn(name = "passenger_id")
  private Passengers passengers;

  @OneToMany(mappedBy = "users")
  private List<Sessions> sessions;

  @Column(name = "role")
  private String role;

  public Users() {}

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Passengers getPassengers() {
    return passengers;
  }

  public void setPassengers(Passengers passengers) {
    this.passengers = passengers;
  }

  public List<Sessions> getSessions() {
    return sessions;
  }

  public void setSessions(List<Sessions> sessions) {
    this.sessions = sessions;
  }
}
