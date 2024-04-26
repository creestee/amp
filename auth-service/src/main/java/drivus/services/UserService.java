package drivus.services;

import drivus.dto.SignupRequest;
import drivus.exceptions.DuplicateException;
import drivus.model.User;
import drivus.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest request) {
        if (repository.existsByEmail(request.email())) {
            throw new DuplicateException(String.format("User with the email address '%s' already exists.", request.email()));
        }

        if (repository.existsByUsername(request.name())) {
            throw new DuplicateException(String.format("User with the username '%s' already exists.", request.name()));
        }

        User user = new User();
        user.setEmail(request.email());
        user.setUsername(request.name());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setServiceProvider(request.isServiceProvider());

        repository.saveAndFlush(user);
    }

}
