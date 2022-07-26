package com.shazzar.voteme.model.responseModel.userResponse;

import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.entity.role.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private String fullName;
    private String email;
    private String eventName;
}
