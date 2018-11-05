package com.airline.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class Users implements Serializable {
  @Id
  @Column(name = "user_id")
  private int userId;
  @Column(name = "email")
  private String email;
  @Column(name = "password")
  private String password;
  @Column(name = "passenger_id")
  private int passengerId;
  @Column(name = "role")
  private String role;

  public Users() {

  }

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

  public int getPassengerId() {
    return passengerId;
  }

  public void setPassengerId(int passengerId) {
    this.passengerId = passengerId;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }
}
