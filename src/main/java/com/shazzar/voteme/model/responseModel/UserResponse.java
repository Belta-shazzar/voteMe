package com.shazzar.voteme.model.responseModel;

import com.shazzar.voteme.entity.role.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponse {

    private String fullName;
    private String email;
    private AppUserRole role;
    private Boolean isEnabled;
}
