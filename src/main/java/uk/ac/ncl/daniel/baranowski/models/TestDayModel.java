package uk.ac.ncl.daniel.baranowski.models;

import java.util.Objects;
import javax.validation.constraints.Size;

public class TestDayModel {
    @Size(min=10, max=10, message="Date is invalid")
    private String date;
    @Size(min=2, max=120, message="Location must be between 2 and 120 characters")
    private String location;

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

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || !(o instanceof TestDayModel)) return false; //NOSONAR
        TestDayModel that = (TestDayModel) o;
        return Objects.equals(getDate(), that.getDate()) &&
                Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getDate(), getLocation());
    }

    @Override
    public String toString() {
        return String.format("TestDayModel [date=%s, location=%s]", date, location);
    }
}
