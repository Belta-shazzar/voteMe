package com.shazzar.voteme.service;

import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.model.requestmodel.userrequest.*;
import com.shazzar.voteme.model.responsemodel.userresponse.UserActionResponse;

public interface UserService {
    UserActionResponse createAdminUser(AdminRequest request);

    UserActionResponse createCandidateUser(CandidateRequest request);

    UserActionResponse createUser(UserRequest userRequest);

    UserActionResponse switchCandidateToUser(RoleSwitchRequest switchRequest);

    UserActionResponse castVote(VoteRequest vote, String username);

    void deleteUser(User user);

}

