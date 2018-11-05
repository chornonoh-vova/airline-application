package com.airline.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "sessions")
public class Sessions implements Serializable {
  @Id
  @Column(name = "session_id")
  private int sessionId;
  @Column(name = "user_id")
  private int userId;
  @Column(name = "session_key")
  private String sessionKey;

  public Sessions() {

  }

  public int getSessionId() {
    return sessionId;
  }

  public void setSessionId(int sessionId) {
    this.sessionId = sessionId;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getSessionKey() {
    return sessionKey;
  }

  public void setSessionKey(String sessionKey) {
    this.sessionKey = sessionKey;
  }
}
