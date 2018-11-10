package com.airline.api.controllers;

import com.airline.api.model.Sessions;
import com.airline.api.services.AuthService;
import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UsersController {
  private final AuthService authService;

  @Autowired
  public UsersController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  @ResponseBody
  public JsendResponse login(@RequestBody LoginRequest body) {
    JsendResponse resp;
    Sessions session = authService.login(body.email, body.password);
    if (session == null) {
      resp = new JsendResponse("fail", new Message("Wrong email or password"));
    } else {
      resp =
          new JsendResponse(
              "success",
              new JsendData() {
                public String sessionKey = session.getSessionKey();
              });
    }
    return resp;
  }

  @PostMapping("/logout")
  @ResponseBody
  public JsendResponse logout(@RequestBody LogoutRequest body) {
    if (authService.logout(body.sessionKey)) {
      return new JsendResponse("success", new Message("Session removed"));
    } else {
      return new JsendResponse("fail", new Message("Removing failed"));
    }
  }

  @PostMapping("/registrate")
  @ResponseBody
  public JsendResponse registrate(@RequestBody LoginRequest body) {
    JsendResponse resp;
    try {
      authService.registrate(body.email, body.password);
      resp = new JsendResponse("success", new Message("User registrated"));
    } catch (Exception e) {
      resp = new JsendResponse("fail", new Message(e.getMessage()));
    }
    return resp;
  }

  public static class Message implements JsendData {
    public Message(String message) {
      this.message = message;
    }

    public String message;
  }

  public static class LoginRequest {
    String email;
    String password;

    public LoginRequest() {}

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
  }

  public static class LogoutRequest {
    public LogoutRequest() {}
    String sessionKey;

    public String getSessionKey() {
      return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
      this.sessionKey = sessionKey;
    }
  }
}
