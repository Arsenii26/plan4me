package ca.arsenii.plan4me.repository.datajpa;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class DataJpaPlanRepository implements PlanRepository {

    @Autowired
    private CrudPlanRepository crudPlanRepository;

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    @Transactional
    public Plan save(Plan plan, int userId) {
        if(!plan.isNew() && get(plan.getId(), userId) == null){
            return null;
        }
        plan.setUser(crudUserRepository.getOne(userId));
        return crudPlanRepository.save(plan);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudPlanRepository.delete(id, userId) != 0;
    }

    @Override
    public Plan get(int id, int userId) {
        return crudPlanRepository.findById(id).filter(plan -> plan.getUser().getId() == userId).orElse(null);
    }

    @Override
    public List<Plan> getAll(int userId) {
        return crudPlanRepository.getAll(userId);
    }

    @Override
    public List<Plan> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudPlanRepository.getBetween(startDate, endDate, userId);
    }

    @Override
    public Plan getWithUser(int id, int userId) {
        return crudPlanRepository.getWithUser(id, userId);
    }
}
