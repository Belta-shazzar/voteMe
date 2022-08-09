package com.shazzar.voteme.model.requestmodel.userrequest;

import lombok.Data;

@Data
public class SignInRequest {
    private String username;
    private String password;
}
