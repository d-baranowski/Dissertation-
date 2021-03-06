package uk.ac.ncl.daniel.baranowski.models;

import javax.validation.constraints.Size;
import java.util.Objects;

public class CandidateModel {
    private int id;
    @Size(min=2, max=50, message="First name must be between 2 and 60 characters")
    private String firstName;
    @Size(min=2, max=50, message="Surname must be between 2 and 60 characters")
    private String surname;
    private boolean hasExtraTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getSurname();
    }

    public void setHasExtraTime(boolean hasExtraTime) {
        this.hasExtraTime = hasExtraTime;
    }

    public boolean hasId() {
        return id != 0;
    }

    @Override
    public final boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || !(o instanceof CandidateModel)) return false; //NOSONAR
        CandidateModel that = (CandidateModel) o;
        return getId() == that.getId() &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getSurname(), that.getSurname()) &&
                Objects.equals(getHasExtraTime(), that.getHasExtraTime());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getFirstName(), getSurname(), getHasExtraTime());
    }

    @Override
    public String toString() {
        return String.format("CandidateModel [id=%s, firstName=%s, surname=%s, hasExtraTime=%s]", id, firstName, surname, hasExtraTime);
    }

    public boolean getHasExtraTime() {
        return hasExtraTime;
    }
}
