package com.shazzar.voteme.service;

import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.model.requestModel.TokenRequest;
import com.shazzar.voteme.model.responseModel.ElectionEventResponse;
import com.shazzar.voteme.model.requestModel.ElectionEventRequest;

public interface ElectionEventService {
    
    ElectionEventResponse createEvent(ElectionEventRequest request) throws ResourceNotFoundException;
    ElectionEventResponse getEventByToken(TokenRequest tokenRequest);
    ElectionEvent getEventById(Long eventId);
}
