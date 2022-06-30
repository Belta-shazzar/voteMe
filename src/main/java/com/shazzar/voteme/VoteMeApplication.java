package com.shazzar.voteme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoteMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoteMeApplication.class, args);
    }

//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public VoteMeApplication(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        UserRequest userRequest = new UserRequest("Niel Nonso", "Shazzar", "nieltest@gmail.com", "secret");
//        UserRequest userRequest = new UserRequest("Samson Twist", "Sammie", "sammietest@gmail.com", "reticent");
//        UserRequest userRequest = new UserRequest("Susan Jane", "Susy", "susytest@gmail.com", "Clandestine");
//        UserRequest userRequest = new UserRequest("Dante Mba", "Daniel", "dantetest@gmail.com", "clansecret");

//        User user = new User();
//
//        user.setFullName(userRequest.getFullName());
//        user.setUserName(userRequest.getUserName());
//        user.setEmail(userRequest.getEmail());
//        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

//        userRepository.save(user);


//    }
}
