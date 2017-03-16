package uk.ac.ncl.daniel.baranowski.models.websocket;

import uk.ac.ncl.daniel.baranowski.models.MarkModel;

public class MarkMessage {
    private MarkModel mark;
    private int testAttemptId;
    private int questionId;
    private int questionVersionNo;

    public MarkModel getMark() {
        return mark;
    }

    public MarkMessage setMark(MarkModel mark) {
        this.mark = mark;
        return this;
    }

    public int getTestAttemptId() {
        return testAttemptId;
    }

    public MarkMessage setTestAttemptId(int testAttemptId) {
        this.testAttemptId = testAttemptId;
        return this;
    }

    public int getQuestionId() {
        return questionId;
    }

    public MarkMessage setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public int getQuestionVersionNo() {
        return questionVersionNo;
    }

    public MarkMessage setQuestionVersionNo(int questionVersionNo) {
        this.questionVersionNo = questionVersionNo;
        return this;
    }
}
