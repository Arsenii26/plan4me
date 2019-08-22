package ca.arsenii.plan4me.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Plan extends AbstractBaseEntity{


    private  LocalDateTime localDateTime;
    private  String plan;

    public Plan(Integer id, LocalDateTime localDateTime, String plan) {
        super(id);
        this.localDateTime = localDateTime;
        this.plan = plan;
    }

    public Plan() {
    }

    public Plan(LocalDateTime localDateTime, String plan) {
        this(null, localDateTime, plan);
    }


    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getPlan() {
        return plan;
    }

    public LocalDate getDate() {
        return localDateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return localDateTime.toLocalTime();
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", localDateTime=" + localDateTime +
                ", plan='" + plan + '\'' +
                '}';
    }

}
