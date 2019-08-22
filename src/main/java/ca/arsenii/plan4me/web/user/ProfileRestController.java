package ca.arsenii.plan4me.web.user;

import ca.arsenii.plan4me.model.User;
import org.springframework.stereotype.Controller;

import static ca.arsenii.plan4me.web.SecurityUtil.authUserId;

@Controller
public class ProfileRestController extends AbstractUserController {

    public User get() {
        return super.get(authUserId());
    }

    public void delete() {
        super.delete(authUserId());
    }

    public void update(User user) {
        super.update(user, authUserId());
    }
}