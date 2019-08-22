package ca.arsenii.plan4me;

import ca.arsenii.plan4me.model.Plan;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ca.arsenii.plan4me.model.AbstractBaseEntity.START_SEQ;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;
public class PlansTestData {
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 8;

    public static final Plan MEAL1 = new Plan(MEAL1_ID, of(2015, Month.MAY, 30, 10, 0), "Завтрак");
    public static final Plan MEAL2 = new Plan(MEAL1_ID + 1, of(2015, Month.MAY, 30, 13, 0), "Обед");
    public static final Plan MEAL3 = new Plan(MEAL1_ID + 2, of(2015, Month.MAY, 30, 20, 0), "Ужин");
    public static final Plan MEAL4 = new Plan(MEAL1_ID + 3, of(2015, Month.MAY, 31, 10, 0), "Завтрак");
    public static final Plan MEAL5 = new Plan(MEAL1_ID + 4, of(2015, Month.MAY, 31, 13, 0), "Обед");
    public static final Plan MEAL6 = new Plan(MEAL1_ID + 5, of(2015, Month.MAY, 31, 20, 0), "Ужин");
    public static final Plan ADMIN_MEAL1 = new Plan(ADMIN_MEAL_ID, of(2015, Month.JUNE, 1, 14, 0), "Админ ланч");
    public static final Plan ADMIN_MEAL2 = new Plan(ADMIN_MEAL_ID + 1, of(2015, Month.JUNE, 1, 21, 0), "Админ ужин");

    public static final List<Plan> MEALS = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);

    public static Plan getCreated() {
        return new Plan(null, of(2015, Month.JUNE, 1, 18, 0), "Созданный ужин");
    }

    public static Plan getUpdated() {
        return new Plan(MEAL1_ID, MEAL1.getDateTime(), "Обновленный завтрак");
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
