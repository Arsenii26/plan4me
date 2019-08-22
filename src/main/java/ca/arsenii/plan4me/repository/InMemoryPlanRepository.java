package ca.arsenii.plan4me.repository;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.util.PlansUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryPlanRepository implements PlanRepository{
    private Map<Integer, Plan> repository = new ConcurrentHashMap<>();
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    {
        PlansUtil.MEALS.forEach(this::save);

//        save(new Plan(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак"));
//        save(new Plan(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтра2к"));
//        save(new Plan(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "З345автрак"));
//        save(new Plan(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "За3463втрак"));
//        save(new Plan(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "За6346втрак"));
//        save(new Plan(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "За37втрак"));

    }

    @Override
    public Plan save(Plan plan) {
        if(plan.isNew()){
            plan.setId(atomicInteger.incrementAndGet());
            repository.put(plan.getId(), plan);
            return plan;
        }
        return repository.computeIfPresent(plan.getId(), (id, oldPlan) -> plan);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Plan get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Plan> getAll() {
        return repository.values();
    }
}
