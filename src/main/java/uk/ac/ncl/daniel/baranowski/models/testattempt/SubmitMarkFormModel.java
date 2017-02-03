package uk.ac.ncl.daniel.baranowski.models.testattempt;

import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SubmitMarkFormModel {
    private static final String ENDPOINT = ControllerEndpoints.ATTEMPT_PREFIX + ControllerEndpoints.ATTEMPT_MARK_SUBMIT;
    @NotNull(message = "Test Attempt Id is null. Try reloading the page. If problem persists contact system admin")
    private int testAttemptId;
    @NotNull(message = "Question Id is null. Try reloading the page. If problem persists contact system admin")
    private int questionId;
    @NotNull(message = "Question Version Number is null. Try reloading the page. If problem persists contact system admin")
    private int questionVersionNo;
    @Min(value = 0, message = "Please provide a mark before submitting")
    private int mark;
    @Size(max = 5000)
    private String comment;

    public int getTestAttemptId() {
        return testAttemptId;
    }

    public void setTestAttemptId(int testAttemptId) {
        this.testAttemptId = testAttemptId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getQuestionVersionNo() {
        return questionVersionNo;
    }

    public void setQuestionVersionNo(int questionVersionNo) {
        this.questionVersionNo = questionVersionNo;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public static String getENDPOINT() {
        return ENDPOINT;
    }
}
