package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * A day and location of a test. Used to group candidates that took the test during the same assessment day.
 */
public final class TestDay {
    private final int id;
    private final String date;
    private final String location;
    private final String startTime;
    private final String endTime;
    private final String endTimeWithExtraTime;

    private TestDay(Builder builder) {
        id = builder.id;
        date = builder.date;
        location = builder.location;
        endTimeWithExtraTime = builder.endTimeWithExtraTime;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
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

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getEndTimeWithExtraTime() {
        return endTimeWithExtraTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, location, startTime,
                endTime, endTimeWithExtraTime);
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
                    Objects.equals(startTime, that.getStartTime()) &&
                    Objects.equals(endTime, that.getEndTime()) &&
                    Objects.equals(endTimeWithExtraTime, that.getEndTimeWithExtraTime()) &&
                    Objects.equals(id, that.getId());
        }

        return false;
    }

    public static class Builder {
        private int id;
        private String date;
        private String location;
        private String startTime;
        private String endTime;
        private String endTimeWithExtraTime;

        public Builder setEndTimeWithExtraTime(String endTimeWithExtraTime) {
            this.endTimeWithExtraTime = endTimeWithExtraTime;
            return this;
        }

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

        public Builder setStartTime(String startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndLocalTime(String endDateTime) {
            this.endTime = endDateTime;
            return this;
        }

        public TestDay build() {
            return new TestDay(this);
        }
    }
}
