package com.shazzar.voteme.entity.email.confirmationtoken;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("voteMe/v1/confirmationToken")
public class ConfirmationTokenController {

    private final ConfirmationTokenService service;


    public ConfirmationTokenController(ConfirmationTokenService service) {
        this.service = service;
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirmToken(@RequestParam("token") String token) {
        return new ResponseEntity<>(service.confirmToken(token), HttpStatus.OK);
    }


}
