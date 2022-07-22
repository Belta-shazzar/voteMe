package com.shazzar.voteme.model;

import com.shazzar.voteme.entity.AppUser;
import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.model.requestModel.ElectionEventRequest;
import com.shazzar.voteme.model.requestModel.UserRequest;
import com.shazzar.voteme.model.responseModel.ElectionEventResponse;
import com.shazzar.voteme.model.responseModel.UserResponse;

public class Mapper {
    
    public static AppUser userModel2User(UserRequest userRequest) {
        return new AppUser(
                userRequest.getFullName(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getRole());
    }
    
       public static UserResponse user2UserModel(AppUser user) {
            return new UserResponse(
                    user.getFullName(),
                    user.getEmail(),
                    user.getRole(),
                    user.isEnabled()
            );
       }

    public static ElectionEvent eventModel2Event(ElectionEventRequest eventRequest) {
        return new ElectionEvent(eventRequest.getEventName());
    }

    public static ElectionEventResponse event2EventModel(ElectionEvent event) {
        ElectionEventResponse eventResponse = new ElectionEventResponse();
        eventResponse.setEventName(event.getEventName());
        eventResponse.setToken(event.getToken());
        eventResponse.setDateCreated(event.getDateCreated());
        eventResponse.setCommenceDate(event.getCommenceDate());
        eventResponse.setEndDate(event.getEndDate());
        return eventResponse;
    }
}
