package com.shazzar.voteme.controller;

import com.shazzar.voteme.model.requestmodel.PositionRequest;
import com.shazzar.voteme.model.responsemodel.PositionResponse;
import com.shazzar.voteme.service.PositionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("voteMe/v1/position")
public class PositionController {
    
    private final PositionService positionService;


    @PreAuthorize("hasAuthority('position:write')")
    @PostMapping
    public ResponseEntity<PositionResponse> addPosition(@RequestBody PositionRequest request,
                                                        Authentication authentication) {
        PositionResponse position = positionService.addPosition(request, authentication.getName());
        
        return new ResponseEntity<>(position, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('position:read')")
    @GetMapping
    public ResponseEntity<Set<PositionResponse>> getAllPosition(Authentication authentication) {
        Set<PositionResponse> positionResponses = positionService.getAllPosition(authentication.getName());
        return new ResponseEntity<>(positionResponses, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('Role_ADMIN')")
    @PatchMapping
    public ResponseEntity<Set<PositionResponse>> setPositionHolders(Authentication authentication) {
        return new ResponseEntity<>(positionService.setPositionHolders(authentication.getName()), HttpStatus.OK);
    }
}
