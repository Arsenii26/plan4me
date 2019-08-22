package ca.arsenii.plan4me.service;

import ca.arsenii.plan4me.model.User;
import ca.arsenii.plan4me.repository.UserRepository;
import ca.arsenii.plan4me.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static ca.arsenii.plan4me.util.ValidationUtil.checkNotFound;
import static ca.arsenii.plan4me.util.ValidationUtil.checkNotFoundWithId;
@Service
public class UserService {
    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(User user) {
        return repository.save(user);
    }

    public void delete(int id) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User get(int id) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public User getByEmail(String email) throws NotFoundException {
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public void update(User user) throws NotFoundException {
        checkNotFoundWithId(repository.save(user), user.getId());
    }
}