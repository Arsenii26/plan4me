package ca.arsenii.plan4me.util;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.model.PlanTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PlansUtil {
    public static final List<Plan> MEALS = Arrays.asList(
            new Plan(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак"),
            new Plan(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед"),
            new Plan(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин"),
            new Plan(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак"),
            new Plan(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед"),
            new Plan(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин")
    );

//    public static List<Plan> getPlans(Collection<Plan> plans) {
//        return MEALS;
//    }

    private static List<PlanTo> getFilteredWithExcess(Collection<Plan> meals,  Predicate<Plan> filter) {
//        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
//                .collect(
//                        Collectors.groupingBy(Plan::getDate, Collectors.summingInt(Plan::getId))
////                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
//                );

        return meals.stream()
                .filter(filter)
                .map(plan -> createWithExcess(plan))
                .collect(toList());
    }

    private static PlanTo createWithExcess(Plan meal) {
        return new PlanTo(meal.getId(), meal.getLocalDateTime(), meal.getPlan());
    }

    public static List<PlanTo> getPlans(Collection<Plan> plans) {
//        return MEALS;
        return getFilteredWithExcess(plans, plan -> true);
    }


}
