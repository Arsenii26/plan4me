package ca.arsenii.plan4me.service;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ca.arsenii.plan4me.util.DateTimeUtil.adjustEndDateTime;
import static ca.arsenii.plan4me.util.DateTimeUtil.adjustStartDateTime;
import static ca.arsenii.plan4me.util.ValidationUtil.checkNotFoundWithId;

@Service
public class PlanService {
    private final PlanRepository repository;

    @Autowired
    public PlanService(PlanRepository repository) {
        this.repository = repository;
    }

    public Plan get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    //@Nullable makes it clear that the method accepts null values
    public List<Plan> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return getBetweenDateTimes(adjustStartDateTime(startDate), adjustEndDateTime(endDate), userId);
    }

    public List<Plan> getBetweenDateTimes(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        Assert.notNull(startDateTime, "startDateTime must not be null");
        Assert.notNull(endDateTime, "endDateTime  must not be null");
        return repository.getBetween(startDateTime, endDateTime, userId);
    }

    public List<Plan> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Plan plan, int userId) {
        Assert.notNull(plan, "plan must not be null");
        checkNotFoundWithId(repository.save(plan, userId), plan.getId());
    }

    public Plan create(Plan plan, int userId) {
        Assert.notNull(plan, "plan must not be null");
        return repository.save(plan, userId);
    }

    //for JUnit tests
    public Plan getWithUser(int id, int userId){  //for tests
        return checkNotFoundWithId(repository.getWithUser(id, userId), id);
    }
}