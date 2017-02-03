package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * A test given to the candidate. For example Java Level 2 Exam Paper.
 */
public final class Paper {
    private final int id;
    private final String name;
    private final Integer timeAllowed;

    private Paper(Builder builder) {
        id = builder.id;
        name = builder.name;
        timeAllowed = builder.timeAllowed;
    }

    public Integer getTimeAllowed() {
        return timeAllowed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, timeAllowed);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof Paper) {
            Paper that = (Paper) object;
            return Objects.equals(name, that.getName()) &&
                    Objects.equals(id, that.getId()) &&
                    Objects.equals(timeAllowed, that.timeAllowed);
        }

        return false;
    }

    public static class Builder {
        private int id;
        private String name;
        private Integer timeAllowed;

        public Builder setTimeAllowed(Integer timeAllowed) {
            this.timeAllowed = timeAllowed;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Paper build() {
            return new Paper(this);
        }
    }
}
