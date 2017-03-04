package uk.ac.ncl.daniel.baranowski.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SectionReferenceModel {
    private int id;
    private int versionNumber;
    @NotNull(message = "Please specify the reference name for this section.")
    @Size(min = 5, max = 100, message = "Reference name needs to be between 5 and 100 characters long. ")
    private String referenceName;
    @Min(value = 1, message = "Minimum number of questions to answer is 1")
    @Max(value = 100, message = "Maximum number of questions to answer is 99")
    private int noOfQuestionsToAnswer;
    @Min(value = 1, message = "Minimum time scale is 1")
    @Max(value = 100, message = "Maximum time scale is 99")
    private int timeScale;

    public SectionReferenceModel() {
        id = 0;
        versionNumber = 0;
        referenceName = "";
        noOfQuestionsToAnswer = 0;
        timeScale = 0;
    }

    public int getNoOfQuestionsToAnswer() {
        return noOfQuestionsToAnswer;
    }

    public SectionReferenceModel setNoOfQuestionsToAnswer(int noOfQuestionsToAnswer) {
        this.noOfQuestionsToAnswer = noOfQuestionsToAnswer;
        return this;
    }

    public int getTimeScale() {
        return timeScale;
    }

    public SectionReferenceModel setTimeScale(int timeScale) {
        this.timeScale = timeScale;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    public boolean canEqual(Object other) {
        return other instanceof SectionReferenceModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || !(o instanceof SectionReferenceModel)) return false; //NOSONAR
        SectionReferenceModel that = (SectionReferenceModel) o;
        return getId() == that.getId() &&
                getNoOfQuestionsToAnswer() == that.getNoOfQuestionsToAnswer() &&
                getVersionNumber() == that.getVersionNumber() &&
                getTimeScale() == that.getTimeScale() &&
                Objects.equals(getReferenceName(), that.getReferenceName()) &&
                that.canEqual(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVersionNumber(), getReferenceName(), getNoOfQuestionsToAnswer(), getTimeScale());
    }

    @Override
    public String toString() {
        return String.format("SectionReference [id=%s, versionNumber=%s, referenceName=%s, noOfQuestionsToAnswer=%s, timeScale=%s]", id, versionNumber,
                referenceName, noOfQuestionsToAnswer, timeScale);
    }
}
