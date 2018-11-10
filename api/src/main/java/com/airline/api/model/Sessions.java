package com.airline.api.model;

import com.airline.api.utils.JsendData;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sessions")
public class Sessions implements Serializable, JsendData {
  @Id
  @Column(name = "session_id")
  private int sessionId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private Users users;

  @Column(name = "session_key")
  private String sessionKey;

  public Sessions() {}

  public int getSessionId() {
    return sessionId;
  }

  public void setSessionId(int sessionId) {
    this.sessionId = sessionId;
  }

  public String getSessionKey() {
    return sessionKey;
  }

  public void setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
  }

  public Users getUsers() {
    return users;
  }

  public void setUsers(Users users) {
    this.users = users;
  }
}
