package uk.ac.ncl.daniel.baranowski.models.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class AddQuestionToSection {
    @NotNull(message = "Question ID Missing")
    @Min(value = 1, message = "Wrong Question id")
    private int questionId;
    @NotNull(message = "Question Version Number Missing")
    @Min(value = 1, message = "Wrong Question Version Number")
    private int questionVersion;
    @NotNull(message = "Section ID Missing")
    @Min(value = 1, message = "Wrong Section id")
    private int sectionId;
    @NotNull(message = "Section Version Number Missing")
    @Min(value = 1, message = "Wrong Section Version Number")
    private int sectionVersion;

    public int getQuestionId() {
        return questionId;
    }

    public AddQuestionToSection setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public int getQuestionVersion() {
        return questionVersion;
    }

    public AddQuestionToSection setQuestionVersion(int questionVersion) {
        this.questionVersion = questionVersion;
        return this;
    }

    public int getSectionId() {
        return sectionId;
    }

    public AddQuestionToSection setSectionId(int sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public int getSectionVersion() {
        return sectionVersion;
    }

    public AddQuestionToSection setSectionVersion(int sectionVersion) {
        this.sectionVersion = sectionVersion;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddQuestionToSection)) return false;
        AddQuestionToSection that = (AddQuestionToSection) o;
        return getQuestionId() == that.getQuestionId() &&
                getQuestionVersion() == that.getQuestionVersion() &&
                getSectionId() == that.getSectionId() &&
                getSectionVersion() == that.getSectionVersion();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestionId(), getQuestionVersion(), getSectionId(), getSectionVersion());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddQuestionToSection{");
        sb.append("questionId=").append(questionId);
        sb.append(", questionVersion=").append(questionVersion);
        sb.append(", sectionId=").append(sectionId);
        sb.append(", sectionVersion=").append(sectionVersion);
        sb.append('}');
        return sb.toString();
    }
}
