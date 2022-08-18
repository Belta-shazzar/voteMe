package com.shazzar.voteme.controller;

import com.shazzar.voteme.model.requestmodel.userrequest.RoleSwitchRequest;
import com.shazzar.voteme.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("voteMe/v1/user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('Role_ADMIN')")
    @PostMapping
    public ResponseEntity<String> candidateToUser(@RequestBody RoleSwitchRequest switchRequest) {
        String candidateName = userService.switchCandidateToUser(switchRequest);
        return new ResponseEntity<>(candidateName + " has been removed from candidate list", HttpStatus.OK);
    }

}
