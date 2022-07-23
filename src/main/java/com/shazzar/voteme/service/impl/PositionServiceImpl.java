package com.shazzar.voteme.service.impl;

import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.model.Mapper;
import com.shazzar.voteme.model.requestModel.PositionRequest;
import com.shazzar.voteme.model.responseModel.PositionResponse;
import com.shazzar.voteme.repository.PositionRepository;
import com.shazzar.voteme.service.ElectionEventService;
import com.shazzar.voteme.service.PositionService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
public class PositionServiceImpl implements PositionService {
    
    private final ElectionEventService eventService;
    private final PositionRepository positionRepository;

    public PositionServiceImpl(ElectionEventService eventService, PositionRepository positionRepository) {
        this.eventService = eventService;
        this.positionRepository = positionRepository;
    }

    @Override
    public PositionResponse createPosition(PositionRequest request) {
        ElectionEvent event = eventService.getEventById(request.getEventId());
        Position position = new Position(request.getPositionTitle(), event);
        positionRepository.save(position);
        return Mapper.positionToPositionModel(position);
    }

    @SneakyThrows
    @Override
    public Position getPositionById(Long positionId) {
        return positionRepository.findById(positionId).orElseThrow(() ->
                new ResourceNotFoundException("Position", "id", positionId));
    }
}
