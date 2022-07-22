package com.shazzar.voteme.entity;

import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.model.Mapper;
import com.shazzar.voteme.model.requestModel.UserRequest;
import com.shazzar.voteme.model.responseModel.UserResponse;
import com.shazzar.voteme.repository.UserRepository;
import com.shazzar.voteme.service.impl.ElectionEventServiceImpl;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG = "user with %s %s not found";
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ElectionEventServiceImpl eventService;

    @Lazy
    public AppUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ElectionEventServiceImpl eventService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eventService = eventService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, "email", email)));
    }
    
    public UserResponse createUser(UserRequest userRequest) {
        AppUser user = Mapper.userModel2User(userRequest);
        if (!user.getRole().name().equals("ADMIN") && user.getEvent() == null) {
            if (userRequest.getElectionId() != null) {
                ElectionEvent event = eventService.getEventById(userRequest.getElectionId());
                user.setEvent(event);
            } else {
                throw new IllegalStateException("Non admin user must have an event token");
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return Mapper.user2UserModel(user);
    }
    
    public AppUser getById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User", "id", id));
    }
    
    @SneakyThrows
    public void updateUserEvent(Long userId, ElectionEvent event) {
        AppUser user = getById(userId);
        if (user.getEvent() != null) {
            throw new IllegalStateException("This user already created an event");
        }
        user.setEvent(event);
        userRepository.save(user);
    }
}
