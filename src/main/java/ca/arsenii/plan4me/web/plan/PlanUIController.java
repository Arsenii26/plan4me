package ca.arsenii.plan4me.web.plan;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.to.PlanTo;
import ca.arsenii.plan4me.util.ValidationUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/profile/plans")
public class PlanUIController extends AbstractPlanController{

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlanTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}")
    public Plan get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Valid Plan plan) {
        if (plan.isNew()) {
            super.create(plan);
        } else {
            super.update(plan, plan.getId());
        }
    }

    @Override
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlanTo> getBetween(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalTime startTime,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}