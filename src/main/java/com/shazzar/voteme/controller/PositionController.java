package com.shazzar.voteme.controller;

import com.shazzar.voteme.model.requestmodel.PositionRequest;
import com.shazzar.voteme.model.responsemodel.PositionResponse;
import com.shazzar.voteme.service.PositionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("voteMe/v1/position")
public class PositionController {
    
    private final PositionService positionService;


    @PostMapping
    public ResponseEntity<PositionResponse> addPosition(@RequestBody PositionRequest request) {
        PositionResponse position = positionService.addPosition(request);
        
        return new ResponseEntity<>(position, HttpStatus.CREATED);
    }
    

    @GetMapping
    public ResponseEntity<Set<PositionResponse>> getAllPosition() {
        Set<PositionResponse> positionResponses = positionService.getAllPosition();
        return new ResponseEntity<>(positionResponses, HttpStatus.OK);
    }
}
