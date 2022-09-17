package com.shazzar.voteme.controller;

import com.shazzar.voteme.model.requestmodel.userrequest.VoteRequest;
import com.shazzar.voteme.model.responsemodel.userresponse.GetAllUserResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.UserActionResponse;
import com.shazzar.voteme.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("voteMe/v1/user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('Role_ADMIN')")
    @PutMapping("/{candidateId}")
    public ResponseEntity<UserActionResponse> candidateToUser(@PathVariable("candidateId") Long candidateId,
                                                              Authentication authentication) {
        return new ResponseEntity<>(userService.switchCandidateToUser(candidateId, authentication.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('Role_ADMIN', 'Role_CANDIDATE', 'Role_USER')")
    @PatchMapping("cast-vote")
    public ResponseEntity<UserActionResponse> castVote(@RequestBody VoteRequest vote, Authentication authentication) {
        UserActionResponse response = userService.castVote(vote, authentication.getName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Role_ADMIN')")
    @GetMapping
    public ResponseEntity<Set<GetAllUserResponse>> getAllUser(Authentication authentication) {
        return new ResponseEntity<>(userService.getUsers(authentication.getName()), HttpStatus.OK);
    }


}
