package com.shazzar.voteme.service.impl;

import com.shazzar.voteme.config.userauth.AppUserService;
import com.shazzar.voteme.entity.Candidate;
import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.exception.AlreadyExistException;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.model.Mapper;
import com.shazzar.voteme.model.requestmodel.PositionRequest;
import com.shazzar.voteme.model.responsemodel.PositionResponse;
import com.shazzar.voteme.repository.PositionRepository;
import com.shazzar.voteme.service.PositionService;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class PositionServiceImpl implements PositionService {
    
    private final PositionRepository positionRepository;
    private final ElectionEventServiceImpl service;
    private final UserServiceImpl userServiceForGetById;
    private final AppUserService userService;
    private static final String NOT_FOUND_ERROR_MSG = "%s with %s %s, not found";

    @Lazy
    public PositionServiceImpl(PositionRepository positionRepository, ElectionEventServiceImpl service, UserServiceImpl userServiceForGetById, AppUserService userService) {
        this.positionRepository = positionRepository;
        this.service = service;
        this.userServiceForGetById = userServiceForGetById;
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public Position getPositionById(Long positionId) {
        return positionRepository.findById(positionId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MSG, "Position", "id", positionId)));
    }

    @Override
    public void createPosition(Position position) {
        positionRepository.save(position);
    }

    @Override
    public PositionResponse addPosition(PositionRequest request) {
        checkPositionEvent(request);
        Position position = new Position();
        position.setPositionTitle(request.getPositionTitle());
        ElectionEvent event = service.getEventById(request.getEventId());
        position.setEvent(event);
        positionRepository.save(position);
        return Mapper.positionToPositionModel(position);
    }
    
    @SneakyThrows
    public void checkPositionEvent(PositionRequest request) {
        String positionTitle = request.getPositionTitle();
        Optional<Position> position = Optional.ofNullable(positionRepository.findByPositionTitle(positionTitle));
        if (position.isPresent()) {
            Long positionEventId = position.get().getEvent().getId();
            if (Objects.equals(positionEventId, request.getEventId())) {
                String errorMessage = "position with the title " + positionTitle +
                        " already exist in this event.";
                throw new AlreadyExistException(errorMessage);
            }
        }
    }

    @Override
    public Set<PositionResponse> getAllPosition() {
        List<Position> positions = positionRepository.findAll();
        return Mapper.positionToPositionModels(positions);
    }

    @SneakyThrows
    @Override
    public Set<PositionResponse> setPositionHolders(String name) {
        User user = userService.getUserByUsername(name);
        ElectionEvent event = user.getEvent();

        if (event.getEndDate().isBefore(LocalDateTime.now())) {
            for (Position position : event.getPositions()) {
                int highestCandidateVote = 0;
                Long winnerId = 0L;
                for (Candidate candidate : position.getAspirants()) {
                    if (candidate.getVoters().size() > highestCandidateVote) {
                        highestCandidateVote = candidate.getVoters().size();
                        winnerId = candidate.getId();
                    }
                }
                User positionWinner = userServiceForGetById.getById(winnerId);
                position.setHolder(positionWinner);
            }
            return Mapper.positionToPositionModels(event.getPositions());
        } else {
            throw new IllegalArgumentException("Election is yet to end");
        }

    }
}
