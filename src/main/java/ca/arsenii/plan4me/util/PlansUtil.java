package ca.arsenii.plan4me.util;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.to.PlanTo;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PlansUtil {
    public static final List<Plan> PLANS = Arrays.asList(
            new Plan(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак"),
            new Plan(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед"),
            new Plan(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин"),
            new Plan(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак"),
            new Plan(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед"),
            new Plan(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин")
    );

    public PlansUtil() {
    }

    private static List<PlanTo> getFilteredWithExcess(Collection<Plan> plans, Predicate<Plan> filter) {
        return plans.stream()
                .filter(filter)
                .map(plan -> createWithExcess(plan))
                .collect(toList());
    }

        private static PlanTo createWithExcess(Plan plan) {
        return new PlanTo(plan.getId(), plan.getDateTime(), plan.getPlan());
    }

    public static List<PlanTo> getPlans(Collection<Plan> plans) {
//        return PLANS;
        return getFilteredWithExcess(plans, plan -> true);
    }
    public static List<PlanTo> getFilteredWithExcess(Collection<Plan> plans,  @Nullable LocalTime startTime, @Nullable LocalTime endTime) {
        return getFilteredWithExcess(plans,  plan -> Util.isBetween(plan.getTime(), startTime, endTime));
    }

}
