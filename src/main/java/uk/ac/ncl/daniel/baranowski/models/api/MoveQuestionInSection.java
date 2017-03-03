package uk.ac.ncl.daniel.baranowski.models.api;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MoveQuestionInSection {
    @NotNull(message = "Section is is missing")
    @Min(value = 1, message = "Wrong section id")
    private int sectionId;
    @NotNull(message = "Section version Missing")
    @Min(value = 1, message = "Wrong section version")
    private int sectionVer;
    @NotNull(message = "New Reference Missing")
    @Min(value = 1, message = "New reference is wrong")
    private int newRef;
    @NotNull(message = "Question Id is missing")
    @Min(value = 1, message = "Question Id is wrong")
    private int questionId;
    @NotNull(message = "Question Version Number is missing")
    @Min(value = 1, message = "Question Version Number is wrong")
    private int questionVerNo;


    public MoveQuestionInSection() {
        sectionVer = 0;
        newRef = 0;
        sectionId = 0;
        questionId = 0;
        questionVerNo = 0;
    }

    public MoveQuestionInSection setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public MoveQuestionInSection setQuestionVerNo(int questionVerNo) {
        this.questionVerNo = questionVerNo;
        return this;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getQuestionVerNo() {
        return questionVerNo;
    }

    public int getNewRef() {
        return newRef;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getSectionVer() {
        return sectionVer;
    }

    public MoveQuestionInSection setSectionVer(int sectionVer) {
        this.sectionVer = sectionVer;
        return this;
    }

    public MoveQuestionInSection setNewRef(int newRef) {
        this.newRef = newRef;
        return this;
    }

    public MoveQuestionInSection setSectionId(int sectionId) {
        this.sectionId = sectionId;
        return this;
    }
}
