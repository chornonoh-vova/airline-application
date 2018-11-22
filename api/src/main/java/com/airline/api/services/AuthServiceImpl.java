package com.airline.api.services;

import com.airline.api.model.Passengers;
import com.airline.api.model.Sessions;
import com.airline.api.model.Users;
import com.airline.api.repositories.PassengersRepository;
import com.airline.api.repositories.SessionsRepository;
import com.airline.api.repositories.UsersRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

  private final UsersRepository usersRepository;
  private final SessionsRepository sessionsRepository;
  private final PassengersRepository passengersRepository;

  @Autowired
  public AuthServiceImpl(
      UsersRepository usersRepository,
      SessionsRepository sessionsRepository,
      PassengersRepository passengersRepository) {
    this.usersRepository = usersRepository;
    this.sessionsRepository = sessionsRepository;
    this.passengersRepository = passengersRepository;
  }

  @Override
  public Sessions login(String email, String password) {
    Users user = usersRepository.findByEmailAndPassword(email, password);
    if (user != null) {
      Sessions session = new Sessions();
      session.setUsers(user);
      session.setSessionKey(UUID.randomUUID().toString());
      sessionsRepository.save(session);
      return session;
    }
    return null;
  }

  @Override
  public boolean logout(String sessionKey) {
    Sessions session = sessionsRepository.findBySessionKey(sessionKey);
    if (session == null) {
      return false;
    }
    sessionsRepository.delete(session);
    return true;
  }

  @Override
  public Users register(String email, String password) {
    Users user = new Users();
    // TODO: add email format checking
    user.setEmail(email);
    user.setPassword(password);
    user.setRole("USER");
    try {
      System.err.println("Saving user: " + new ObjectMapper().writeValueAsString(user));
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    usersRepository.save(user);
    return user;
  }

  @Override
  public Passengers addPassengerInfo(
      int userId, String firstName, String lastName, String address, String phoneNumber) {
    Optional<Users> user = usersRepository.findById(userId);
    if (user.isPresent()) {
      Users u = user.get();
      Passengers passenger = new Passengers();
      passenger.setFirstName(firstName);
      passenger.setLastName(lastName);
      passenger.setAddress(address);
      // TODO: add phone number checking
      passenger.setPhoneNumber(phoneNumber);
      Passengers p = passengersRepository.saveAndFlush(passenger);
      return p;
    }
    return null;
  }

  @Override
  public Users getUserBySessionKey(String sessionKey) {
    Sessions session = sessionsRepository.findBySessionKey(sessionKey);
    if (session != null) {
      return session.getUsers();
    }
    return null;
  }

  @Override
  public boolean hasAdminAccess(Users user) {
    return user.getRole().equals("ADMIN");
  }
}
