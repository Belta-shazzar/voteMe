package com.shazzar.voteme.model.requestmodel.userrequest;

import lombok.Data;

@Data
public class RoleSwitchRequest {
    private Long electionId;
    private Long candidateId;
}
