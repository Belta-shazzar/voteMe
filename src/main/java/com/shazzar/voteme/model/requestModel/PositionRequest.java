package com.shazzar.voteme.model.requestModel;

import lombok.Data;

@Data
public class PositionRequest {
    private String positionTitle;
    private Long eventId;
}
