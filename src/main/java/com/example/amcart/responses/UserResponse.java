package com.example.amcart.responses;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@Builder
public class UserResponse {
    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String phoneNumber;

    private Set<String> roles;

    private List<AddressResponse> addresses;
}
