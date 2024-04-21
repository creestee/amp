package drivus.services;

import drivus.dto.SignupRequest;
import drivus.exceptions.DuplicateException;
import drivus.model.User;
import drivus.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void signup(SignupRequest request) {
        String email = request.email();
        Optional<User> existingUser = repository.findByEmail(email);
        if (existingUser.isPresent()) {
            throw new DuplicateException(String.format("User with the email address '%s' already exists.", email));
        }

        String hashedPassword = passwordEncoder.encode(request.password());
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setServiceProvider(request.isServiceProvider());
        user.setUsername(request.name());

        repository.saveAndFlush(user);
    }

}
