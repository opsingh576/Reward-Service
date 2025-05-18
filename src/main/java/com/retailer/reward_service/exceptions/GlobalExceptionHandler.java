package com.retailer.reward_service.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleCustomerNotFound(CustomerNotFoundException ex,WebRequest request) {
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTransactionAmountException.class)
    public ResponseEntity<ErrorDetails> handleCustomerNotFound(InvalidTransactionAmountException ex,WebRequest request) {
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }

   // @ExceptionHandler(MethodArgumentNotValidException.class)

    protected ResponseEntity<ErrorDetails> handleValidationExceptions(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                   HttpStatusCode statusCode, WebRequest request) {

        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
                Objects.requireNonNull(ex.getFieldError()).getDefaultMessage(),request.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorDetails errorDetails=new ErrorDetails(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
