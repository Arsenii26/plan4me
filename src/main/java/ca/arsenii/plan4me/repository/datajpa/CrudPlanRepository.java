package ca.arsenii.plan4me.repository.datajpa;


import ca.arsenii.plan4me.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudPlanRepository extends JpaRepository<Plan, Integer> {


    @Modifying
    @Transactional
    @Query("DELETE  FROM  Plan p WHERE p.id=:id AND p.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    @Override
    @Transactional
    Plan save(Plan plan);

    @Query("SELECT p FROM  Plan p WHERE p.user.id=:userId ORDER BY p.dateTime DESC ")
    List<Plan> getAll(@Param("userId") int userId);

    @SuppressWarnings("JpaQlInspection")
    @Query("SELECT p from Plan p WHERE p.user.id=:userId AND p.dateTime BETWEEN :startDate AND :endDate ORDER BY p.dateTime DESC ")
    List<Plan> getBetween(@Param("startDate")LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @Query("SELECT p FROM Plan p JOIN  FETCH  p.user WHERE  p.id = ?1 and p.user.id = ?2")
    Plan getWithUser(int id, int userId);
}
