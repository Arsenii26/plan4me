package ca.arsenii.plan4me.service;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.util.exception.ErrorType;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static ca.arsenii.plan4me.PlanTestData.*;
import static ca.arsenii.plan4me.UserTestData.ADMIN_ID;
import static ca.arsenii.plan4me.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractPlanServiceTest extends AbstractServiceTest {

    @Autowired
    protected PlanService service;

    @Test
    void delete() throws Exception {
        service.delete(PLAN1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), PLAN6, PLAN5, PLAN4, PLAN3, PLAN2);
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(1, USER_ID));
    }

    @Test
    void deleteNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.delete(PLAN1_ID, ADMIN_ID));
    }

    @Test
    void create() throws Exception {
        Plan newPlan = getCreated();
        Plan created = service.create(newPlan, USER_ID);
        newPlan.setId(created.getId());
        assertMatch(newPlan, created);
        assertMatch(service.getAll(USER_ID), newPlan, PLAN6, PLAN5, PLAN4, PLAN3, PLAN2, PLAN1);
    }

    @Test
    void get() throws Exception {
        Plan actual = service.get(ADMIN_PLAN_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_PLAN1);
    }

    @Test
    void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(1, ADMIN_ID));
    }

    @Test
    void getNotOwn() throws Exception {
        assertThrows(NotFoundException.class, () ->
                service.get(PLAN1_ID, ADMIN_ID));
    }

    @Test
    void update() throws Exception {
        Plan updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(PLAN1_ID, USER_ID), updated);
    }

    @Test
    void updateNotFound() throws Exception {
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.update(PLAN1, ADMIN_ID));
        String msg = e.getMessage();
        assertTrue(msg.contains(ErrorType.DATA_NOT_FOUND.name()));
        assertTrue(msg.contains(NotFoundException.NOT_FOUND_EXCEPTION));
        assertTrue(msg.contains(String.valueOf(PLAN1_ID)));
    }

    @Test
    void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), PLANS);
    }

    @Test
    void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2019, Month.MAY, 30),
                LocalDate.of(2019, Month.MAY, 30), USER_ID), PLAN3, PLAN2, PLAN1);
    }

    @Test
    void createWithException() throws Exception {
        Assumptions.assumeTrue(isJpaBased(), "Validation not supported (JPA only)");
        validateRootCause(() -> service.create(new Plan(null, of(2019, Month.JUNE, 1, 18, 0), "  "), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Plan(null, null, "Plan"), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Plan(null, of(2019, Month.JUNE, 1, 18, 0), null), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Plan(null, of(2019, Month.JUNE, 1, 18, 0), null), USER_ID), ConstraintViolationException.class);
    }
}