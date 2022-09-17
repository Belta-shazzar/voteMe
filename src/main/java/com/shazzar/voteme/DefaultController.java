package com.shazzar.voteme;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class DefaultController {

    @GetMapping
    public String voteMe() {
        return "VoteMe: Build completed!";
    }

}