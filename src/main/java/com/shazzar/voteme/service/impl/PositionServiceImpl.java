package com.shazzar.voteme.service.impl;

import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.model.Mapper;
import com.shazzar.voteme.model.requestModel.PositionRequest;
import com.shazzar.voteme.model.responseModel.PositionResponse;
import com.shazzar.voteme.repository.PositionRepository;
import com.shazzar.voteme.service.PositionService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService {
    
    private final PositionRepository positionRepository;

    public PositionServiceImpl(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @SneakyThrows
    @Override
    public Position getPositionById(Long positionId) {
        return positionRepository.findById(positionId).orElseThrow(() ->
                new ResourceNotFoundException("Position", "id", positionId));
    }

    @Override
    public void createPosition(Position position) {
        positionRepository.save(position);
    }

    @Override
    public PositionResponse addPosition(PositionRequest request) {
        return null;
    }
}
