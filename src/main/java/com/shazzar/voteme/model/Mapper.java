package com.shazzar.voteme.model;

import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.model.requestmodel.userrequest.AdminRequest;
import com.shazzar.voteme.model.requestmodel.userrequest.CandidateRequest;
import com.shazzar.voteme.model.requestmodel.userrequest.UserRequest;
import com.shazzar.voteme.model.responsemodel.electionresponse.ElectionEventResponse;
import com.shazzar.voteme.model.responsemodel.PositionResponse;
import com.shazzar.voteme.model.responsemodel.electionresponse.TokenResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.AdminResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.UserResponse;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    
       public static UserResponse user2UserModel(User user, String jwt) {
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setFullName(user.getFullName());
            response.setEmail(user.getEmail());
            response.setEventName(user.getEvent().getEventName());
            response.setJwtToken(jwt);
            return response;
       }

    public static AdminResponse admin2UserModel(User user, String jwt) {
        AdminResponse response = new AdminResponse();
        response.setFullName(user.getFullName());
        response.setEmail(user.getEmail());
        ElectionEvent event = user.getEvent();
        response.setEventName(event.getEventName());
        response.setEventToken(event.getToken());
        response.setJwtToken(jwt);
        return response;
    }
    
    public static TokenResponse eventToTokenModel(ElectionEvent event) {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setEventId(event.getId());
        tokenResponse.setToken(event.getToken());
        tokenResponse.setEventName(event.getEventName());
        return tokenResponse;
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
    
    public static Set<PositionResponse> positionToPositionModels(List<Position> positions) {
//        TODO: Convert to stream API later;
        Set<PositionResponse> positionSet = new HashSet<>();
        for (Position position : positions) {
            positionSet.add(positionToPositionModel(position));
        }
        return positionSet;
    }
}
