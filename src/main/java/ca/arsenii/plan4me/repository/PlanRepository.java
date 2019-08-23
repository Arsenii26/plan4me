package ca.arsenii.plan4me.repository;

import ca.arsenii.plan4me.model.Plan;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface PlanRepository {
    // null if updated plan do not belong to userId
    Plan save(Plan plan, int userId);

    // false if plan do not belong to userId
    boolean delete(int id, int userId);

    // null if plan do not belong to userId
    Plan get(int id, int userId);

    // ORDERED dateTime desc
    List<Plan> getAll(int userId);

    List<Plan> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);

    default Plan getWithUser(int id, int userId) {
        throw new UnsupportedOperationException();
    }
}