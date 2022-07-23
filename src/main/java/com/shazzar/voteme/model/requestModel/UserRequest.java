package com.shazzar.voteme.model.requestModel;

import com.shazzar.voteme.entity.role.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserRequest {
    private String fullName;
    private String email;
    private String password;
    private AppUserRole role;
    private Long electionId;
    private Long positionId;
}
