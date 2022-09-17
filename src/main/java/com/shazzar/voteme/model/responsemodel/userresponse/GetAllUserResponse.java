package com.shazzar.voteme.model.responsemodel.userresponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetAllUserResponse {
    private String fullName;
    private String email;
}
