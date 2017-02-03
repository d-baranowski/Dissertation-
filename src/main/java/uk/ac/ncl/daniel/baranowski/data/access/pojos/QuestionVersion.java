package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * Questions can be edited and changed and we need to keep track of all old version of the question.
 * All the information about the question that can change will be kept in Question Version.
 */
public final class QuestionVersion {
    private final int questionId;
    private final int versionNo;
    private final String text;
    private final String correctAnswer;
    private final String markingGuide;
    private final int timeScale;

    private QuestionVersion(Builder builder) {
        questionId = builder.questionId;
        text = builder.text;
        correctAnswer = builder.correctAnswer;
        markingGuide = builder.markingGuide;
        timeScale = builder.timeScale;
        versionNo = builder.versionNo;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getMarkingGuide() {
        return markingGuide;
    }

    public int getTimeScale() {
        return timeScale;
    }

    public int getVersionNo() {
        return versionNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(questionId, text, correctAnswer, markingGuide, timeScale, versionNo);
    }

    @Override
    public boolean equals(final Object object) {
        if (this == object) {
            return true;
        }

        if (object instanceof QuestionVersion) {
            QuestionVersion that = (QuestionVersion) object;
            return Objects.equals(versionNo, that.getVersionNo()) && // NOSONAR We need to compare all fields
                    Objects.equals(questionId, that.getQuestionId()) &&
                    Objects.equals(text, that.getText()) &&
                    Objects.equals(correctAnswer, that.getCorrectAnswer()) &&
                    Objects.equals(markingGuide, that.getMarkingGuide()) &&
                    Objects.equals(timeScale, that.getTimeScale());
        }

        return false;
    }

    @Override
    public String toString() {
        return String.format(
                "QuestionVersion [questionId=%s, versionNo=%s, text=%s, correctAnswer=%s, markingGuide=%s, timeScale=%s]",
                questionId, versionNo, text, correctAnswer, markingGuide, timeScale);
    }

    public static class Builder {
        private int versionNo;
        private int questionId;
        private String text;
        private String correctAnswer;
        private String markingGuide;
        private int timeScale;

        public Builder setVersionNo(int versionNo) {
            this.versionNo = versionNo;
            return this;
        }

        public Builder setQuestionId(int questionId) {
            this.questionId = questionId;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
            return this;
        }

        public Builder setMarkingGuide(String markingQuide) {
            this.markingGuide = markingQuide;
            return this;
        }

        public Builder setTimeScale(int timeScale) {
            this.timeScale = timeScale;
            return this;
        }

        public QuestionVersion build() {
            return new QuestionVersion(this);
        }
    }
}
