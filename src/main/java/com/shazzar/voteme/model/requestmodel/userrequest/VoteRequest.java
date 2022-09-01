package com.shazzar.voteme.model.requestmodel.userrequest;

import lombok.Data;

import java.util.Map;

@Data
public class VoteRequest {
    private Long eventId;
    private Long userId;
    private Map<String, Long> voteMap; //positionTitle, candidateId
}
