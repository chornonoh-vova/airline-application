package com.airline.api.utils;

import org.junit.Assert;
import org.junit.Test;

public class TestHeaderUtils {
  @Test
  public void testGetSessionKeyRight() {
    String authHeader = "Basic abcd";
    Assert.assertEquals("abcd", HeaderUtils.getSessionKey(authHeader));
  }

  @Test
  public void testGetSessionKeyWrong() {
    String authHeader = "Basicabcd";
    Assert.assertNull(HeaderUtils.getSessionKey(authHeader));
  }

  @Test
  public void testGetSessionKeyWrong1() {
    String authHeader = "Basic-basic abcd";
    Assert.assertNull(HeaderUtils.getSessionKey(authHeader));
  }
}
