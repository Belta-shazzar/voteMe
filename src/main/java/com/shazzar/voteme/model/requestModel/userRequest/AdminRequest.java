package com.shazzar.voteme.model.requestModel.userRequest;

import lombok.Data;

@Data
public class AdminRequest {
    private String fullName;
    private String email;
    private String password;
    private String eventName;
    private String positionTitle;
}
