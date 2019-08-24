package ca.arsenii.plan4me.web.plan;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.service.PlanService;
import ca.arsenii.plan4me.to.PlanTo;
import ca.arsenii.plan4me.util.PlansUtil;
import ca.arsenii.plan4me.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ca.arsenii.plan4me.util.ValidationUtil.assureIdConsistent;
import static ca.arsenii.plan4me.util.ValidationUtil.checkNew;
@RestController
@RequestMapping(value = PlanRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class PlanRestController extends AbstractPlanController{

    static final String REST_URL = "/rest/profile/plans";

    @Override
    @GetMapping("/{id}")
    public Plan get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<PlanTo> getAll() {
        return super.getAll();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Plan> createWithLocation(@RequestBody Plan plan) {
        Plan created = super.create(plan);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void update(@RequestBody Plan plan, @PathVariable int id) {
        super.update(plan, id);
    }

    @Override
    @GetMapping(value = "/filter")
    public List<PlanTo> getBetween(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalTime startTime,
            @RequestParam(required = false)LocalDate endDate,
            @RequestParam(required = false) LocalTime endTime) {
        return super.getBetween(startDate, startTime, endDate, endTime);
    }
}