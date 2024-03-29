package com.shazzar.voteme.controller;

import com.shazzar.voteme.config.userauth.AppUserService;
import com.shazzar.voteme.model.requestmodel.userrequest.SignInRequest;
import com.shazzar.voteme.model.responsemodel.userresponse.AdminResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.UserActionResponse;
import com.shazzar.voteme.service.UserService;
import com.shazzar.voteme.model.requestmodel.userrequest.AdminRequest;
import com.shazzar.voteme.model.requestmodel.userrequest.CandidateRequest;
import com.shazzar.voteme.model.requestmodel.userrequest.UserRequest;
import com.shazzar.voteme.model.responsemodel.userresponse.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("voteMe/v1/register")
@AllArgsConstructor
public class RegistrationController {
    
    private final UserService userService;
    private final AppUserService authenticationService;
    
    @PostMapping("/admin")
    public ResponseEntity<UserActionResponse> createAdminUser(@RequestBody @Valid AdminRequest request) {
        UserActionResponse user = userService.createAdminUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/candidate")
    public ResponseEntity<UserActionResponse> createCandidateUser(@RequestBody @Valid CandidateRequest request) {
        UserActionResponse user = userService.createCandidateUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/user")
    public ResponseEntity<UserActionResponse> createCasualUser(@RequestBody @Valid UserRequest userRequest) {
        UserActionResponse user = userService.createUser(userRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PostMapping("/authentication")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest userRequest, HttpServletRequest request) {
        return new ResponseEntity<>(authenticationService.authenticateUser(userRequest, request), HttpStatus.OK);
    }
}
