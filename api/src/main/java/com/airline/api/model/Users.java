package com.airline.api.model;

import com.airline.api.utils.JsendData;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "users")
public class Users implements Serializable, JsendData {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private int userId;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @JsonIgnore
  @OneToOne(mappedBy = "user")
  private Passengers passenger;

  @JsonIgnore
  @OneToMany(mappedBy = "users")
  private List<Sessions> sessions;

  @JsonIgnore
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

  public Passengers getPassenger() {
    return passenger;
  }

  public void setPassenger(Passengers passenger) {
    this.passenger = passenger;
  }

  public List<Sessions> getSessions() {
    return sessions;
  }

  public void setSessions(List<Sessions> sessions) {
    this.sessions = sessions;
  }
}
