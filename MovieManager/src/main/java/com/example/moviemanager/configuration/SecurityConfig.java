package com.example.moviemanager.configuration;

import com.example.moviemanager.handlers.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationEntryPoint customPoint;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((auth) -> auth
                        .antMatchers("/registration", "/login", "/", "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .logout()
                .logoutUrl("/logout").logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(customPoint);
        return http.build();
    }
}