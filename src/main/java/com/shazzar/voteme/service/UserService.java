package com.shazzar.voteme.service;

import com.shazzar.voteme.model.requestmodel.userrequest.*;
import com.shazzar.voteme.model.responsemodel.userresponse.AdminResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.UserActionResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.UserResponse;

public interface UserService {
    AdminResponse createAdminUser(AdminRequest request);

    UserResponse createCandidateUser(CandidateRequest request);

    UserResponse createUser(UserRequest userRequest);

    UserActionResponse switchCandidateToUser(RoleSwitchRequest switchRequest);

    UserActionResponse castVote(VoteRequest vote, String username);
}

