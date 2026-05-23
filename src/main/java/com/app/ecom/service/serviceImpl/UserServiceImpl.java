package com.app.ecom.service.serviceImpl;

import com.app.ecom.dto.AddressDto;
import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.app.ecom.model.Address;
import com.app.ecom.repository.UserRepository;
import com.app.ecom.model.User;
import com.app.ecom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserResponse> getUser(Long id){
        return userRepository.findById(id)
                .map(this::maptoUserResponse);

    }

    @Override
    public List<UserResponse> getAllUsers(){
        return userRepository.findAll().stream()
                .map(this::maptoUserResponse).
                collect(Collectors.toList());
    }

    @Override
    public void createUsers(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }


    @Override
    public boolean updatedUser(Long id, UserRequest updateUser){
        return userRepository.findById(id)
                .map(existingUser -> {updateUserFromRequest(existingUser, updateUser);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }


    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());

        if(userRequest.getAddress() != null){
            Address addressDto = new Address();
            addressDto.setStreet(userRequest.getAddress().getStreet());
            addressDto.setCity(userRequest.getAddress().getCity());
            addressDto.setState(userRequest.getAddress().getState());
            addressDto.setCountry(userRequest.getAddress().getCountry());
            addressDto.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(addressDto);
        }
    }

    private UserResponse maptoUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhone(user.getPhone());
        userResponse.setEmail(user.getEmail());
        userResponse.setUserRole(user.getUserRole());

        if(user.getAddress() != null){
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setCountry(user.getAddress().getCountry());
            addressDto.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(addressDto);
        }
        return userResponse;
    }
}
