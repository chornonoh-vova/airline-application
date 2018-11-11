package com.airline.api.utils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = JsendSerializer.class)
public class JsendResponse {
  private String status;
  private JsendData data;
  private String message;

  // for success and fail responses
  public JsendResponse(String status, JsendData data) {
    this.status = status;
    this.data = data;
    this.message = null;
  }

  // for error response
  public JsendResponse(String status, String message) {
    this.status = status;
    this.message = message;
    this.data = null;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public JsendData getData() {
    return data;
  }

  public void setData(JsendData data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
