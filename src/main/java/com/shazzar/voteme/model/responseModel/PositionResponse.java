package com.shazzar.voteme.model.responseModel;

import com.shazzar.voteme.entity.AppUser;
import com.shazzar.voteme.entity.ElectionEvent;
import lombok.Data;

@Data
public class PositionResponse {
    private String positionTitle;
    private String eventName;
    private AppUser holder;
}
