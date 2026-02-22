package com.example.amcart.user.mapper;

import com.example.amcart.responses.AddressResponse;
import com.example.amcart.responses.UserResponse;
import com.example.amcart.user.entity.Address;
import com.example.amcart.user.entity.Role;
import com.example.amcart.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class UserMapper {
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(
                        user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet())
                )
                .addresses(
                        user.getAddresses()
                                .stream()
                                .map(this::toAddressResponse)
                                .toList()
                )
                .build();
    }

    private AddressResponse toAddressResponse(Address a) {
        return AddressResponse.builder()
                .id(a.getId())
                .doorNo(a.getDoorNo())
                .street(a.getStreet())
                .city(a.getCity())
                .state(a.getState())
                .postalCode(a.getPostalCode())
                .build();
    }
}

