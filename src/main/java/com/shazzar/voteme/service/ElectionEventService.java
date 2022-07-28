package com.shazzar.voteme.service;

import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.model.requestModel.ElectionDateSetRequest;
import com.shazzar.voteme.model.responseModel.ElectionEventResponse;
import com.shazzar.voteme.model.responseModel.TokenResponse;

public interface ElectionEventService {
    
    void createEvent(ElectionEvent event);
    TokenResponse getEventByToken(String token);
    ElectionEvent getEventById(Long eventId);
    ElectionEventResponse setCommenceAndEndDate(ElectionDateSetRequest dateSet);
}
