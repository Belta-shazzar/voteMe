package com.shazzar.voteme.model.requestmodel.electionrequest;

import lombok.Data;

@Data
public class ElectionDateSetRequest {
    private Long eventId;
    private String commenceDate;
    private String endDate;
    
}
