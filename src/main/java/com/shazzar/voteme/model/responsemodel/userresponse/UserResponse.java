package com.shazzar.voteme.model.responsemodel.userresponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String email;
    private String eventName;
    private String jwtToken;
}
