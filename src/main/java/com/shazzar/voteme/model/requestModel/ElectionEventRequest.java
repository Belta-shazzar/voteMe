package com.shazzar.voteme.model.requestModel;

import lombok.Data;

@Data
public class ElectionEventRequest {
    private String eventName;
    private Long userId;
}
