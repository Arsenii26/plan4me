package ca.arsenii.plan4me.to;

import java.time.LocalDateTime;

public class PlanTo {
    private final Integer id;
    private final LocalDateTime dateTime;
    private final String plan;

    public PlanTo(Integer id, LocalDateTime dateTime, String plan) {
        this.id = id;
        this.dateTime = dateTime;
        this.plan = plan;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getPlan() {
        return plan;
    }


    @Override
    public String toString() {
        return "PlanTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", plan=" + plan  +
                '}';
    }
}
