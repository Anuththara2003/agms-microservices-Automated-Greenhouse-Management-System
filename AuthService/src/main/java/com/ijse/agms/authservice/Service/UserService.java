package com.ijse.agms.authservice.Service;

import com.ijse.agms.authservice.Dto.UserDto;
import com.ijse.agms.authservice.Entity.User;

public interface UserService {
    User register(UserDto userDTO);
}