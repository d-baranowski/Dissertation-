package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

public class QuestionVersionEntry {
    private int sectionVersionNo;
    private int sectionId;
    private int questionVersionNumber;
    private int questionId;
    private int referenceNumber;

    public QuestionVersionEntry() {
        sectionVersionNo = -1;
        sectionId = -1;
        questionVersionNumber = -1;
        questionId = -1;
        referenceNumber = -1;
    }

    public int getSectionVersionNo() {
        return sectionVersionNo;
    }

    public QuestionVersionEntry setSectionVersionNo(int sectionVersionNo) {
        this.sectionVersionNo = sectionVersionNo;
        return this;
    }

    public int getSectionId() {
        return sectionId;
    }

    public QuestionVersionEntry setSectionId(int sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public int getQuestionVersionNumber() {
        return questionVersionNumber;
    }

    public QuestionVersionEntry setQuestionVersionNumber(int questionVersionNumber) {
        this.questionVersionNumber = questionVersionNumber;
        return this;
    }

    public int getQuestionId() {
        return questionId;
    }

    public QuestionVersionEntry setQuestionId(int questionId) {
        this.questionId = questionId;
        return this;
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public QuestionVersionEntry setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionVersionEntry)) return false;
        QuestionVersionEntry that = (QuestionVersionEntry) o;
        return getSectionVersionNo() == that.getSectionVersionNo() &&
                getSectionId() == that.getSectionId() &&
                getQuestionVersionNumber() == that.getQuestionVersionNumber() &&
                getQuestionId() == that.getQuestionId() &&
                getReferenceNumber() == that.getReferenceNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSectionVersionNo(), getSectionId(), getQuestionVersionNumber(), getQuestionId(), getReferenceNumber());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuestionVersionEntry{");
        sb.append("sectionVersionNo=").append(sectionVersionNo);
        sb.append(", sectionId=").append(sectionId);
        sb.append(", questionVersionNumber=").append(questionVersionNumber);
        sb.append(", questionId=").append(questionId);
        sb.append(", referenceNumber=").append(referenceNumber);
        sb.append('}');
        return sb.toString();
    }
}
