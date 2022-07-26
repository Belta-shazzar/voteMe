package com.shazzar.voteme.model.responseModel;

import com.shazzar.voteme.entity.User;
import lombok.Data;

@Data
public class PositionResponse {
    private String positionTitle;
    private String eventName;
    private User holder;
}
