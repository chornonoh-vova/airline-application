package com.airline.api.utils;

import com.airline.api.model.Users;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class TestJsendSerializer {
  @Test
  public void testJsendError() throws JsonProcessingException {
    JsendResponse res = new JsendResponse("error", "error message");
    String json = new ObjectMapper().writeValueAsString(res);
    Assert.assertEquals("{\"status\":\"error\",\"message\":\"error message\"}", json);
  }

  @Test
  public void testJsendSuccessDataMessage() throws JsonProcessingException {
    JsendData data =
        new JsendData() {
          public String message = "success message";
        };
    JsendResponse res = new JsendResponse("success", data);
    String json = new ObjectMapper().writeValueAsString(res);
    Assert.assertEquals(
        "{\"status\":\"success\",\"data\":{\"message\":\"success message\"}}", json);
  }

  @Test
  public void testJsendSuccessDataSessionKey() throws JsonProcessingException {
    JsendData data =
        new JsendData() {
          public String sessionKey = "550e8400-e29b-41d4-a716-446655440000";
        };
    JsendResponse res = new JsendResponse("success", data);
    String json = new ObjectMapper().writeValueAsString(res);
    Assert.assertEquals(
        "{\"status\":\"success\",\"data\":{\"sessionKey\":\"550e8400-e29b-41d4-a716-446655440000\"}}",
        json);
  }

  @Test
  public void testJsendSuccessWithUserInfo() throws JsonProcessingException {
    Users user = new Users();
    user.setEmail("admin@localhost");
    user.setPassword("8D969EEF6ECAD3C29A3A629280E686CF0C3F5D5A86AFF3CA12020C923ADC6C92");
    user.setRole("USER");
    JsendResponse res = new JsendResponse("success", user);
    String json = new ObjectMapper().writeValueAsString(res);
    String userJson = new ObjectMapper().writeValueAsString(user);
    Assert.assertNotEquals("{\"status\":\"success\",\"data\":" + userJson + "}", json);
  }

  @Test
  public void testJsendFailWithMessage() throws JsonProcessingException {
    JsendData data =
        new JsendData() {
          public String message = "fail message";
        };
    JsendResponse res = new JsendResponse("fail", data);
    String json = new ObjectMapper().writeValueAsString(res);
    Assert.assertEquals("{\"status\":\"fail\",\"data\":{\"message\":\"fail message\"}}", json);
  }
}
