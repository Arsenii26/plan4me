package ca.arsenii.plan4me.model;


import ca.arsenii.plan4me.View;
import ca.arsenii.plan4me.util.DateTimeUtil;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@SuppressWarnings("JpaQlInspection")  //hides some issues in sql code
@NamedQueries({
        @javax.persistence.NamedQuery(name = Plan.ALL_SORTED, query = "SELECT p FROM Plan p WHERE p.user.id=:userId ORDER BY p.dateTime DESC"),
        @javax.persistence.NamedQuery(name = Plan.DELETE, query = "DELETE FROM Plan p WHERE p.id=:id AND p.user.id=:userId"),
        @NamedQuery(name = Plan.GET_BETWEEN, query = "SELECT p FROM Plan p " +
                "WHERE p.user.id=:userId AND p.dateTime BETWEEN :startDate AND :endDate ORDER BY p.dateTime DESC"),
})
@Entity
@Table(name = "plans", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"}, name = "plans_unique_user_datetime_idx")})
public class Plan extends AbstractBaseEntity{

    public static final String ALL_SORTED = "Plan.getAll";
    public static final String DELETE = "Plan.delete";
    public static final String GET_BETWEEN = "Plan.getBetween";

    @Column(name = "date_time", nullable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime dateTime;

    @Column(name = "plan", nullable = false)
    @NotBlank
    @Size(min = 2, max = 200)
    @SafeHtml(groups = {View.Web.class})
    private  String plan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private User user;

    public Plan() {
    }

    public Plan(LocalDateTime dateTime, String plan) {
        this(null, dateTime, plan);
    }

    public Plan(Integer id, LocalDateTime dateTime, String plan) {
        super(id);
        this.dateTime = dateTime;
        this.plan = plan;
    }




    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getPlan() {
        return plan;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

//    public void setLocalDateTime(LocalDateTime dateTime) {  //in this case date is empty
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", plan='" + plan +
                '}';
    }

}
