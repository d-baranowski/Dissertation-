package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * Mark given by a logged in marker to an answer. Mark also contains a comment.
 */
public final class Mark {
    private final int id;
    private final String markerId;
    private final String comment;
    private final int actualMark;

    private Mark(Builder builder) {
        id = builder.id;
        markerId = builder.markerId;
        comment = builder.comment;
        actualMark = builder.actualMark;
    }

    public int getId() {
        return id;
    }

    public String getMarkerId() {
        return markerId;
    }

    public String getComment() {
        return comment;
    }

    public int getActualMark() {
        return actualMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        Mark mark = (Mark) o;
        return id == mark.id &&
                actualMark == mark.actualMark &&
                Objects.equals(markerId, mark.markerId) &&
                Objects.equals(comment, mark.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, markerId, comment, actualMark);
    }

    public static class Builder {
        private int id;
        private String markerId;
        private String comment;
        private int actualMark;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setMarkerId(String markerId) {
            this.markerId = markerId;
            return this;
        }

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setActualMark(int actualMark) {
            this.actualMark = actualMark;
            return this;
        }

        public Mark build() {
            return new Mark(this);
        }
    }
}
