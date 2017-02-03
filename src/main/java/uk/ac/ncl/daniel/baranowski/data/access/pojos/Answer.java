package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * Candidate giving an answer to a particular version of a question.
 * We need to know which past version of the question was the answer given to for archival purposes.
 */
public final class Answer {
    private final int questionVersionNo;
    private final int questionId;
    private final int testDayEntryId;
    private final String text;
    private final Integer markId;

    private Answer(Builder builder) {
        questionVersionNo = builder.questionVersionNo;
        questionId = builder.questionId;
        testDayEntryId = builder.testDayEntryId;
        text = builder.text;
        markId = builder.markId;
    }

    public int getTestDayEntryId() {
        return testDayEntryId;
    }

    public int getQuestionVersionNo() {
        return questionVersionNo;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getText() {
        return text;
    }

    public Integer getMarkId() {
        return markId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Answer answer = (Answer) o;
        return getQuestionVersionNo() == answer.getQuestionVersionNo() && //NOSONAR This is a standard equals method
                getQuestionId() == answer.getQuestionId() &&
                getTestDayEntryId() == answer.getTestDayEntryId() &&
                Objects.equals(getText(), answer.getText()) &&
                Objects.equals(getMarkId(), answer.getMarkId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuestionVersionNo(), getQuestionId(), getTestDayEntryId(), getText(), getMarkId());
    }

    @Override
    public String toString() {
        return "Answer{" +
                "questionVersionNo=" + questionVersionNo +
                ", questionId=" + questionId +
                ", testDayEntryId=" + testDayEntryId +
                ", text='" + text + '\'' +
                ", markId=" + markId +
                '}';
    }

    public static class Builder {
        private int questionId;
        private int questionVersionNo;
        private int testDayEntryId;
        private String text;
        private Integer markId;

        public Builder setQuesionVersionNo(int questionVersionNo) {
            this.questionVersionNo = questionVersionNo;
            return this;
        }

        public Builder setQuesionId(int questionId) {
            this.questionId = questionId;
            return this;
        }

        public Builder setTestDayEntryId(int testDayEntryId) {
            this.testDayEntryId = testDayEntryId;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setMarkId(Integer markId) {
            this.markId = markId;
            return this;
        }

        public Answer build() {
            return new Answer(this);
        }
    }
}
