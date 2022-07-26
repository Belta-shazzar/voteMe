package com.shazzar.voteme.service;

import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.model.requestModel.ElectionDateSetRequest;
import com.shazzar.voteme.model.requestModel.TokenRequest;
import com.shazzar.voteme.model.responseModel.ElectionEventResponse;

public interface ElectionEventService {
    
    void createEvent(ElectionEvent event);
    ElectionEvent getEventByToken(String token);
    ElectionEvent getEventById(Long eventId);
    ElectionEventResponse setCommenceAndEndDate(ElectionDateSetRequest dateSet);
}
