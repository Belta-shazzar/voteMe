package com.shazzar.voteme.service;

import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.model.requestmodel.userrequest.*;
import com.shazzar.voteme.model.responsemodel.userresponse.GetAllUserResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.UserActionResponse;

import java.util.Set;

public interface UserService {
    UserActionResponse createAdminUser(AdminRequest request);

    UserActionResponse createCandidateUser(CandidateRequest request);

    UserActionResponse createUser(UserRequest userRequest);

    UserActionResponse switchCandidateToUser(Long candidateId, String userName);

    UserActionResponse castVote(VoteRequest vote, String username);

    void deleteUserWithExpiredToken(User user);

    Set<GetAllUserResponse> getUsers(String userName);

    void deleteUser(String name);
}

