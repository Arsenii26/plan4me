package ca.arsenii.plan4me.to;

import java.time.LocalDateTime;
import java.util.Objects;

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

    //possible to change
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlanTo)) return false;
        PlanTo planTo = (PlanTo) o;
        return Objects.equals(getId(), planTo.getId()) &&
                Objects.equals(getDateTime(), planTo.getDateTime()) &&
                Objects.equals(getPlan(), planTo.getPlan());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateTime(), getPlan());
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
