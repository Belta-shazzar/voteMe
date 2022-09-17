package com.shazzar.voteme.model;

import com.shazzar.voteme.entity.Candidate;
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
import com.shazzar.voteme.model.responsemodel.userresponse.GetAllUserResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.UserResponse;

import java.util.Collection;
import java.util.HashSet;
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

    public static Set<GetAllUserResponse> getAllUserResponses(Set<User> users) {
        Set<GetAllUserResponse> userResponses = new HashSet<>();
        for (User user : users) {
            userResponses.add(new GetAllUserResponse(user.getFullName(), user.getEmail()));
        }
        return userResponses;
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
        if (position.getHolder() != null) {
            positionResponse.setHolderName(position.getHolder().getFullName());
        }
        Set<String> aspirantNames = new HashSet<>();
        for (Candidate candidate : position.getAspirants()) {
            aspirantNames.add(candidate.getCandidateFullName());
        }
        positionResponse.setAspirantNames(aspirantNames);
        return positionResponse;
    }
    
    public static Set<PositionResponse> positionToPositionModels(Collection<Position> positions) {
//        TODO: Convert to stream API later;
        Set<PositionResponse> positionSet = new HashSet<>();
        for (Position position : positions) {
            positionSet.add(positionToPositionModel(position));
        }
        return positionSet;
    }
}
