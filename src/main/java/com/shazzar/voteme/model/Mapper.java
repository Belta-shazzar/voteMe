package com.shazzar.voteme.model;

import com.shazzar.voteme.entity.AppUser;
import com.shazzar.voteme.model.requestModel.UserRequest;
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
}
