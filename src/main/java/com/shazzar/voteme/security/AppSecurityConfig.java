package com.shazzar.voteme.security;

import com.shazzar.voteme.entity.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class AppSecurityConfig {
    
    private final AppUserService appUserService; 
    private PasswordEncoder passwordEncoder;

//    private static final String[] WHITE_LIST_URLS = {"/voteMe/v1/user", "/voteMe/v1/event"};
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/voteMe/v1/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

        return http.build();

    }

//    @Bean
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(appUserService);

        return provider;
    }

}
