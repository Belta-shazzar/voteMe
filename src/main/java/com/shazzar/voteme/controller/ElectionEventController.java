package com.shazzar.voteme.controller;

import com.shazzar.voteme.model.requestModel.ElectionDateSetRequest;
import com.shazzar.voteme.model.requestModel.TokenRequest;
import com.shazzar.voteme.model.responseModel.ElectionEventResponse;
import com.shazzar.voteme.model.responseModel.TokenResponse;
import com.shazzar.voteme.service.ElectionEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("voteMe/v1/event")
public class ElectionEventController {
    
    private ElectionEventService eventService;
    
    @PreAuthorize("hasAnyRole('CANDIDATE', 'USER')")
    @GetMapping
    public ResponseEntity<TokenResponse> getEventByToken(@RequestBody TokenRequest tokenRequest) {
        TokenResponse event = eventService.getEventByToken(tokenRequest.getToken());
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('event:write')")
    @PutMapping
    public ResponseEntity<ElectionEventResponse> setCommenceAndEndDate(@RequestBody ElectionDateSetRequest dateSet) {
        ElectionEventResponse event = eventService.setCommenceAndEndDate(dateSet);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }
}
