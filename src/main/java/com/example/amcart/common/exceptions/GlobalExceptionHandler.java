package com.example.amcart.common.exceptions;

import com.example.amcart.responses.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private ResponseEntity<ErrorResponse> buildResponse(Exception ex,
                                                        HttpStatus status,
                                                        HttpServletRequest request){

        ErrorResponse response= ErrorResponse.builder()
                .timeStamp(LocalDateTime.now()).status(status.value())
                .error(status.getReasonPhrase()).message(ex.getMessage())
                .path(request.getRequestURI()).build();

        return new ResponseEntity<>(response,status);

    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmailExists(EmailAlreadyExistsException ex,
                                                           HttpServletRequest request){
        return buildResponse(ex, HttpStatus.CONFLICT,request);
    }

    @ExceptionHandler(PhoneNumberAlreadyUsed.class)
    public ResponseEntity<ErrorResponse> handlePhoneException(PhoneNumberAlreadyUsed ex,
                                                              HttpServletRequest request){
        return buildResponse(ex,HttpStatus.CONFLICT,request);
    }

    public ResponseEntity<?> handleValidExceptionErrors(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),
                                                                        error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
}
