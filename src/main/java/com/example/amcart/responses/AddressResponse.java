package com.example.amcart.responses;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressResponse {
    private Long id;
    private String doorNo;
    private String street;
    private String city;
    private String state;
    private String postalCode;
}
