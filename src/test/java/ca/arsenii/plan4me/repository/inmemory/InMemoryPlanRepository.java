package ca.arsenii.plan4me.repository.inmemory;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.repository.PlanRepository;
import ca.arsenii.plan4me.util.PlansUtil;
import ca.arsenii.plan4me.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ca.arsenii.plan4me.UserTestData.ADMIN_ID;
import static ca.arsenii.plan4me.UserTestData.USER_ID;

@Repository
public class InMemoryPlanRepository implements PlanRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryPlanRepository.class);
    private Map<Integer, InMemoryBaseRepository> userPlansMap = new ConcurrentHashMap<>();

    {
        PlansUtil.MEALS.forEach(plan -> save(plan, USER_ID));

        save(new Plan(LocalDateTime.of(2019, Month.AUGUST, 1, 14, 0), "asd"), ADMIN_ID);
    }
    @Override
    public Plan save(Plan meal, int userId) {
        InMemoryBaseRepository<Plan> meals = userPlansMap.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>());
        return meals.save(meal);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ PreDestroy");
    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryBaseRepository<Plan> meals = userPlansMap.get(userId);
        return meals != null && meals.delete(id);
    }

    @Override
    public Plan get(int id, int userId) {
        InMemoryBaseRepository<Plan> meals = userPlansMap.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Plan> getAll(int userId) {
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Plan> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllFiltered(userId, meal -> Util.isBetween(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Plan> getAllFiltered(int userId, Predicate<Plan> filter) {
        InMemoryBaseRepository<Plan> meals = userPlansMap.get(userId);
        return meals == null ? Collections.emptyList() :
                meals.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Plan::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}
