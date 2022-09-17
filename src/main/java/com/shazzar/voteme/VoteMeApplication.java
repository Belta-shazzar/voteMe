package com.shazzar.voteme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoteMeApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoteMeApplication.class, args);
    }
// TODO: complete CRUD endpoints, Implement download feature, Check election result, Set election winner
//    Also add entity id to all response and Mapper
//    Remove email ui from logging to the console

}
