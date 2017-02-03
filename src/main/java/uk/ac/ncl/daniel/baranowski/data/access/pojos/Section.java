package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * Section in a paper can contain any number of questions.
 * Sometimes not all the questions stored inside the paper need to be answered.
 */
public final class Section {
    private final int id;
    private final String referenceName;

    private Section(Builder builder) {
        id = builder.id;
        referenceName = builder.referenceName;
    }

    public int getId() {
        return id;
    }

    public String getReferenceName() {
        return referenceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        Section section = (Section) o;
        return getId() == section.getId() &&
                Objects.equals(getReferenceName(), section.getReferenceName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getReferenceName());
    }

    public static class Builder {
        private int id;
        private String referenceName;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setReferenceName(String referenceName) {
            this.referenceName = referenceName;
            return this;
        }

        public Section build() {
            return new Section(this);
        }
    }
}
