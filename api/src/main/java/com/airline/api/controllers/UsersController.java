package com.airline.api.controllers;

import com.airline.api.model.Passengers;
import com.airline.api.model.Sessions;
import com.airline.api.services.AuthService;
import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import com.airline.api.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

  @PostMapping("/register")
  @ResponseBody
  public JsendResponse register(@RequestBody LoginRequest body) {
    JsendResponse resp;
    try {
      authService.register(body.email, body.password);
      resp = new JsendResponse("success", new Message("User registered"));
    } catch (Exception e) {
      resp = new JsendResponse("fail", new Message(e.getMessage()));
    }
    return resp;
  }

  @PostMapping("/users/{userId}/passengerInfo")
  @ResponseBody
  public JsendResponse addPassengerInfo(
      @PathVariable int userId, @RequestBody PassengerRequest body) {
    Passengers p =
        authService.addPassengerInfo(
            userId, body.firstName, body.lastName, body.address, body.phoneNumber);
    JsendData data =
        new JsendData() {
          public int passengerId = p.getPassengerId();
        };
    return new JsendResponse("success", data);
  }

  public static class PassengerRequest {
    String lastName;
    String firstName;
    String address;
    String phoneNumber;

    public PassengerRequest() {}

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
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
    String sessionKey;

    public LogoutRequest() {}

    public String getSessionKey() {
      return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
      this.sessionKey = sessionKey;
    }
  }
}
