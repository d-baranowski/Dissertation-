package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * Sections can be edited and changed and we need to keep track of all old version of the section.
 * All the information about the section that can change will be kept in Section Version.
 */
public final class SectionVersion {
    private final int sectionId;
    private final int versionNo;
    private final int noOfQuestionsToAnswer;
    private final String description;
    private final int timeScale;

    private SectionVersion(Builder builder) {
        noOfQuestionsToAnswer = builder.noOfQuestionsToAnswer;
        description = builder.description;
        timeScale = builder.timeScale;
        sectionId = builder.sectionId;
        versionNo = builder.versionNo;
    }

    public int getNoOfQuestionsToAnswer() {
        return noOfQuestionsToAnswer;
    }

    public String getDescription() {
        return description;
    }

    public int getTimeScale() {
        return timeScale;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getVersionNo() {
        return versionNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        SectionVersion that = (SectionVersion) o;
        return getSectionId() == that.getSectionId() && //NOSONAR
                getVersionNo() == that.getVersionNo() &&
                getNoOfQuestionsToAnswer() == that.getNoOfQuestionsToAnswer() &&
                getTimeScale() == that.getTimeScale() &&
                Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSectionId(), getVersionNo(), getNoOfQuestionsToAnswer(), getDescription(), getTimeScale());
    }

    public static class Builder {
        private int noOfQuestionsToAnswer;
        private String description;
        private int timeScale;
        private int sectionId;
        private int versionNo;

        public Builder setVersionNo(int versionNo) {
            this.versionNo = versionNo;
            return this;
        }

        public Builder setNoOfQuestionsToAnswer(int noOfQuestionsToAnswer) {
            this.noOfQuestionsToAnswer = noOfQuestionsToAnswer;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setTimeScale(int timeScale) {
            this.timeScale = timeScale;
            return this;
        }

        public Builder setSectionId(int sectionId) {
            this.sectionId = sectionId;
            return this;
        }

        public SectionVersion build() {
            return new SectionVersion(this);
        }
    }
}
