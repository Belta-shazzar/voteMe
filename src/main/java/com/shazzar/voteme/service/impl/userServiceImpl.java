package com.shazzar.voteme.service.impl;

import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.entity.role.AppUserRole;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.model.Mapper;
import com.shazzar.voteme.model.requestModel.userRequest.AdminRequest;
import com.shazzar.voteme.model.requestModel.userRequest.CandidateRequest;
import com.shazzar.voteme.model.requestModel.userRequest.UserRequest;
import com.shazzar.voteme.model.responseModel.userResponse.AdminResponse;
import com.shazzar.voteme.model.responseModel.userResponse.UserResponse;
import com.shazzar.voteme.repository.UserRepository;
import com.shazzar.voteme.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class userServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ElectionEventServiceImpl eEventService;
    private final PositionServiceImpl positionService;
    private final CandidateServiceImpl candidateService;
    private static final String NOT_FOUND_ERROR_MSG = "%s with %s %s, not found";
    
    public userServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           ElectionEventServiceImpl eEventService, PositionServiceImpl positionService,
                           CandidateServiceImpl candidateService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.eEventService = eEventService;
        this.positionService = positionService;
        this.candidateService = candidateService;
    }
    
    public User getById(Long id) throws ResourceNotFoundException {
        
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MSG, "User", "id", id)));
    }

    @Override
    public AdminResponse createAdminUser(AdminRequest request) {
        User user = Mapper.userModel2User(request);
        user.setRole(AppUserRole.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ElectionEvent event = createElection(request.getEventName());
        user.setEvent(event);
        createAPosition(event, request.getPositionTitle());
        userRepository.save(user);
        return Mapper.admin2UserModel(user);
    }

    private ElectionEvent createElection(String eventName) {
        ElectionEvent event = new ElectionEvent(eventName);
        String token = UUID.randomUUID().toString();
        event.setToken(token);
        eEventService.createEvent(event);
        return event;
    }

    private void createAPosition(ElectionEvent event, String positionTitle) {
        Position position = new Position(positionTitle, event);
        positionService.createPosition(position);
    }

    @Override
    public UserResponse createCandidateUser(CandidateRequest request) {
        User user = Mapper.userModel2User(request);
        user.setRole(AppUserRole.CANDIDATE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ElectionEvent event = eEventService.getEventById(request.getElectionId());
        user.setEvent(event);
        Long positionId = request.getPositionId();
        userRepository.save(user);
        candidateService.addCandidate(positionId, user);
        return Mapper.user2UserModel(user);
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        User user = Mapper.userModel2User(request);
        user.setRole(AppUserRole.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ElectionEvent event = eEventService.getEventById(request.getElectionId());
        user.setEvent(event);
        userRepository.save(user);
        return Mapper.user2UserModel(user);
    }
}
