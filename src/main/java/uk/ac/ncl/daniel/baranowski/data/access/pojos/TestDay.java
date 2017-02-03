package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * A day and location of a test. Used to group candidates that took the test during the same assessment day.
 */
public final class TestDay {
    private final int id;
    private final String date;
    private final String location;

    private TestDay(Builder builder) {
        id = builder.id;
        date = builder.date;
        location = builder.location;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, location);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof TestDay) {
            TestDay that = (TestDay) object;
            return Objects.equals(date, that.getDate()) &&
                    Objects.equals(location, that.getLocation()) &&
                    Objects.equals(id, that.getId());
        }

        return false;
    }

    public static class Builder {
        private int id;
        private String date;
        private String location;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setDate(String name) {
            this.date = name;
            return this;
        }

        public Builder setLocation(String surname) {
            this.location = surname;
            return this;
        }

        public TestDay build() {
            return new TestDay(this);
        }
    }
}
