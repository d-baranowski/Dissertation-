package uk.ac.ncl.daniel.baranowski.models;

import org.joda.time.LocalTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class TestDayModel {
    private int id;
    @Size(min=10, max=10, message="Date is invalid")
    private String date;
    @Size(min=2, max=120, message="Location must be between 2 and 120 characters")
    private String location;

    @NotNull(message = "Pick a valid start time")
    private String startTime;

    private String endTime;
    private String endTimeWithExtraTime;

    public int getId() {
        return id;
    }

    public LocalTime getStartTimeAsLocalTime() {
        return  startTime != null ? LocalTime.parse(startTime) : null;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getEndTimeWithExtraTime() {
        return endTimeWithExtraTime;
    }

    public LocalTime getEndTimeAsLocalTime() {
        return  endTime != null ? LocalTime.parse(endTime) : null;
    }
    public LocalTime getEndTimeWithExtraTimeAsLocalTime() {
        return  endTimeWithExtraTime != null ?  LocalTime.parse(endTimeWithExtraTime) : null;
    }

    public String getDate() {
        return date;
    }

    public TestDayModel setDate(String date) {
        this.date = date;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public TestDayModel setStartTime(LocalTime startTime) {
        this.startTime = startTime.toString("HH:mm");
        return this;
    }

    public TestDayModel setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public TestDayModel setEndTime(LocalTime endTime) {
        this.endTime = endTime.toString("HH:mm");
        return this;
    }
    public TestDayModel setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }


    public TestDayModel setEndTimeWithExtraTime(LocalTime endTimeWithExtraTime) {
        this.endTimeWithExtraTime = endTimeWithExtraTime.toString("HH:mm");
        return this;
    }

    public TestDayModel setEndTimeWithExtraTime(String endTimeWithExtraTime) {
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
                Objects.equals(getEndTimeWithExtraTime(), that.getEndTimeWithExtraTime()) &&
                Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getDate(), getLocation(), getStartTime(), getEndTime(), getEndTimeWithExtraTime());
    }

    @Override
    public String toString() {
        return String.format("TestDayModel [id= %s, date=%s,startTime=%s,endTime=%s ,location=%s]" ,id, date, startTime, endTime, location);
    }
}
