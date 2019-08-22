package ca.arsenii.plan4me.repository.jdbc;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.repository.PlanRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public class JdbcPlanRepository implements PlanRepository {
    private static final BeanPropertyRowMapper<Plan> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Plan.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcPlanRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("plans")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Plan save(Plan meal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("plan", meal.getPlan())
                .addValue("date_time", meal.getLocalDateTime())
                .addValue("user_id", userId);

        if (meal.isNew()) {
            Number newId = insertMeal.executeAndReturnKey(map);
            meal.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                            "UPDATE plans " +
                            "   SET plan=:plan,  date_time=:date_time " +
                            " WHERE id=:id AND user_id=:user_id"
                    , map) == 0) {
                return null;
            }
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM plans WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Plan get(int id, int userId) {
        List<Plan> meals = jdbcTemplate.query(
                "SELECT * FROM plans WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }
//    private SessionFactory sessionFactory;
    @Override
    public List<Plan> getAll(int userId) {
//        Session session = sessionFactory.getCurrentSession();
//        return session.createQuery("from plans").setFirstResult(10 * (page - 1)).setMaxResults(10).list();
        return jdbcTemplate.query(
                "SELECT * FROM plans WHERE user_id=? ORDER BY date_time DESC", ROW_MAPPER, userId);
    }

    @Override
    public List<Plan> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query(
                "SELECT * FROM plans WHERE user_id=?  AND date_time BETWEEN  ? AND ? ORDER BY date_time DESC",
                ROW_MAPPER, userId, startDate, endDate);
    }

}