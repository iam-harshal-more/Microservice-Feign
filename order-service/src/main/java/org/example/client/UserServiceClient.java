package org.example.client;

import org.example.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "user-service", fallback = UserServiceFallback.class)
public interface UserServiceClient {

    @GetMapping("/api/users/{id}")
    User getUserById(@PathVariable("id") Long id);

    @GetMapping("/api/users")
    List<User> getAllUsers();
}

// Fallback implementation for circuit breaker pattern
@org.springframework.stereotype.Component
class UserServiceFallback implements UserServiceClient {

    @Override
    public User getUserById(Long id) {
        User fallbackUser = new User();
        fallbackUser.setId(id);
        fallbackUser.setName("Unknown User");
        fallbackUser.setEmail("unknown@example.com");
        fallbackUser.setPhone("N/A");
        fallbackUser.setAddress("N/A");
        return fallbackUser;
    }

    @Override
    public List<User> getAllUsers() {
        return java.util.Collections.emptyList();
    }
}
