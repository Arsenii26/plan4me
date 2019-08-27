package ca.arsenii.plan4me.web.user.json;

import ca.arsenii.plan4me.UserTestData;
import ca.arsenii.plan4me.model.User;
import ca.arsenii.plan4me.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import ca.arsenii.plan4me.model.Plan;

import java.util.List;

import static ca.arsenii.plan4me.PlanTestData.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {

    @Test
    void readWriteValue() throws Exception {
        String json = JsonUtil.writeValue(ADMIN_PLAN1);
        System.out.println(json);
        Plan plan = JsonUtil.readValue(json, Plan.class);
        assertMatch(plan, ADMIN_PLAN1);
    }

    @Test
    void readWriteValues() throws Exception {
        String json = JsonUtil.writeValue(PLANS);
        System.out.println(json);
        List<Plan> plans = JsonUtil.readValues(json, Plan.class);
        assertMatch(plans, PLANS);
    }
    @Test
    void writeOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}