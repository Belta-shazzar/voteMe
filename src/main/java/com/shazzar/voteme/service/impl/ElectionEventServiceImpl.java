package com.shazzar.voteme.service.impl;

import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.model.Mapper;
import com.shazzar.voteme.model.requestmodel.electionrequest.ElectionDateSetRequest;
import com.shazzar.voteme.model.responsemodel.electionresponse.ElectionEventResponse;
import com.shazzar.voteme.model.responsemodel.electionresponse.TokenResponse;
import com.shazzar.voteme.repository.ElectionEventRepo;
import com.shazzar.voteme.service.ElectionEventService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ElectionEventServiceImpl implements ElectionEventService {
    
    private final ElectionEventRepo eventRepo;
    private static final String NOT_FOUND_ERROR_MSG = "%s with %s %s, not found";
    

    public ElectionEventServiceImpl(ElectionEventRepo eventRepo) {
        this.eventRepo = eventRepo;
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
        String commenceDate = dateSet.getCommenceDate();
        String endDate = dateSet.getEndDate();
        event.setCommenceDate(dateTimeFormat(commenceDate));
        event.setEndDate(dateTimeFormat(endDate));
        eventRepo.save(event);
        return Mapper.event2EventModel(event);
    }

    public LocalDateTime dateTimeFormat(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return LocalDateTime.parse(dateTimeString, formatter);
    }
    
    public void checkDate(ElectionEvent event) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (dateTime.isBefore(event.getCommenceDate())) {
            throw new IllegalStateException("Not yet time for election");
        } else if (dateTime.isAfter(event.getEndDate())) {
            throw new IllegalStateException("Election has ended");
        }
    }
}
