package com.airline.api.utils;

public class HeaderUtils {
  public static String getSessionKey(String authHeader) {
    int spaceIndex = authHeader.indexOf(' ');
    if (spaceIndex == -1) return null;
    if (!authHeader.substring(0, spaceIndex).equals("Basic")) return null;
    return authHeader.substring(spaceIndex + 1);
  }
}
