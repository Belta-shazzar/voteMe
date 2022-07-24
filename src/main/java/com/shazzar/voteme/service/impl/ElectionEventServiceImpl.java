package com.shazzar.voteme.service.impl;

import com.shazzar.voteme.entity.AppUserService;
import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.model.Mapper;
import com.shazzar.voteme.model.requestModel.ElectionDateSetRequest;
import com.shazzar.voteme.model.requestModel.TokenRequest;
import com.shazzar.voteme.model.responseModel.ElectionEventResponse;
import com.shazzar.voteme.model.requestModel.ElectionEventRequest;
import com.shazzar.voteme.repository.ElectionEventRepo;
import com.shazzar.voteme.service.ElectionEventService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class ElectionEventServiceImpl implements ElectionEventService {
    
    private final ElectionEventRepo eventRepo;
    
    private final AppUserService userService;

    public ElectionEventServiceImpl(ElectionEventRepo eventRepo, AppUserService userService) {
        this.eventRepo = eventRepo;
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public ElectionEventResponse createEvent(ElectionEventRequest request) {
        ElectionEvent event = Mapper.eventModel2Event(request);
        String token = UUID.randomUUID().toString();
        event.setToken(token);
        event.setDateCreated(LocalDateTime.now());
        userService.updateUserEvent(request.getUserId(), event);
        
//        eventRepo.save(event);
        
        return Mapper.event2EventModel(event);
    }

    @SneakyThrows
    @Override
    public ElectionEventResponse getEventByToken(TokenRequest tokenRequest) {
        ElectionEvent event = eventRepo.findByToken(tokenRequest.getToken()).orElseThrow(() ->
                new ResourceNotFoundException("Event", "token", tokenRequest));
        return Mapper.event2EventModel(event);
    }

    @SneakyThrows
    @Override
    public ElectionEvent getEventById(Long eventId) {
        return eventRepo.findById(eventId).orElseThrow(() ->
                new ResourceNotFoundException("Event", "Id", eventId));
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
}
