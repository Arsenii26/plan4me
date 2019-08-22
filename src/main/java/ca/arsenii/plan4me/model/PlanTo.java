package ca.arsenii.plan4me.model;

import java.time.LocalDateTime;


public class PlanTo {
    private final Integer id;
    private final LocalDateTime localDateTime;
    private final String plan;

    public PlanTo(Integer id, LocalDateTime localDateTime, String plan) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.plan = plan;
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


    @Override
    public String toString() {
        return "PlanTo{" +
                "id=" + id +
                ", localDateTime=" + localDateTime +
                ", plan=" + plan  +
                '}';
    }
}
