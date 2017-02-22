package uk.ac.ncl.daniel.baranowski.models;

import org.joda.time.LocalTime;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class TestDayModel {
    private int id;
    @Size(min=10, max=10, message="Date is invalid")
    private String date;
    @Size(min=2, max=120, message="Location must be between 2 and 120 characters")
    private String location;

    @NotNull(message = "Pick a valid start time")
    private LocalTime startTime;

    private LocalTime endTime;
    private LocalTime endTimeWithExtraTime;

    public int getId() {
        return id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    public LocalTime getEndTimeWithExtraTime() {
        return endTimeWithExtraTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public TestDayModel setStartTime(LocalTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public TestDayModel setEndTime(LocalTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public TestDayModel setEndTimeWithExtraTime(LocalTime endTimeWithExtraTime) {
        this.endTimeWithExtraTime = endTimeWithExtraTime;
        return this;
    }

    public TestDayModel setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || !(o instanceof TestDayModel)) return false; //NOSONAR
        TestDayModel that = (TestDayModel) o;
        return Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getStartTime(), that.getStartTime()) &&
                Objects.equals(getEndTime(), that.getEndTime()) &&
                Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getDate(), getLocation(), getStartTime(), getEndTime());
    }

    @Override
    public String toString() {
        return String.format("TestDayModel [id= %s, date=%s,startTime=%s,endTime=%s ,location=%s]" ,id, date, startTime, endTime, location);
    }
}
