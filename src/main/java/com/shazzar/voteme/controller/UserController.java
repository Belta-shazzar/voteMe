package com.shazzar.voteme.controller;

import com.shazzar.voteme.model.requestmodel.userrequest.RoleSwitchRequest;
import com.shazzar.voteme.model.requestmodel.userrequest.VoteRequest;
import com.shazzar.voteme.model.responsemodel.userresponse.UserActionResponse;
import com.shazzar.voteme.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("voteMe/v1/user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('Role_ADMIN')")
    @PutMapping("/dismiss-candidate")
    public ResponseEntity<UserActionResponse> candidateToUser(@RequestBody RoleSwitchRequest switchRequest) {
        return new ResponseEntity<>(userService.switchCandidateToUser(switchRequest), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('Role_ADMIN', 'Role_CANDIDATE', 'Role_USER')")
    @PatchMapping("cast-vote")
    public ResponseEntity<UserActionResponse> castVote(@RequestBody VoteRequest vote) {
        return new ResponseEntity<>(userService.castVote(vote), HttpStatus.OK);
    }


}
