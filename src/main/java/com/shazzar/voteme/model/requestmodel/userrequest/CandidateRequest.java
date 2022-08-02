package com.shazzar.voteme.model.requestmodel.userrequest;

import lombok.Data;

@Data
public class CandidateRequest {
    private String fullName;
    private String email;
    private String password;
    private Long electionId;
    private Long positionId;
}
