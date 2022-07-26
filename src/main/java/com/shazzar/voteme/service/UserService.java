package com.shazzar.voteme.service;

import com.shazzar.voteme.model.requestModel.userRequest.AdminRequest;
import com.shazzar.voteme.model.requestModel.userRequest.CandidateRequest;
import com.shazzar.voteme.model.requestModel.userRequest.UserRequest;
import com.shazzar.voteme.model.responseModel.userResponse.AdminResponse;
import com.shazzar.voteme.model.responseModel.userResponse.UserResponse;

public interface UserService {
    AdminResponse createAdminUser(AdminRequest request);

    UserResponse createCandidateUser(CandidateRequest request);

    UserResponse createUser(UserRequest userRequest);
}

