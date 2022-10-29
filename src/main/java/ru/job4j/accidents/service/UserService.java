package ru.job4j.accidents.service;

import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> save(User user) {
        Optional<User> result = Optional.empty();
        try {
            result = Optional.of(repository.save(user));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
