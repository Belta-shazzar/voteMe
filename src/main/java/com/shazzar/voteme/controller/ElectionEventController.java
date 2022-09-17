package com.shazzar.voteme.controller;

import com.shazzar.voteme.model.requestmodel.electionrequest.ElectionDateSetRequest;
import com.shazzar.voteme.model.requestmodel.electionrequest.TokenRequest;
import com.shazzar.voteme.model.responsemodel.electionresponse.ElectionEventResponse;
import com.shazzar.voteme.model.responsemodel.electionresponse.ElectionResultResponse;
import com.shazzar.voteme.model.responsemodel.electionresponse.TokenResponse;
import com.shazzar.voteme.service.ElectionEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("voteMe/v1/event")
public class ElectionEventController {
    
    private ElectionEventService eventService;
    
    @GetMapping
    public ResponseEntity<TokenResponse> getEventByToken(@RequestBody TokenRequest token) {
        TokenResponse event = eventService.getEventByToken(token.getToken());
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('event:write')")
    @PutMapping
    public ResponseEntity<ElectionEventResponse> setCommenceAndEndDate(@RequestBody ElectionDateSetRequest dateSet) {
        ElectionEventResponse event = eventService.setCommenceAndEndDate(dateSet);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('Role_ADMIN', 'Role_CANDIDATE', 'Role_USER')")
    @GetMapping("/result")
    public ResponseEntity<ElectionResultResponse> getElectionResult(Authentication authentication) {
        return new ResponseEntity<>(eventService.getElectionResult(authentication.getName()), HttpStatus.OK);
    }
}
