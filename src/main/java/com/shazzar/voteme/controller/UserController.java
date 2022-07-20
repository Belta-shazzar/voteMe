package com.shazzar.voteme.controller;

import com.shazzar.voteme.entity.AppUserService;
import com.shazzar.voteme.model.requestModel.UserRequest;
import com.shazzar.voteme.model.responseModel.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("voteMe/v1/user")
@AllArgsConstructor
public class UserController {
    
    private AppUserService userService;
    
    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse user = userService.createUser(userRequest);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
