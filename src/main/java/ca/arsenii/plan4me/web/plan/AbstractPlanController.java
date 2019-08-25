package ca.arsenii.plan4me.web.plan;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.service.PlanService;
import ca.arsenii.plan4me.to.PlanTo;
import ca.arsenii.plan4me.util.PlansUtil;
import ca.arsenii.plan4me.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ca.arsenii.plan4me.util.ValidationUtil.assureIdConsistent;
import static ca.arsenii.plan4me.util.ValidationUtil.checkNew;

public abstract class AbstractPlanController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PlanService service;


    public Plan get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get plan {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete plan {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<PlanTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return PlansUtil.getPlans(service.getAll(userId));
    }

    public Plan create(Plan plan) {
        int userId = SecurityUtil.authUserId();
        checkNew(plan);
        log.info("create {} for user {}", plan, userId);
        return service.create(plan, userId);
    }

    public void update(Plan plan, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(plan, id);
        log.info("update {} for user {}", plan, userId);
        service.update(plan, userId);
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    public List<PlanTo> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Plan> plansDateFiltered = service.getBetweenDates(startDate, endDate, userId);
        return PlansUtil.getFilteredWithExcess(plansDateFiltered, startTime, endTime);
    }
}
