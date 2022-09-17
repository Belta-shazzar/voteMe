package com.shazzar.voteme.service.impl;

import com.shazzar.voteme.config.userauth.AppUserService;
import com.shazzar.voteme.entity.Candidate;
import com.shazzar.voteme.entity.ElectionEvent;
import com.shazzar.voteme.entity.Position;
import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.entity.email.confirmationtoken.ConfirmationTokenService;
import com.shazzar.voteme.entity.role.AppUserRole;
import com.shazzar.voteme.exception.ResourceNotFoundException;
import com.shazzar.voteme.model.Mapper;
import com.shazzar.voteme.model.requestmodel.userrequest.*;
import com.shazzar.voteme.model.responsemodel.userresponse.GetAllUserResponse;
import com.shazzar.voteme.model.responsemodel.userresponse.UserActionResponse;
import com.shazzar.voteme.repository.UserRepository;
import com.shazzar.voteme.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AppUserService appUserService;
    private final PasswordEncoder passwordEncoder;
    private final ElectionEventServiceImpl eEventService;
    private final PositionServiceImpl positionService;
    private final CandidateServiceImpl candidateService;
    private final ConfirmationTokenService cTokenService;
    private static final String NOT_FOUND_ERROR_MSG = "%s with %s %s, not found";

//    TODO: Implement method to validate email and password
    
    public UserServiceImpl(UserRepository userRepository, AppUserService appUserService, PasswordEncoder passwordEncoder,
                           ElectionEventServiceImpl eEventService, PositionServiceImpl positionService,
                           CandidateServiceImpl candidateService, ConfirmationTokenService cTokenService) {
        this.userRepository = userRepository;
        this.appUserService = appUserService;
        this.passwordEncoder = passwordEncoder;
        this.eEventService = eEventService;
        this.positionService = positionService;
        this.candidateService = candidateService;
        this.cTokenService = cTokenService;
    }

    public User getById(Long id) throws ResourceNotFoundException {
        
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format(NOT_FOUND_ERROR_MSG, "User", "id", id)));
    }

    @Override
    @Transactional
    public UserActionResponse createAdminUser(AdminRequest request) {
        User user = Mapper.userModel2User(request);
        user.setRole(AppUserRole.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        String eventName = nullCheck(request.getEventName());
        String positionTitle = nullCheck(request.getPositionTitle());
        ElectionEvent event = createElection(eventName);
        user.setEvent(event);
        createAPosition(event, positionTitle);
        String accCreateResponse = cTokenService.createToken(user);
        userRepository.save(user);

        return new UserActionResponse(accCreateResponse);
    }

    public String nullCheck(String nameToCheck) {
        if (nameToCheck == null) {
            throw new IllegalArgumentException("missing field");
        } else {
            return nameToCheck;
        }
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
    public UserActionResponse createCandidateUser(CandidateRequest request) {
        User user = Mapper.userModel2User(request);
        user.setRole(AppUserRole.CANDIDATE);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ElectionEvent event = eEventService.getEventById(request.getElectionId());
        user.setEvent(event);
        Long positionId = request.getPositionId();
        userRepository.save(user);
        candidateService.addCandidate(positionId, user);
        String accCreateResponse = cTokenService.createToken(user);

        return new UserActionResponse(accCreateResponse);
    }

    @Override
    public UserActionResponse createUser(UserRequest request) {
        User user = Mapper.userModel2User(request);
        user.setRole(AppUserRole.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        ElectionEvent event = eEventService.getEventById(request.getElectionId());
        user.setEvent(event);
        String accCreateResponse = cTokenService.createToken(user);
        userRepository.save(user);

        return new UserActionResponse(accCreateResponse);
    }

    @Override
    @SneakyThrows
    public UserActionResponse switchCandidateToUser(Long candidateId, String userName) {
        User adminUser = appUserService.getUserByUsername(userName);
        User user = getById(candidateId);
        ElectionEvent event = adminUser.getEvent();
        if (user.getEvent().equals(event) && user.getRole().equals(AppUserRole.CANDIDATE)) {
            user.setRole(AppUserRole.USER);
            candidateService.deleteCandidate(user.getId());
            userRepository.save(user);
            String switchSuccess = user.getFullName() + " have been removed from candidate list";
            return new UserActionResponse(switchSuccess);
        } else {
            throw new IllegalArgumentException("This user is not a candidate");
        }
    }

    @Override
    @SneakyThrows
    @Transactional
    public UserActionResponse castVote(VoteRequest vote, String username) {
        ElectionEvent election = eEventService.getEventById(vote.getEventId());
        eEventService.checkDate(election);
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with %s %s not found", "username", username))
        );

//        Check if this user have voted
        if (!user.getVotedCandidates().isEmpty()) {
            throw new IllegalArgumentException("This user already casted vote");
        } else {
            Set<Position> votedPosition = new HashSet<>();
            Set<Candidate> votedCandidate = new HashSet<>();
            for (String positionTitle : vote.getVoteMap().keySet()) {
                for (Position position : election.getPositions()) {
                    if (position.getPositionTitle().equals(positionTitle) && !votedPosition.contains(position)) {
                        votedPosition.add(position);
                        Candidate candidate = candidateService.getById(vote.getVoteMap().get(positionTitle));
                        if (candidate.getPosition().equals(position)) {
//                       TODO: To be continued
                            candidate.getVoters().add(user);
                            votedCandidate.add(candidate);
//                        candidateService.saveCandidate(candidate);
                        }
                    }
                }
            }
            user.setVotedCandidates(votedCandidate);
            userRepository.save(user);
            votedPosition.clear();
            String voteSuccess = user.getFullName() + " successfully voted";
            return new UserActionResponse(voteSuccess);
        }

    }

    @Override
    public void deleteUser(String name) {
        User user = appUserService.getUserByUsername(name);
        if (user.getRole().equals(AppUserRole.ADMIN)) {
            eEventService.deleteEvent(name);
        }
        deleteUserWithExpiredToken(user);
    }

    @Override
    public void deleteUserWithExpiredToken(User user) {
        if (user.getRole() == AppUserRole.CANDIDATE) {
            candidateService.deleteCandidate(user.getId());
        }

        userRepository.delete(user);
    }

    @Override
    public Set<GetAllUserResponse> getUsers(String userName) {
        User user = appUserService.getUserByUsername(userName);
        Set<User> users = user.getEvent().getUsers();
        return Mapper.getAllUserResponses(users);
    }

    @SneakyThrows
    public String updateIsEnabled(User user) {
        if (!user.getIsEnabled()) {
            user.setIsEnabled(true);
            userRepository.save(user);
            return "Your account have been activated";
        } else {
            return "This account is already verified";
        }
    }
}
