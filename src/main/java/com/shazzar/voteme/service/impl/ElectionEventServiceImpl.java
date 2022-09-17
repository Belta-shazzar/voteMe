package com.shazzar.voteme.service.impl;

import com.shazzar.voteme.config.userauth.AppUserService;
import com.shazzar.voteme.entity.Candidate;
import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.model.Mapper;
import com.shazzar.voteme.model.requestmodel.electionrequest.ElectionDateSetRequest;
import com.shazzar.voteme.model.responsemodel.electionresponse.ElectionEventResponse;
import com.shazzar.voteme.model.responsemodel.electionresponse.ElectionResultResponse;
import com.shazzar.voteme.model.responsemodel.electionresponse.TokenResponse;
import com.shazzar.voteme.repository.ElectionEventRepo;
import com.shazzar.voteme.service.ElectionEventService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ElectionEventServiceImpl implements ElectionEventService {
    
    private final ElectionEventRepo eventRepo;
    private final AppUserService userService;
    private static final String NOT_FOUND_ERROR_MSG = "%s with %s %s, not found";

//    TODO: Check if commence date and end date are valid
    

    public ElectionEventServiceImpl(ElectionEventRepo eventRepo, AppUserService userService) {
        this.eventRepo = eventRepo;
        this.userService = userService;
    }

    @Override
    public void createEvent(ElectionEvent event) {
        event.setDateCreated(LocalDateTime.now());
        
        eventRepo.save(event);
    }

    @SneakyThrows
    @Override
    public TokenResponse getEventByToken(String token) {
        ElectionEvent event = eventRepo.findByToken(token).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MSG, "Event", "token", token)));
        return Mapper.eventToTokenModel(event);
    }

    @SneakyThrows
    @Override
    public ElectionEvent getEventById(Long eventId) {
        return eventRepo.findById(eventId).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MSG, "Event", "Id", eventId)));
    }

    @Override
    public ElectionEventResponse setCommenceAndEndDate(ElectionDateSetRequest dateSet) {
        ElectionEvent event = getEventById(dateSet.getEventId());
        LocalDateTime commenceDate = dateTimeFormat(dateSet.getCommenceDate());
        LocalDateTime endDate = dateTimeFormat(dateSet.getEndDate());
        if (commenceDate.isAfter(LocalDateTime.now()) && endDate.isAfter(commenceDate)) {
            event.setCommenceDate(commenceDate);
            event.setEndDate(endDate);
        } else {
            throw new IllegalArgumentException("Incorrect date and time");
        }

        eventRepo.save(event);
        return Mapper.event2EventModel(event);
    }

    @Override
    public ElectionResultResponse getElectionResult(String name) {
        User user = userService.getUserByUsername(name);

        if (user.getEvent().getEndDate().isBefore(LocalDateTime.now())) {
//        <PositionName, <CandidateName, No. of votes>
            Map<String, Map<String, Integer>> resultResponse = new HashMap<>();

            Set<Position> positions = user.getEvent().getPositions();

            for (Position position : positions) {
                Set<Candidate> candidates = position.getAspirants();
                Map<String, Integer> candidatesVote = new HashMap<>();
                for (Candidate candidate : candidates) {
                    candidatesVote.put(candidate.getCandidateFullName(), candidate.getVoters().size());
                }
                resultResponse.put(position.getPositionTitle(), candidatesVote);
            }

            return new ElectionResultResponse(user.getEvent().getEventName(), resultResponse);
        } else {
            throw new IllegalArgumentException("Election is yet to end");
        }
    }

    public LocalDateTime dateTimeFormat(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(dateTimeString, formatter);
    }
    
    public void checkDate(ElectionEvent event) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (dateTime.isBefore(event.getCommenceDate())) {
            throw new IllegalArgumentException("Not yet time for election");
        } else if (dateTime.isAfter(event.getEndDate())) {
            throw new IllegalArgumentException("Election has ended");
        }
    }
}
