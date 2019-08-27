package ca.arsenii.plan4me.repository.jdbc;

import ca.arsenii.plan4me.model.Plan;
import ca.arsenii.plan4me.repository.PlanRepository;
import ca.arsenii.plan4me.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Repository
@Transactional(readOnly = true)
public class JdbcPlanRepository implements PlanRepository {
//    private static final BeanPropertyRowMapper<Plan> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Plan.class);
    private static final RowMapper<Plan> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Plan.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertPlan;

    @Autowired
    public JdbcPlanRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertPlan = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("plans")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public Plan save(Plan plan, int userId) {

        ValidationUtil.validate(plan);

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", plan.getId())
                .addValue("plan", plan.getPlan())
                .addValue("date_time", plan.getDateTime())
                .addValue("user_id", userId);

        if (plan.isNew()) {
            Number newId = insertPlan.executeAndReturnKey(map);
            plan.setId(newId.intValue());
        } else {
            if (namedParameterJdbcTemplate.update("" +
                            "UPDATE plans " +
                            "   SET plan=:plan,  date_time=:date_time " +
                            " WHERE id=:id AND user_id=:user_id"
                    , map) == 0) {
                return null;
            }
        }
        return plan;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM plans WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public Plan get(int id, int userId) {
        List<Plan> plans = jdbcTemplate.query(
                "SELECT * FROM plans WHERE id = ? AND user_id = ?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(plans);
    }

    @Override
    public List<Plan> getAll(int userId) {
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