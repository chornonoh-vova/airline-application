package com.airline.api.controllers;

import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

public class TestBaseController extends BaseController {
  @Test
  public void testSuccessMessage() throws JsonProcessingException {
    JsendResponse r = success("Response");
    String expected = "{\"status\":\"success\",\"data\":{\"message\":\"Response\"}}";
    Assert.assertEquals(expected, new ObjectMapper().writeValueAsString(r));
  }

  @Test
  public void testSuccessPayload() throws JsonProcessingException {
    JsendResponse r = success(new JsendData() {
      public int someId = 0;
    });
    String expected = "{\"status\":\"success\",\"data\":{\"someId\":0}}";
    Assert.assertEquals(expected, new ObjectMapper().writeValueAsString(r));
  }

  @Test
  public void testSuccessPayloadComplex() throws JsonProcessingException {
    JsendResponse r = success(new JsendData() {
      public int someOtherId = 123;
      public String[] values = {"value1", "value2", "value3"};
    });
    String expected = "{\"status\":\"success\",\"data\":{\"someOtherId\":123,\"values\":[\"value1\",\"value2\",\"value3\"]}}";
    Assert.assertEquals(expected, new ObjectMapper().writeValueAsString(r));
  }

  @Test
  public void testFailureMessage() throws JsonProcessingException {
    JsendResponse r = fail("Failure");
    String expected = "{\"status\":\"fail\",\"data\":{\"message\":\"Failure\"}}";
    Assert.assertEquals(expected, new ObjectMapper().writeValueAsString(r));
  }

  @Test
  public void testErrorMessage() throws JsonProcessingException {
    JsendResponse r = error("Error");
    String expected = "{\"status\":\"error\",\"message\":\"Error\"}";
    Assert.assertEquals(expected, new ObjectMapper().writeValueAsString(r));
  }
}
