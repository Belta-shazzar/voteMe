package com.shazzar.voteme.service;

import com.shazzar.voteme.model.requestmodel.userrequest.AdminRequest;
import com.shazzar.voteme.model.requestmodel.userrequest.CandidateRequest;
import com.shazzar.voteme.model.requestmodel.userrequest.RoleSwitchRequest;
import com.shazzar.voteme.model.requestmodel.userrequest.UserRequest;
import com.shazzar.voteme.model.responsemodel.userresponse.AdminResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.UserResponse;

public interface UserService {
    AdminResponse createAdminUser(AdminRequest request);

    UserResponse createCandidateUser(CandidateRequest request);

    UserResponse createUser(UserRequest userRequest);

    String switchCandidateToUser(RoleSwitchRequest switchRequest);
}

