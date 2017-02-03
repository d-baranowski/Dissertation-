package uk.ac.ncl.daniel.baranowski.models;

import java.util.Map;
import java.util.Objects;

public final class SectionModel extends SectionReferenceModel {
    private String instructionsText;
    private int noOfQuestionsToAnswer;
    private int timeScale;
    private Map<Integer, QuestionModel> questions;

    public String getInstructionsText() {
        return instructionsText;
    }

    public void setInstructionsText(String instructionsText) {
        this.instructionsText = instructionsText;
    }

    public Map<Integer, QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(Map<Integer, QuestionModel> questions) {
        this.questions = questions;
    }

    public int getNoOfQuestionsToAnswer() {
        return noOfQuestionsToAnswer;
    }

    public void setNoOfQuestionsToAnswer(int noOfQuestionsToAnswer) {
        this.noOfQuestionsToAnswer = noOfQuestionsToAnswer;
    }

    public int getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(int timeScale) {
        this.timeScale = timeScale;
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    @Override
    public final boolean canEqual(Object other) {
        return other instanceof SectionModel;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        if (!super.equals(o)) return false; //NOSONAR
        SectionModel that = (SectionModel) o;
        return Objects.equals(getInstructionsText(), that.getInstructionsText()) && //NOSONAR
                Objects.equals(getQuestions(), that.getQuestions()) &&
                Objects.equals(getTimeScale(), that.getTimeScale()) &&
                Objects.equals(getNoOfQuestionsToAnswer(), that.getNoOfQuestionsToAnswer()) &&
                this.canEqual(that);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(super.hashCode(), getInstructionsText(), getQuestions(), getNoOfQuestionsToAnswer(), getTimeScale());
    }

    @Override
    public String toString() {
        return "SectionModel{" +
                "instructionsText='" + instructionsText + '\'' +
                ", questions=" + questions +
                '}';
    }
}
