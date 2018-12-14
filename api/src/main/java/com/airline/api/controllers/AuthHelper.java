package com.airline.api.controllers;

import com.airline.api.model.Users;
import com.airline.api.services.AuthService;
import com.airline.api.utils.HeaderUtils;
import com.airline.api.utils.JsendResponse;
import com.airline.api.utils.Message;

/**
 * Authorization helper class, uses AuthService to retrieve users from database
 */
public class AuthHelper {
  private static final String ADMIN_KEY = "ADMIN";
  private static final String USER_KEY = "USER";
  public final JsendResponse UNAUTHORIZED = new JsendResponse("fail", new Message("Unauthorized"));

  private AuthService authService;

  public AuthHelper(AuthService authService) {
    this.authService = authService;
  }

  /**
   * Checks, if user authorized with header
   * @param authHeader header to check
   * @return true if user session exists and role = user
   */
  public boolean isRegularUser(String authHeader) {
    Users user = getUser(authHeader);

    return user != null && user.getRole().equals(USER_KEY);
  }

  /**
   * Checks, if admin authorized with header
   * @param authHeader header to check
   * @return true if user session exists and role = admin
   */
  public boolean isAdminUser(String authHeader) {
    Users user = getUser(authHeader);

    return user != null && user.getRole().equals(ADMIN_KEY);
  }

  private Users getUser(String authHeader) {
    String sessionKey = HeaderUtils.getSessionKey(authHeader);
    return authService.getUserBySessionKey(sessionKey);
  }
}
