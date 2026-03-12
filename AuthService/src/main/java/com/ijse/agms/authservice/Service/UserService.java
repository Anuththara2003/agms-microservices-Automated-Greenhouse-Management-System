package com.ijse.agms.authservice.Service;

import com.ijse.agms.authservice.Dto.UserDto;
import com.ijse.agms.authservice.Entity.User;

import java.util.Map;

public interface UserService {
    User register(UserDto userDTO);
    Map<String, String> login(UserDto userDTO);

    String refreshAccessToken(String refreshToken);
}