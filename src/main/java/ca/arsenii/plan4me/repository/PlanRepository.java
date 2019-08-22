package ca.arsenii.plan4me.repository;

import ca.arsenii.plan4me.model.Plan;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface PlanRepository {
    // null if updated meal do not belong to userId
    Plan save(Plan meal, int userId);

    // false if meal do not belong to userId
    boolean delete(int id, int userId);

    // null if meal do not belong to userId
    Plan get(int id, int userId);

    // ORDERED dateTime desc
    List<Plan> getAll(int userId);

    List<Plan> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId);
}