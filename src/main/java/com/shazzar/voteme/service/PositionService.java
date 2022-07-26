package com.shazzar.voteme.service;

import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.model.requestModel.PositionRequest;
import com.shazzar.voteme.model.responseModel.PositionResponse;

import java.util.Set;

public interface PositionService {
    Position getPositionById(Long positionId);
    
    void createPosition(Position position);
    
    PositionResponse addPosition(PositionRequest request);
    
    Set<PositionResponse> getAllPosition();
}
