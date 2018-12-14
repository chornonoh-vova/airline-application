package com.airline.api.controllers;

import com.airline.api.utils.JsendData;
import com.airline.api.utils.JsendResponse;
import com.airline.api.utils.Message;

/**
 * Base class for controllers in this package.<br>
 * Introduces some utility methods
 */
public class BaseController {
  private static final String SUCCESS = "success";
  private static final String FAIL = "fail";
  private static final String ERROR = "error";

  /**
   * Successful response with message payload
   * @param message
   * @return
   */
  protected JsendResponse success(String message) {
    return new JsendResponse(SUCCESS, new Message(message));
  }

  /**
   * Successful response with data payload
   * @param data
   * @return
   */
  protected JsendResponse success(JsendData data) {
    return new JsendResponse(SUCCESS, data);
  }

  /**
   * Failure response
   * @param message
   * @return
   */
  protected JsendResponse fail(String message) {
    return new JsendResponse(FAIL, new Message(message));
  }

  /**
   * Internal error response
   * @param message
   * @return
   */
  protected JsendResponse error(String message) {
    return new JsendResponse(ERROR, message);
  }
}
