package com.example.moviemanager.controllers;

import com.example.moviemanager.entities.UserEntity;
import com.example.moviemanager.requests.EditPasswordRequest;
import com.example.moviemanager.requests.LoginRequest;
import com.example.moviemanager.requests.RegisterUserRequest;
import com.example.moviemanager.requests.ResetPasswordRequest;
import com.example.moviemanager.responses.RegisterAndDeleteResponse;
import com.example.moviemanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String home() {
        return "home page";
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterAndDeleteResponse> registration(
            @Valid @RequestBody RegisterUserRequest request) {
        userService.saveUser(
                request.getFirstName(),
                request.getSecondName(),
                request.getNickname(),
                request.getPassword(),
                request.getRepeatPassword(),
                request.getQuestion(),
                request.getAnswer()
        );
        return ResponseEntity.ok(new RegisterAndDeleteResponse(
                "Registered User:",
                request.getFirstName(),
                request.getSecondName(),
                request.getNickname()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginRequest request) {
        userService.login(request.getLogin(), request.getPassword());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public RedirectView logout(HttpServletRequest request, HttpServletResponse response) {
        userService.customLogout(request, response);
        return new RedirectView("/account");
    }

    @PutMapping("{id}/edit-password")
    public ResponseEntity<Void> editPassword(
            @PathVariable long id,
            @RequestBody EditPasswordRequest editRequest) {
        userService.editPassword(
                id,
                editRequest.getOldPassword(),
                editRequest.getNewPassword(),
                editRequest.getRepeatNewPassword()
        );
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request){
        userService.resetPassword(
                request.getNickname(),
                request.getAnswer(),
                request.getPassword(),
                request.getRepeatPassword()
                );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-account")
    public ResponseEntity<RegisterAndDeleteResponse> deleteAccount(
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse,
            @AuthenticationPrincipal UserEntity user) {
        RegisterAndDeleteResponse response = new RegisterAndDeleteResponse(
                "Deleted User:",
                user.getFirstName(),
                user.getSecondName(),
                user.getNickname()
        );
        userService.deleteAccount(servletRequest, servletResponse, user);
        return ResponseEntity.ok(response);
    }

}
