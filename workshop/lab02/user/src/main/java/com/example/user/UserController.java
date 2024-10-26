package com.example.user;

import com.example.user.exception.FakeInternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final Random random;

    private Logger logger;

    public UserController() {
        random = new Random(0);

        this.logger = LoggerFactory.getLogger(UserController.class);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {

        // Simulate request error
        if (random.nextInt(3) > 1) {
            throw new FakeInternalException("Failed to fetch user id %d".formatted(id));
        }

        logger.info("Fetching user id {}", id);
        return new User(id, "Tomas");
    }
}