package com.shazzar.voteme.config.userauth;

import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.entity.role.AppUserRole;
import com.shazzar.voteme.model.Mapper;
import com.shazzar.voteme.model.requestmodel.userrequest.SignInRequest;
import com.shazzar.voteme.repository.UserRepository;
import com.shazzar.voteme.util.JwtUtil;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public class AppUserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Lazy
    public AppUserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with %s %s not found", "username", username)));
        return new AppUser(user);
    }

    public User getUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with %s %s not found", "username", username)));
    }


    public Object authenticateUser(SignInRequest request, HttpServletRequest httpServletRequest) {
        String username = request.getUsername();
        String password = request.getPassword();

        jwtUtil.authenticate(username, password);
        UserDetails userDetails = loadUserByUsername(username);
        String jwt = jwtUtil.generateToken(userDetails, httpServletRequest);
        User user = userRepository.findByEmail(request.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with %s %s not found", "username", username)));

        if (user.getRole().equals(AppUserRole.ADMIN)) {
            return Mapper.admin2UserModel(user, jwt);
        } else return Mapper.user2UserModel(user, jwt);
    }
}

