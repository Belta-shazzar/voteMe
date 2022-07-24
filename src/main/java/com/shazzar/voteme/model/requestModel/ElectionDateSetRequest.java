package com.shazzar.voteme.model.requestModel;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ElectionDateSetRequest {
    private Long eventId;
    private String commenceDate;
    private String endDate;
    
}
