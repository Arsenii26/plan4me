package ca.arsenii.plan4me.web.user.json;

import ca.arsenii.plan4me.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import ca.arsenii.plan4me.model.Plan;

import java.util.List;

import static ca.arsenii.plan4me.PlanTestData.*;

class JsonUtilTest {

    @Test
    void readWriteValue() throws Exception {
        String json = JsonUtil.writeValue(ADMIN_PLAN1);
        System.out.println(json);
        Plan meal = JsonUtil.readValue(json, Plan.class);
        assertMatch(meal, ADMIN_PLAN1);
    }

    @Test
    void readWriteValues() throws Exception {
        String json = JsonUtil.writeValue(PLANS);
        System.out.println(json);
        List<Plan> plans = JsonUtil.readValues(json, Plan.class);
        assertMatch(plans, PLANS);
    }
}