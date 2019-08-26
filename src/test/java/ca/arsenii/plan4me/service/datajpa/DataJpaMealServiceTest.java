package ca.arsenii.plan4me.service.datajpa;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ca.arsenii.plan4me.UserTestData;
import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.service.AbstractPlanServiceTest;
import ca.arsenii.plan4me.util.exception.NotFoundException;

import static ca.arsenii.plan4me.Profiles.DATAJPA;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ca.arsenii.plan4me.PlanTestData.*;
import static ca.arsenii.plan4me.Profiles.DATAJPA;
import static ca.arsenii.plan4me.UserTestData.ADMIN_ID;

@ActiveProfiles(DATAJPA)
class DataJpaPlanServiceTest extends AbstractPlanServiceTest {
    @Test
    void getWithUser() throws Exception {
        Plan adminPlan = service.getWithUser(ADMIN_PLAN_ID, ADMIN_ID);
        assertMatch(adminPlan, ADMIN_PLAN1);
        UserTestData.assertMatch(adminPlan.getUser(), UserTestData.ADMIN);
    }

    @Test
    void getWithUserNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.getWithUser(PLAN1_ID, ADMIN_ID));
    }
}
