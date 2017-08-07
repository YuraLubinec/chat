package com.oblenergo.chat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GeneralController {

  private final static String DEFAULTFAILUREMESSAGE = "Сервіс недоступний, спробуйте пізніше";
  private final static String BADREQUESTMESSAGE = "Не вірний формат запиту";
  private final static String METHODNOTALLOWED = "Метод не підтримуєтсья";
  private static final String FALLBACKOPTION = "Помилка валідації";
  private static final int FIRST_FIELD_NOT_PASSED_VALIDATION = 0;

  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String validationErrorHandler(MethodArgumentNotValidException ex) {
   
    FieldError error = ex.getBindingResult().getFieldErrors().get(FIRST_FIELD_NOT_PASSED_VALIDATION);
    return messageSource.getMessage(error.getCode() + "." + error.getObjectName() + "." + error.getField(), null, FALLBACKOPTION, null);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  @ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED, reason = METHODNOTALLOWED)
  public void errorMethodNotSupported(Exception e) {

    log.error("Method isn`t supported", e);
    throw new RuntimeException();
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = BADREQUESTMESSAGE)
  public void errorHttpMessageNotReadable(HttpMessageNotReadableException e) {

    log.error("Error in http request syntax", e);
    throw e;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = DEFAULTFAILUREMESSAGE)
  public void error500Default(Exception e) {

    log.error("Server error occurred", e);
    throw new RuntimeException(e.getMessage());
  }

}
