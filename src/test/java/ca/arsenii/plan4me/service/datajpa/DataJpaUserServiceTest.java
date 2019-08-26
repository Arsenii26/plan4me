package ca.arsenii.plan4me.service.datajpa;

import ca.arsenii.plan4me.PlanTestData;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import ca.arsenii.plan4me.PlanTestData;
import ca.arsenii.plan4me.model.User;
import ca.arsenii.plan4me.service.AbstractJpaUserServiceTest;
import ca.arsenii.plan4me.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ca.arsenii.plan4me.Profiles.DATAJPA;
import static ca.arsenii.plan4me.UserTestData.*;

@ActiveProfiles(DATAJPA)
class DataJpaUserServiceTest extends AbstractJpaUserServiceTest {
    @Test
    void getWithPlans() throws Exception {
        User admin = service.getWithPlans(ADMIN_ID);
        assertMatch(admin, ADMIN);
        PlanTestData.assertMatch(admin.getPlans(), PlanTestData.ADMIN_PLAN2, PlanTestData.ADMIN_PLAN1);
    }

    @Test
    void getWithPlansNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.getWithPlans(1));
    }
}