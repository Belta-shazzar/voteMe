package com.shazzar.voteme.model.responsemodel;

import com.shazzar.voteme.entity.User;
import lombok.Data;

@Data
public class PositionResponse {
    private String positionTitle;
    private String eventName;
    private User holder;
}
