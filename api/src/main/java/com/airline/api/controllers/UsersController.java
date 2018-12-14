package com.airline.api.controllers;

import com.airline.api.model.Passengers;
import com.airline.api.model.Sessions;
import com.airline.api.model.Users;
import com.airline.api.services.AuthService;
import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController extends BaseController {
  private final AuthService authService;
  private final AuthHelper authHelper;

  @Autowired
  public UsersController(AuthService authService) {
    this.authService = authService;
    this.authHelper = new AuthHelper(authService);
  }

  @PostMapping("/login")
  @ResponseBody
  public JsendResponse login(@RequestBody LoginRequest body) {
    Sessions session = authService.login(body.email, body.password);
    if (session == null) {
      return fail("Wrong email or password");
    } else {
      return success(new JsendData() {
        public String sessionKey = session.getSessionKey();
        public int userId = session.getUsers().getUserId();
      });
    }
  }

  @GetMapping("/users/{userId}")
  @ResponseBody
  public JsendResponse getUserInfo(
      @PathVariable int userId, @RequestHeader(name = "Authorization") String authorization) {
    if (authHelper.isRegularUser(authorization)) {
      JsendData data = new JsendData() { public Users user = authService.getUser(userId); };
      return success(data);
    } else {
      return authHelper.UNAUTHORIZED;
    }
  }

  @GetMapping("/users/{userId}/passengerInfo")
  @ResponseBody
  public JsendResponse getPassengerInfo(
      @PathVariable int userId, @RequestHeader(name = "Authorization") String authorization) {
    if (authHelper.isRegularUser(authorization)) {
      JsendData data =
          new JsendData() { public Passengers passenger = authService.getPassengerInfo(userId); };
      return success(data);
    } else {
      return authHelper.UNAUTHORIZED;
    }
  }

  @PostMapping("/logout")
  @ResponseBody
  public JsendResponse logout(@RequestBody LogoutRequest body) {
    if (authService.logout(body.sessionKey)) {
      return success("Session removed");
    } else {
      return fail("Removing failed");
    }
  }

  @PostMapping("/register")
  @ResponseBody
  public JsendResponse register(@RequestBody LoginRequest body) {
    try {
      Users u = authService.register(body.email, body.password);
      return success(new JsendData() { public int userId = u.getUserId(); });
    } catch (Exception e) {
      return fail(e.getMessage());
    }
  }

  @PostMapping("/users/{userId}/passengerInfo")
  @ResponseBody
  public JsendResponse addPassengerInfo(
      @PathVariable int userId, @RequestBody PassengerRequest body) {
    Passengers p = authService.addPassengerInfo(
        userId, body.firstName, body.lastName, body.address, body.phoneNumber);
    JsendData data = new JsendData() { public int passengerId = p.getPassengerId(); };
    return success(data);
  }

  public static class PassengerRequest {
    public String lastName;
    public String firstName;
    public String address;
    public String phoneNumber;
  }

  public static class LoginRequest {
    public String email;
    public String password;
  }

  public static class LogoutRequest { public String sessionKey; }
}
