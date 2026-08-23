package com.server.common.api.exceptions;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

import com.mongodb.MongoException;
import com.server.common.api.exceptions.api.data.json.runtime.ExternalApiResponseMappingException;
import com.server.common.api.exceptions.mongo.ResourceNotFoundException;
import com.server.common.api.exceptions.validation.cart.CartValidationException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalControllersExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalControllersExceptionHandler.class);

  private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message, Exception ex, Object data){
    logger.error("Error intercepted: {} - Details: {}", message, ex.getMessage());
    
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", Instant.now().toString());
    body.put("status", status.value());
    body.put("error", status.getReasonPhrase());
    body.put("message", message);
    body.put("data", data);

    return new ResponseEntity<>(body, status);
  }

  // =========================================================================
  // 1. MONGODB (HIGH LEVEL ERRORS)
  // =========================================================================

  @ExceptionHandler(DuplicateKeyException.class)
  public ResponseEntity<?> handleDuplicateKeyException(DuplicateKeyException ex) {
    return buildResponse(HttpStatus.CONFLICT, "This entry already exists in the database (duplicate key)", ex, null);
  }

  @ExceptionHandler(MongoException.class)
  public ResponseEntity<?> handleMongoException(MongoException ex) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error while working with MongoDB database !", ex, null);
  }

  // =========================================================================
  // 2. PARAMETERS AND VALIDATION OF INCOMING DATA IN REST CONTROLLERS
  // =========================================================================

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<Map<String, Object>> handleMissingParams(MissingServletRequestParameterException ex){
    return buildResponse(HttpStatus.BAD_REQUEST, String.format("Missing required parameter: \"%s\"", ex.getParameterName()), ex, ex.getParameterName());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException ex){
    String details = ex.getConstraintViolations().stream()
    .map(err -> err.getMessage())
    .collect(Collectors.joining("; "));

    return buildResponse(HttpStatus.BAD_REQUEST, details, ex, null);
  }

    // =========================================================================
    // 3. JSON PARCING AND VALIDATION
    // =========================================================================

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
    return buildResponse(HttpStatus.BAD_REQUEST, "Invalid JSON format. Failed to parse the request body !", ex, null);
  }

  @ExceptionHandler(HttpStatusCodeException.class)
  public ResponseEntity<?> handleRestClientResponseException(HttpStatusCodeException ex) {
    String responseBody = ex.getResponseBodyAsString();
    String message = String.format("External API returned an error (Status: %s). Body: %s", ex.getStatusCode(), responseBody);
        
    return buildResponse(HttpStatus.BAD_GATEWAY, message, ex, ex.getResponseBodyAsString()); 
  }

  @ExceptionHandler(ResourceAccessException.class)
  public ResponseEntity<?> handleNetworkTimeoutException(ResourceAccessException ex) {
    return buildResponse(HttpStatus.GATEWAY_TIMEOUT, "Error connecting to external resource or network timeout !", ex, null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
    logger.error("Validation failed: {}", ex.getMessage());
    
    Map<String, Object> body = new HashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", "Validation Failed");
    
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> 
      errors.put(error.getField(), error.getDefaultMessage())
    );
    body.put("details", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ExternalApiResponseMappingException.class)
  public ResponseEntity<?> externalApiResponseMappingException(ExternalApiResponseMappingException ex){
    return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), ex, null);
  }

  // =========================================================================
  // 4. CART VALIDATION
  // =========================================================================

  @ExceptionHandler(CartValidationException.class)
  public ResponseEntity<?> cartValidationException(CartValidationException ex){
    return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "cart validation.", ex, ex.getConflicts());
  }

  // =========================================================================
  // 5. NULL OR NOT FOUND
  // =========================================================================

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex){
    return buildResponse(HttpStatus.NOT_FOUND, "resource not found.", ex, null);
  }
    
  // =========================================================================
  // 6. FINAL BACKUP CATCH (Catch-All)
  // =========================================================================

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleAllUncaughtExceptions(Exception ex) {
    return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected internal server error occurred !", ex, null);
  }


}