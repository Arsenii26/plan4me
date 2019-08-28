package ca.arsenii.plan4me;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.to.PlanTo;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.Month;
import java.util.List;

import static ca.arsenii.plan4me.TestUtil.readListFromJsonMvcResult;
import static ca.arsenii.plan4me.model.AbstractBaseEntity.START_SEQ;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;

public class PlanTestData {
    public static final int PLAN1_ID = START_SEQ + 2;
    public static final int ADMIN_PLAN_ID = START_SEQ + 8;

    public static final Plan PLAN1 = new Plan(PLAN1_ID, of(2019, Month.MAY, 30, 10, 0), "Eating");
    public static final Plan PLAN2 = new Plan(PLAN1_ID + 1, of(2019, Month.MAY, 30, 13, 0), "Going to the gym");
    public static final Plan PLAN3 = new Plan(PLAN1_ID + 2, of(2019, Month.MAY, 30, 20, 0), "Reading");
    public static final Plan PLAN4 = new Plan(PLAN1_ID + 3, of(2019, Month.MAY, 31, 10, 0), "Work A");
    public static final Plan PLAN5 = new Plan(PLAN1_ID + 4, of(2019, Month.MAY, 31, 13, 0), "Move to another work");
    public static final Plan PLAN6 = new Plan(PLAN1_ID + 5, of(2019, Month.MAY, 31, 20, 0), "Work B");
    public static final Plan ADMIN_PLAN1 = new Plan(ADMIN_PLAN_ID, of(2019, Month.JUNE, 1, 14, 0), "Admin test");
    public static final Plan ADMIN_PLAN2 = new Plan(ADMIN_PLAN_ID + 1, of(2019, Month.JUNE, 1, 21, 0), "Admin bug fixes");

    public static final List<Plan> PLANS = List.of(PLAN6, PLAN5, PLAN4, PLAN3, PLAN2, PLAN1);

    public static Plan getCreated() {
        return new Plan(null, of(2019, Month.JUNE, 1, 18, 0), "New plan");
    }

    public static Plan getUpdated() {
        return new Plan(PLAN1_ID, PLAN1.getDateTime(), "Updated plan");
    }

    public static void assertMatch(Plan actual, Plan expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user");
    }

    public static void assertMatch(Iterable<Plan> actual, Plan... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Plan> actual, Iterable<Plan> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected);
    }

    public static ResultMatcher contentJson(PlanTo... expected) {
        return contentJson(List.of(expected));
    }

    public static ResultMatcher contentJson(Iterable<PlanTo> expected) {
        return result -> assertThat(readListFromJsonMvcResult(result, PlanTo.class)).isEqualTo(expected);
    }
}
