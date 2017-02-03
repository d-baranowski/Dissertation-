package uk.ac.ncl.daniel.baranowski.models.testattempt;

import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;

import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubmitAnswerFormModel {
    private static final String ENDPOINT = ControllerEndpoints.ATTEMPT_PREFIX + ControllerEndpoints.ATTEMPT_QUESTION_SUBMIT;
    @NotNull
    private int attemptId;
    @NotNull
    private int questionId;
    @NotNull
    private int questionVersionNo;
    @Size(min=1, max=5000, message = "Answer needs to be between 1 and 5000 characters")
    @NotNull(message = "Please provide an answer before submitting")
    private String text;
    private String base64imagePng;

    public static String getENDPOINT() { //NOSONAR User inside thymeleaf template
        return ENDPOINT;
    }

    public int getAttemptId() {
        return attemptId;
    }

    public SubmitAnswerFormModel setAttemptId(int attemptId) {
        this.attemptId = attemptId;
        return this;
    }

    public int getQuestionId() {
        return questionId;
    }

    public SubmitAnswerFormModel setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public int getQuestionVersionNo() {
        return questionVersionNo;
    }

    public SubmitAnswerFormModel setQuestionVersionNo(int questionVersionNo) {
        this.questionVersionNo = questionVersionNo;
        return this;
    }

    public String getText() {
        return text;
    }

    public SubmitAnswerFormModel setText(String text) {
        this.text = text;
        return this;
    }

    public String getBase64imagePng() {
        return base64imagePng;
    }

    public SubmitAnswerFormModel setBase64imagePng(String base64imagePng) {
        this.base64imagePng = base64imagePng;
        return this;
    }

    @Override
    public boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        SubmitAnswerFormModel that = (SubmitAnswerFormModel) o;
        return getAttemptId() == that.getAttemptId() && //NOSONAR
                getQuestionId() == that.getQuestionId() &&
                getQuestionVersionNo() == that.getQuestionVersionNo() &&
                Objects.equals(getText(), that.getText()) &&
                Objects.equals(getBase64imagePng(), that.getBase64imagePng());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttemptId(), getQuestionId(), getQuestionVersionNo(), getText(), getBase64imagePng());
    }

    @Override
    public String toString() {
        return "SubmitAnswerFormModel{" +
                "attemptId=" + attemptId +
                ", questionId=" + questionId +
                ", questionVersionNo=" + questionVersionNo +
                ", text='" + text + '\'' +
                ", base64imagePng='" + base64imagePng + '\'' +
                '}';
    }
}
