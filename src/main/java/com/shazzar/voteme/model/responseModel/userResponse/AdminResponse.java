package com.shazzar.voteme.model.responseModel.userResponse;

import lombok.Data;

@Data
public class AdminResponse {
    private String fullName;
    private String email;
    private String eventName;
    private String eventToken;
}
