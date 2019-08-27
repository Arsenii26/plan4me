package ca.arsenii.plan4me.to;

import java.time.LocalDateTime;
import java.util.Objects;

public class PlanTo extends BaseTo{

    private  LocalDateTime dateTime;
    private  String plan;


    public PlanTo(Integer id, LocalDateTime dateTime, String plan) {
        super(id);
        this.dateTime = dateTime;
        this.plan = plan;
    }

    public PlanTo() {
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getPlan() {
        return plan;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanTo that = (PlanTo) o;
        return
                Objects.equals(id, that.id) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(plan, that.plan);
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
