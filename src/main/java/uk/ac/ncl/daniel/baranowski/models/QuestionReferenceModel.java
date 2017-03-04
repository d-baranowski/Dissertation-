package uk.ac.ncl.daniel.baranowski.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class QuestionReferenceModel {
    private int id;
    private int versionNo;
    @Size(max = 100)
    private String language;
    @NotNull(message = "Please specify the reference name for this question.")
    @Size(min = 5, max = 100, message = "Reference name needs to be between 5 and 100 characters long. ")
    private String referenceName;
    @Min(value = 1, message = "Minimum difficulty is 1")
    @Max(value = 100, message = "Max difficulty is 100")
    private int difficulty;
    @Min(value = 0, message = "Minimum time scale is 0")
    private int timeScale;

    @Size(max = 20)
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(int timeScale) {
        this.timeScale = timeScale;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    public boolean canEqual(Object other) {
        return other instanceof QuestionReferenceModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || !(o instanceof QuestionReferenceModel)) return false; //NOSONAR
        QuestionReferenceModel that = (QuestionReferenceModel) o;
        return getId() == that.getId() &&  //NOSONAR
                Objects.equals(this.getDifficulty(), that.getDifficulty()) &&
                Objects.equals(this.getTimeScale(), that.getTimeScale()) &&
                getVersionNo() == that.getVersionNo() &&
                Objects.equals(getLanguage(), that.getLanguage()) &&
                Objects.equals(getReferenceName(), that.getReferenceName()) &&
                Objects.equals(getType(), that.getType()) &&
                that.canEqual(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVersionNo(), getLanguage(), getReferenceName(), getDifficulty(), getTimeScale(), getType());
    }

    @Override
    public String toString() {
        return String.format("QuestionReference [id=%s, versionNo=%s, language=%s, referenceName=%s]", id, versionNo,
                language, referenceName);
    }
}
