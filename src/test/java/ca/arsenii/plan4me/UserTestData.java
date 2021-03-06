package ca.arsenii.plan4me;

import ca.arsenii.plan4me.model.Role;
import ca.arsenii.plan4me.model.User;
import ca.arsenii.plan4me.web.json.JsonUtil;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Arrays;
import java.util.List;

import static ca.arsenii.plan4me.TestUtil.readListFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;
import static ca.arsenii.plan4me.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static void assertMatch(User actual, User expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "plans", "password");
    }

    public static void assertMatch(Iterable<User> actual, User... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<User> actual, Iterable<User> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("registered", "plans", "password").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(User... expected){
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), List.of(expected));
    }

    public static ResultMatcher contentJson(User expected){
        return result -> assertMatch(readListFromJsonMvcResult(result, User.class), expected);
    }

    public static String jsonWithPassword(User user, String passw){
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
