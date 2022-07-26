package com.shazzar.voteme.security.userAuth;

import com.shazzar.voteme.entity.User;
import com.shazzar.voteme.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {
    
    private final UserRepository userRepository;

    public AppUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with %s %s not found", "username", username)));
        return new AppUser(user);
    }
    
    
}
