package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);

    private final Map<Integer, User> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        save(new User(1, "Alexa", "alexasmith92@yahoo.com", "adminadmin", Role.ADMIN));
        save(new User(2, "Leo", "s.leo18@gmail.com", "123456", Role.USER));
        save(new User(3, "Nikki", "nik84@gmail.com", "qwerty", Role.USER));
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        if (!repository.containsKey(id)) {
            return false;
        }
        repository.remove(id);
        return true;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        user.setId(counter.incrementAndGet());
        repository.put(user.getId(), user);
        return repository.containsValue(user) ? user : null;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        Collection<User> users = repository.values();
        return users.stream().sorted(Comparator.comparing(User::getName)).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Collection<User> users = repository.values();
        return users.stream().filter(user -> email.equals(user.getEmail())).findAny().orElse(null);
    }
}
