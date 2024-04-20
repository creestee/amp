package drivus.services;

import drivus.model.LoginAttempt;
import drivus.repository.LoginAttemptRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoginService {

    private final LoginAttemptRepository repository;
    private static final int RECENT_COUNT = 10;

    public LoginService(LoginAttemptRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void addLoginAttempt(String email, boolean success) {
        LoginAttempt loginAttempt = new LoginAttempt();
        loginAttempt.setEmail(email);
        loginAttempt.setSuccess(success);
        loginAttempt.setCreatedAt(LocalDateTime.now());

        repository.saveAndFlush(loginAttempt);
    }

    public List<LoginAttempt> findRecentLoginAttempts(String email) {
        return repository.findRecentByEmail(email, PageRequest.of(0, RECENT_COUNT));
    }
}

