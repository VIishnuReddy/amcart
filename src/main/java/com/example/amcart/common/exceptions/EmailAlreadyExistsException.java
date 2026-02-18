package com.example.amcart.common.exceptions;

public class EmailAlreadyExistsException extends RuntimeException{
    public EmailAlreadyExistsException(String email){
        super("Email already registered : "+email);
    }
}
