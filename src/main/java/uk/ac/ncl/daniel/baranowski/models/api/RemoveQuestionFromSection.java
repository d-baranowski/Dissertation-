package uk.ac.ncl.daniel.baranowski.models.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RemoveQuestionFromSection {
    @NotNull(message = "Question ID Missing")
    @Min(value = 1, message = "Wrong Question id")
    private int questionId;
    @NotNull(message = "Question Version Missing")
    @Min(value = 1, message = "Wrong Question version")
    private int questionVersion;
    @NotNull(message = "Section ID Missing")
    @Min(value = 1, message = "Wrong Section id")
    private int sectionId;
    @NotNull(message = "Section Version Missing")
    @Min(value = 1, message = "Wrong Section version")
    private int sectionVersion;
    @NotNull(message = "Question number Missing")
    @Min(value = 1, message = "Wrong Question number")
    private int questionNo;

    public RemoveQuestionFromSection() {
        this.questionId = 0;
        this.questionVersion = 0;
        this.sectionId = 0;
        this.sectionVersion = 0;
        this.questionNo = 0;
    }

    public RemoveQuestionFromSection setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
        return this;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getQuestionVersion() {
        return questionVersion;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getSectionVersion() {
        return sectionVersion;
    }

    public RemoveQuestionFromSection setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public RemoveQuestionFromSection setQuestionVersion(int questionVersion) {
        this.questionVersion = questionVersion;
        return this;
    }

    public RemoveQuestionFromSection setSectionId(int sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public RemoveQuestionFromSection setSectionVersion(int sectionVersion) {
        this.sectionVersion = sectionVersion;
        return this;
    }

    public int getQuestionNo() {
        return questionNo;
    }
}
