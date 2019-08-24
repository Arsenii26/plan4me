package ca.arsenii.plan4me.web.plan;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.to.PlanTo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }


    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestParam Integer id,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                               @RequestParam String plan
                               ) {
        Plan meal = new Plan(id, dateTime, plan);
        if (meal.isNew()) {
            super.create(meal);
        }
//        Plan planObject = new Plan(id, dateTime, plan);
//        if (planObject.isNew()) {
//            super.create(planObject);
//        }
    }

    @Override
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlanTo> getBetween(@RequestParam(required = false) LocalDate startDate,
                                   @RequestParam(required = false) LocalTime startTime,
                                   @RequestParam(required = false) LocalDate endDate,
                                   @RequestParam(required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}
