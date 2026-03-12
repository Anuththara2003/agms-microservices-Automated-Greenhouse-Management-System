package com.ijse.agms.authservice.Controller;

import com.ijse.agms.authservice.Dto.ApiResponse;
import com.ijse.agms.authservice.Dto.UserDto;
import com.ijse.agms.authservice.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/auth"})
@CrossOrigin({"*"})
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> registerUser(@RequestBody UserDto userDTO) {

        return ResponseEntity.ok(
                new ApiResponse(
                        userService.register(userDTO),
                        "User registered successfully",
                        200
                )
        );
    }

}