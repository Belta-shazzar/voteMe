package com.shazzar.voteme.model.requestModel.userRequest;

import lombok.Data;

@Data
public class CandidateRequest {
    private String fullName;
    private String email;
    private String password;
    private Long electionId;
    private Long positionId;
}
