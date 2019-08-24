package ca.arsenii.plan4me.web.user;

import ca.arsenii.plan4me.model.User;
import ca.arsenii.plan4me.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static ca.arsenii.plan4me.util.ValidationUtil.assureIdConsistent;
import static ca.arsenii.plan4me.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected UserService service;

    public List<User> getAll(){
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User create(User user){
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void enable(int id, boolean enabled){
        log.info(enabled ? "enable{}" : "disable {}", id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }
}