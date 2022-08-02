package com.shazzar.voteme.controller;

import com.shazzar.voteme.model.responsemodel.userresponse.AdminResponse;
import com.shazzar.voteme.service.UserService;
import com.shazzar.voteme.model.requestmodel.userrequest.AdminRequest;
import com.shazzar.voteme.model.requestmodel.userrequest.CandidateRequest;
import com.shazzar.voteme.model.requestmodel.userrequest.UserRequest;
import com.shazzar.voteme.model.responsemodel.userresponse.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("voteMe/v1/register")
@AllArgsConstructor
public class RegistrationController {
    
    private UserService userService;
    
    @PostMapping("/admin")
    public ResponseEntity<AdminResponse> createAdminUser(@RequestBody @Valid AdminRequest request) {
        AdminResponse user = userService.createAdminUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/candidate")
    public ResponseEntity<UserResponse> createCandidateUser(@RequestBody @Valid CandidateRequest request) {
        UserResponse user = userService.createCandidateUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> createCasualUser(@RequestBody @Valid UserRequest userRequest) {
        UserResponse user = userService.createUser(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
