package com.airline.api;

import com.airline.api.utils.JsendResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public ResponseEntity<JsendResponse> defaultErrorHandler(Exception e) {
    final String message = Optional.ofNullable(e.getMessage()).orElse(e.getClass().getSimpleName());
    e.printStackTrace(System.err);
    return new ResponseEntity<>(new JsendResponse("error", message), HttpStatus.BAD_REQUEST);
  }
}
