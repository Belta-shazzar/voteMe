package com.shazzar.voteme.model.responsemodel.electionresponse;

import lombok.Data;

@Data
public class TokenResponse {
    private Long eventId;
    private String eventName;
    private String token;
}
