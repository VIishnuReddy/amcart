package com.example.amcart.common.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String message){
        super("Resource not found "+message);
    }
}
