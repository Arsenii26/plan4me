package ca.arsenii.plan4me.web.user;

import ca.arsenii.plan4me.util.PlansUtil;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import ca.arsenii.plan4me.model.User;

import java.util.List;

import static ca.arsenii.plan4me.PlanTestData.PLANS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ca.arsenii.plan4me.UserTestData.*;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users",
                        new AssertionMatcher<List<User>>() {
                            @Override
                            public void assertion(List<User> actual) throws AssertionError {
                                assertMatch(actual, ADMIN, USER);
                            }
                        }
                ));
    }

    @Test
    void getPlans() throws Exception {
        mockMvc.perform(get("/plans"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("plans"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/plans.jsp"))
                .andExpect(model().attribute("plans", PlansUtil.getPlans(PLANS)));
    }
}