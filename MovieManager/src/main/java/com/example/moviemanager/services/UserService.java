package com.example.moviemanager.services;

import com.example.moviemanager.entities.UserEntity;
import com.example.moviemanager.exceptions.BadRequestException;
import com.example.moviemanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final int PASSWORD_LENGTH = 8;

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = repository.findByNickname(nickname);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User with such nickname not found");
        }
        return userEntity.get();
    }

    private void userExists(String nickname) {
        Optional<UserEntity> userEntity = repository.findByNickname(nickname);
        if (userEntity.isPresent()) {
            throw new UsernameNotFoundException("User with such nickname already exist");
        }
    }

    public void saveUser(String firstName,
                         String secondName,
                         String nickname,
                         String password,
                         String repeatPassword) {
        userExists(nickname);
        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setNickname(nickname);
        if (password.length() < PASSWORD_LENGTH) {
            throw new BadRequestException("Password should be at least 8 symbols");
        } else if (password.equals(repeatPassword)) {
            user.setPassword(passwordEncoder.encode(password));
            repository.save(user);
        } else {
            throw new BadRequestException("incorrect password");
        }
    }

    @SneakyThrows
    public void login(String nickname, String password) {
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(nickname, password);
        AuthenticationManager authenticationManager = authenticationConfiguration.getAuthenticationManager();
        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }
}
