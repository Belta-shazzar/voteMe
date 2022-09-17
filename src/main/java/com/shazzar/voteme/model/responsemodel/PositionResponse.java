package com.shazzar.voteme.model.responsemodel;

import com.shazzar.voteme.entity.User;
import lombok.Data;

import java.util.Set;

@Data
public class PositionResponse {
    private String positionTitle;
    private String eventName;
    private String holderName;
    private Set<String> aspirantNames;
}
