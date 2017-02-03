package uk.ac.ncl.daniel.baranowski.models;

import java.util.Objects;
import javax.validation.constraints.Size;

public class CandidateModel {
    private int id;
    @Size(min=2, max=50, message="First name must be between 2 and 60 characters")
    private String firstName;
    @Size(min=2, max=50, message="Surname must be between 2 and 60 characters")
    private String surname;

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
                Objects.equals(getSurname(), that.getSurname());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getFirstName(), getSurname());
    }

    @Override
    public String toString() {
        return String.format("CandidateModel [id=%s, firstName=%s, surname=%s]", id, firstName, surname);
    }
}
