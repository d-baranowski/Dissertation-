package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * A person who attempted a test.
 */
public final class Candidate {
    private final int id;
    private final String name;
    private final String surname;

    private Candidate(Builder builder) {
        id = builder.id;
        name = builder.name;
        surname = builder.surname;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        Candidate candidate = (Candidate) o;
        return getId() == candidate.getId() && //NOSONAR
                Objects.equals(getName(), candidate.getName()) &&
                Objects.equals(getSurname(), candidate.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getSurname());
    }

    public static class Builder {
        private int id;
        private String name;
        private String surname;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public Candidate build() {
            return new Candidate(this);
        }
    }
}
