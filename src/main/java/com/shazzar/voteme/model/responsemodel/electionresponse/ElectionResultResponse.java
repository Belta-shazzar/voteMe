package com.shazzar.voteme.model.responsemodel.electionresponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ElectionResultResponse {
    private String eventName;
    private Map<String, Map<String, Integer>> resultResponse;
}
