package com.shazzar.voteme.model;

import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.model.requestModel.ElectionEventRequest;
import com.shazzar.voteme.model.requestModel.userRequest.AdminRequest;
import com.shazzar.voteme.model.requestModel.userRequest.CandidateRequest;
import com.shazzar.voteme.model.requestModel.userRequest.UserRequest;
import com.shazzar.voteme.model.responseModel.ElectionEventResponse;
import com.shazzar.voteme.model.responseModel.PositionResponse;
import com.shazzar.voteme.model.responseModel.userResponse.AdminResponse;
import com.shazzar.voteme.model.responseModel.userResponse.UserResponse;

public class Mapper {

    
    public static User userModel2User(AdminRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static User userModel2User(CandidateRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }

    public static User userModel2User(UserRequest request) {
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        return user;
    }
    
       public static UserResponse user2UserModel(User user) {
            UserResponse response = new UserResponse();
            response.setFullName(user.getFullName());
            response.setEmail(user.getEmail());
            response.setEventName(user.getEvent().getEventName());
            return response;
       }

    public static AdminResponse admin2UserModel(User user) {
        AdminResponse response = new AdminResponse();
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        ElectionEvent event = user.getEvent();
        response.setEventName(event.getEventName());
        response.setEventToken(event.getToken());
        return response;
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
    
    public static PositionResponse positionToPositionModel(Position position) {
        PositionResponse positionResponse = new PositionResponse();
        positionResponse.setPositionTitle(position.getPositionTitle());
        positionResponse.setEventName(position.getEvent().getEventName());
        return positionResponse;
    }
}
