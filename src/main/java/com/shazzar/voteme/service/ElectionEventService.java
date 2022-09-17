package com.shazzar.voteme.service;

import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.model.requestmodel.ElectionDateSetRequest;
import com.shazzar.voteme.model.responsemodel.electionresponse.ElectionEventResponse;
import com.shazzar.voteme.model.responsemodel.electionresponse.ElectionResultResponse;
import com.shazzar.voteme.model.responsemodel.electionresponse.TokenResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.UserActionResponse;

public interface ElectionEventService {
    
    void createEvent(ElectionEvent event);
    TokenResponse getEventByToken(String token);
    ElectionEvent getEventById(Long eventId);
    ElectionEventResponse setCommenceAndEndDate(ElectionDateSetRequest dateSet, String userName);

    ElectionResultResponse getElectionResult(String name);

    void deleteEvent(String name);
}
