package com.example.amcart.user.service;


import com.example.amcart.common.exceptions.EmailAlreadyExistsException;
import com.example.amcart.common.exceptions.PhoneNumberAlreadyUsed;
import com.example.amcart.common.exceptions.ResourceNotFoundException;
import com.example.amcart.user.dto.RegisterRequest;
import com.example.amcart.responses.UserResponse;
import com.example.amcart.user.entity.Address;
import com.example.amcart.user.entity.Role;
import com.example.amcart.user.entity.User;
import com.example.amcart.user.mapper.UserMapper;
import com.example.amcart.user.repository.RoleRepository;
import com.example.amcart.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // removed constructor and using args Constructor
//    public USerServiceImpl(UserRepository userRepository,AddressRepository addressRepository,
//                           RoleRepository roleRepository) {
//        this.userRepository = userRepository;
//        this.addressRepository=addressRepository;
//        this.roleRepository=roleRepository;
//    }
    @Override
    public UserResponse register(RegisterRequest request) {
        // check if any user exists with email and phone
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(request.getEmail());
        }
        if(userRepository.existsByPhoneNumber(request.getPhoneNumber())){
            throw new PhoneNumberAlreadyUsed(request.getPhoneNumber());
        }
        //checking if customer role in db is present and give user as customer
        Role userRole= roleRepository.findByName("ROLE_CUSTOMER").orElseThrow(()->
                new RuntimeException("Default role not found"));
        // setting user details
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setNonLocked(true);
        //using password encrypt before storing
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(userRole));
        // if address provided set address
        if(request.getAddresses()!=null){
            List<Address> addresses = request.getAddresses().stream().map(a-> {
                Address address = new Address();
                address.setDoorNo(a.getDoorNo());
                address.setStreet(a.getStreet());
                address.setCity(a.getCity());
                address.setState(a.getState());
                address.setPostalCode(a.getPostalCode());
                address.setUser(user);
                return address;
                    }).toList();
            user.setAddresses(addresses);
        }
        User savedUser = userRepository.save(user);
        // return as response after mapping
       return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponse getCurrentUser() {
        String email= SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email).
                orElseThrow(()-> new ResourceNotFoundException("can't access others data"));
        return userMapper.toUserResponse(user);
    }
}
