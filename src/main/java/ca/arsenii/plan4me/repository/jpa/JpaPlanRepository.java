package ca.arsenii.plan4me.repository.jpa;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.model.User;
import ca.arsenii.plan4me.repository.PlanRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaPlanRepository implements PlanRepository{

    //1)Entities are managed by javax.persistence.EntityManager instance using persistence context.
    //2)Each EntityManager instance is associated with a persistence context.
    //3)Within the persistence context, the entity instances and their lifecycle are managed.
    //4)Persistence context defines a scope under which particular entity instances are created, persisted, and removed.
    //5)A persistence context is like a cache which contains a set of persistent entities ,
    // So once the transaction is finished, all persistent objects are detached from the EntityManager's persistence context
    // and are no longer managed.
        @PersistenceContext
        private EntityManager em;

        @Override
        @Transactional
        public Plan save(Plan plan, int userId) {
            if (!plan.isNew() && get(plan.getId(), userId) == null) {
                return null;
            }
            plan.setUser(em.getReference(User.class, userId));
            if (plan.isNew()) {
                em.persist(plan);
                return plan;
            } else {
                return em.merge(plan);
            }
        }

        @Override
        @Transactional
        public boolean delete(int id, int userId) {
            return em.createNamedQuery(Plan.DELETE)
                    .setParameter("id", id)
                    .setParameter("userId", userId)
                    .executeUpdate() != 0;
        }

        @Override
        public Plan get(int id, int userId) {
            Plan plan = em.find(Plan.class, id);
            return plan != null && plan.getUser().getId() == userId ? plan : null;
        }

        @Override
        public List<Plan> getAll(int userId) {
            return em.createNamedQuery(Plan.ALL_SORTED, Plan.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }

        @Override
        public List<Plan> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
            return em.createNamedQuery(Plan.GET_BETWEEN, Plan.class)
                    .setParameter("userId", userId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate).getResultList();
        }
    }