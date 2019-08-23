package ca.arsenii.plan4me;

import ca.arsenii.plan4me.model.Plan;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ca.arsenii.plan4me.model.AbstractBaseEntity.START_SEQ;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
public class PlansTestData {
    public static final int PLAN1_ID = START_SEQ + 2;
    public static final int ADMIN_PLAN_ID = START_SEQ + 8;

    public static final Plan PLAN1 = new Plan(PLAN1_ID, of(2015, Month.MAY, 30, 10, 0), "Завтрак");
    public static final Plan PLAN2 = new Plan(PLAN1_ID + 1, of(2015, Month.MAY, 30, 13, 0), "Обед");
    public static final Plan PLAN3 = new Plan(PLAN1_ID + 2, of(2015, Month.MAY, 30, 20, 0), "Ужин");
    public static final Plan PLAN4 = new Plan(PLAN1_ID + 3, of(2015, Month.MAY, 31, 10, 0), "Завтрак");
    public static final Plan PLAN5 = new Plan(PLAN1_ID + 4, of(2015, Month.MAY, 31, 13, 0), "Обед");
    public static final Plan PLAN6 = new Plan(PLAN1_ID + 5, of(2015, Month.MAY, 31, 20, 0), "Ужин");
    public static final Plan ADMIN_PLAN1 = new Plan(ADMIN_PLAN_ID, of(2015, Month.JUNE, 1, 14, 0), "Админ ланч");
    public static final Plan ADMIN_PLAN2 = new Plan(ADMIN_PLAN_ID + 1, of(2015, Month.JUNE, 1, 21, 0), "Админ ужин");

    public static final List<Plan> PLANS = Arrays.asList(PLAN6, PLAN5, PLAN4, PLAN3, PLAN2, PLAN1);

    public static Plan getCreated() {
        return new Plan(null, of(2015, Month.JUNE, 1, 18, 0), "Созданный ужин");
    }

    public static Plan getUpdated() {
        return new Plan(PLAN1_ID, PLAN1.getDateTime(), "Обновленный завтрак");
    }

    public static void assertMatch(Plan actual, Plan expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }

    public static void assertMatch(Iterable<Plan> actual, Plan... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Plan> actual, Iterable<Plan> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
