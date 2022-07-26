package com.shazzar.voteme.model.requestModel.userRequest;

import com.shazzar.voteme.entity.role.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserRequest {
    private String fullName;
    private String email;
    private String password;
    private Long electionId;
}
