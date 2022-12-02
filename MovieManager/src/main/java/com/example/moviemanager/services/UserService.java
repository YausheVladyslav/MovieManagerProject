package com.example.moviemanager.services;

import com.example.moviemanager.entities.UserEntity;
import com.example.moviemanager.enums.SecretQuestions;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByNickname(nickname);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User with such nickname not found");
        }
        return userEntity.get();
    }

    private void userExists(String nickname) {
        Optional<UserEntity> userEntity = userRepository.findByNickname(nickname);
        if (userEntity.isPresent()) {
            throw new UsernameNotFoundException("User with such nickname already exist");
        }
    }

    public void saveUser(String firstName,
                         String secondName,
                         String nickname,
                         String password,
                         String repeatPassword,
                         String question,
                         String answer) {
        userExists(nickname);
        UserEntity user = new UserEntity();
        user.setFirstName(firstName);
        user.setSecondName(secondName);
        user.setNickname(nickname);
        if (password.equals(repeatPassword)) {
            user.setPassword(passwordEncoder.encode(password));
        } else {
            throw new BadRequestException("Incorrect password");
        }
        user.setQuestion(SecretQuestions.valueOf(question));
        user.setAnswer(passwordEncoder.encode(answer));
        userRepository.save(user);
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

    public void customLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

    public void editPassword(long id, String oldPassword, String newPassword, String repeatNewPassword) {
        UserEntity user = userRepository.findById(id).get();
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            if (newPassword.equals(repeatNewPassword)) {
                user.setPassword(passwordEncoder.encode(newPassword));
                userRepository.save(user);
            } else {
                throw new BadRequestException("Incorrect password");
            }
        } else {
            throw new BadRequestException("Incorrect password");
        }
    }

    public void deleteAccount(HttpServletRequest request, HttpServletResponse response, UserEntity user) {
        userRepository.delete(user);
        customLogout(request, response);
    }

    public void resetPassword(String nickname,
                              String currentAnswer,
                              String password,
                              String repeatPassword) {
        Optional<UserEntity> foundUser = userRepository.findByNickname(nickname);
        if (foundUser.isPresent()) {
            UserEntity user = foundUser.get();
            if (passwordEncoder.matches(currentAnswer, user.getAnswer())) {
                if (password.equals(repeatPassword)) {
                    user.setPassword(passwordEncoder.encode(password));
                    userRepository.save(user);
                } else {
                    throw new BadRequestException("Incorrect password");
                }
            } else {
                throw new BadRequestException("Incorrect answer");
            }
        }
    }
}
