package ca.arsenii.plan4me.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Plan {

    private  Integer id;
    private final LocalDateTime localDateTime;
    private final String plan;

    public Plan(Integer id, LocalDateTime localDateTime, String plan) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.plan = plan;
    }

    public Plan(LocalDateTime localDateTime, String plan) {
        this(null, localDateTime, plan);
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    public boolean isNew() {
        return id == null;
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
