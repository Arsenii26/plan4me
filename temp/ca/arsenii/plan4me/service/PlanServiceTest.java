package ca.arsenii.plan4me.service;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import static ca.arsenii.plan4me.PlansTestData.*;
import static ca.arsenii.plan4me.UserTestData.USER_ID;

@ContextConfiguration({
        "spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class PlanServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private PlanService planService;

    @Test(expected = NotFoundException.class)
    public void get() {
        Plan plan = planService.get(PLAN1.getId(), 10);
        assertMatch(plan, PLAN1);

    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        planService.delete(PLAN1.getId(), 10);
    }

    @Test(expected = NotFoundException.class)
    public void update() {
        planService.update(PLAN1, 10);
    }



    @Test
    public void getBetweenDates() {
        LocalDate localTime1 = LocalDate.of(2011, 03, 11);
        LocalDate localTime2 = LocalDate.of(2019, 12, 25);

        planService.getBetweenDates(localTime1, localTime2, PLAN1.getId());
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime aDateTime1 = LocalDateTime.of(2011, Month.MARCH, 14, 12, 43, 45);
        LocalDateTime aDateTime2 = LocalDateTime.of(2019, Month.MARCH, 14, 12, 43, 45);
        planService.getBetweenDateTimes(aDateTime1, aDateTime2, PLAN1.getId());
    }

    @Test
    public void getAll() {
        assertMatch(planService.getAll(USER_ID), PLANS);
    }


    @Test
    public void create() {
        Plan newPlan = getCreated();
        Plan created = planService.create(newPlan, USER_ID);
        newPlan.setId(created.getId());
        assertMatch(newPlan, created);
        assertMatch(planService.getAll(USER_ID), newPlan, PLAN6, PLAN5, PLAN4, PLAN3, PLAN2, PLAN1);
    }
}
