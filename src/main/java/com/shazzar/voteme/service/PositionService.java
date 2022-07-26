package com.shazzar.voteme.service;

import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.model.requestModel.PositionRequest;
import com.shazzar.voteme.model.responseModel.PositionResponse;

public interface PositionService {
    Position getPositionById(Long positionId);
    
    void createPosition(Position position);
    
    PositionResponse addPosition(PositionRequest request);
}
