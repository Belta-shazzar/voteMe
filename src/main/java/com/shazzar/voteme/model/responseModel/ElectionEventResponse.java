package com.shazzar.voteme.model.responseModel;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ElectionEventResponse {
    private String eventName;
    private String token;
    private LocalDateTime dateCreated;
    private LocalDateTime commenceDate;
    private LocalDateTime endDate;
}
