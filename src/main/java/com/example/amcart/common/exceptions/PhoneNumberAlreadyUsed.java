package com.example.amcart.common.exceptions;

public class PhoneNumberAlreadyUsed extends RuntimeException{

    public PhoneNumberAlreadyUsed(String phoneNumber){
        super("Phone number already used : "+phoneNumber);
    }
}
