package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * Papers can be edited and changed and we need to keep track of all old version of the tests.
 * All the information about the paper that can change will be kept in Paper Version.
 */
public final class PaperVersion {
    private final int versionNo;
    private final String authorId;
    private final int paperId;
    private final String instructionsText;

    private PaperVersion(Builder builder) {
        authorId = builder.authorId;
        paperId = builder.paperId;
        instructionsText = builder.instructionsText;
        versionNo = builder.versionNo;
    }

    public String getAuthorId() {
        return authorId;
    }

    public int getPaperId() {
        return paperId;
    }

    public String getInstructionsText() {
        return instructionsText;
    }

    public int getVersionNo() {
        return versionNo;
    }

    @Override
    public boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        PaperVersion that = (PaperVersion) o;
        return getVersionNo() == that.getVersionNo() && //NOSONAR
                getPaperId() == that.getPaperId() &&
                Objects.equals(getAuthorId(), that.getAuthorId()) &&
                Objects.equals(getInstructionsText(), that.getInstructionsText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVersionNo(), getAuthorId(), getPaperId(), getInstructionsText());
    }

    public static class Builder {
        private String instructionsText;
        private String authorId;
        private int paperId;
        private int versionNo;

        public Builder setVersionNo(int versionNo) {
            this.versionNo = versionNo;
            return this;
        }

        public Builder setAuthorId(String authorId) {
            this.authorId = authorId;
            return this;
        }

        public Builder setPaperId(int paperId) {
            this.paperId = paperId;
            return this;
        }

        public Builder setInstructionsText(String text) {
            this.instructionsText = text;
            return this;
        }

        public PaperVersion build() {
            return new PaperVersion(this);
        }
    }
}
