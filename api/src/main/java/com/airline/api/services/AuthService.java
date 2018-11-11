package com.airline.api.services;

import com.airline.api.model.Passengers;
import com.airline.api.model.Sessions;
import com.airline.api.model.Users;
import org.springframework.lang.Nullable;

public interface AuthService {
  /**
   * Create session for user
   *
   * @param email users email
   * @param password users password(SHA-256)
   * @return created session or null, if email or password is bad
   */
  @Nullable
  Sessions login(String email, String password);

  /**
   * Remove session for user
   *
   * @param sessionKey key of session
   * @return true after successful deletion
   */
  boolean logout(String sessionKey);

  /**
   * Adds new user
   *
   * @param email users email in format <username>@<host>
   * @param password users password(SHA-256)
   * @return result of operation
   */
  Users registrate(String email, String password);

  /**
   * Add passenger info for user
   *
   * @param userId users id
   * @param firstName first name
   * @param lastName last name
   * @param address address
   * @param phoneNumber phone number in format +XX XXX XXX XXXX
   * @return result of operation
   */
  Passengers addPassengerInfo(
      int userId, String firstName, String lastName, String address, String phoneNumber);

  /**
   * Get authenticated user by sessionKey
   *
   * @param sessionKey from authentication header
   * @return user, if found or null
   */
  @Nullable
  Users getUserBySessionKey(String sessionKey);

  /**
   * Checks, if particular user has admin access
   *
   * @param user user to check
   * @return true if user has access
   */
  boolean hasAdminAccess(Users user);
}
