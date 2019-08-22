package ca.arsenii.plan4me.repository;

import ca.arsenii.plan4me.model.Plan;

import java.util.Collection;

public interface PlanRepository {
    Plan save(Plan plan);

    boolean delete(int id);

    Plan get(int id);

    Collection<Plan> getAll();
}
