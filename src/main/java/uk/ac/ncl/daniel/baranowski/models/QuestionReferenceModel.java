package uk.ac.ncl.daniel.baranowski.models;

import java.util.Objects;

public class QuestionReferenceModel {
    private int id;
    private int versionNo;
    private String language;
    private String referenceName;
    private int difficulty;
    private int timeScale;
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
