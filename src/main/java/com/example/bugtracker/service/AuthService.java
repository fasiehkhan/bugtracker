package com.example.bugtracker.service;

import com.example.bugtracker.Dto.RegisterRequest;
import com.example.bugtracker.model.NotificationEmail;
import com.example.bugtracker.model.User;
import com.example.bugtracker.model.VerificationToken;
import com.example.bugtracker.repository.UserRepository;
import com.example.bugtracker.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your email", user.getEmail(),
                "Thank you for signing up to BugTracker, " +
                "please click the link below to activate your account: " +
                "http://localhost:8080/api/auth/accountVerification/" + token
                ));
    }
        private String generateVerificationToken(User user) {
            String token = UUID.randomUUID().toString();
            VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            verificationToken.setUser(user);

            verificationTokenRepository.save(verificationToken);
            return token;
        }

}
