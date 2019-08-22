package ca.arsenii.plan4me.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Plan extends AbstractBaseEntity{


    private  LocalDateTime dateTime;
    private  String plan;

    public Plan(Integer id, LocalDateTime dateTime, String plan) {
        super(id);
        this.dateTime = dateTime;
        this.plan = plan;
    }

    public Plan() {
    }

    public Plan(LocalDateTime dateTime, String plan) {
        this(null, dateTime, plan);
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

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", plan='" + plan + '\'' +
                '}';
    }

}
