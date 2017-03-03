package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

public class SectionVersionEntry {
    private int sectionVersionNo;
    private int sectionId;
    private int paperVersionNumber;
    private int paperId;
    private int referenceNumber;

    public SectionVersionEntry() {
        sectionVersionNo = -1;
        sectionId = -1;
        paperVersionNumber = -1;
        paperId = -1;
        referenceNumber = -1;
    }

    public int getSectionVersionNo() {
        return sectionVersionNo;
    }

    public SectionVersionEntry setSectionVersionNo(int sectionVersionNo) {
        this.sectionVersionNo = sectionVersionNo;
        return this;
    }

    public int getSectionId() {
        return sectionId;
    }

    public SectionVersionEntry setSectionId(int sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public int getPaperVersionNumber() {
        return paperVersionNumber;
    }

    public SectionVersionEntry setPaperVersionNumber(int paperVersionNumber) {
        this.paperVersionNumber = paperVersionNumber;
        return this;
    }

    public int getPaperId() {
        return paperId;
    }

    public SectionVersionEntry setPaperId(int paperId) {
        this.paperId = paperId;
        return this;
    }

    public int getReferenceNumber() {
        return referenceNumber;
    }

    public SectionVersionEntry setReferenceNumber(int referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectionVersionEntry)) return false;
        SectionVersionEntry that = (SectionVersionEntry) o;
        return getSectionVersionNo() == that.getSectionVersionNo() &&
                getSectionId() == that.getSectionId() &&
                getPaperVersionNumber() == that.getPaperVersionNumber() &&
                getPaperId() == that.getPaperId() &&
                getReferenceNumber() == that.getReferenceNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSectionVersionNo(), getSectionId(), getPaperVersionNumber(), getPaperId(), getReferenceNumber());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuestionVersionEntry{");
        sb.append("sectionVersionNo=").append(sectionVersionNo);
        sb.append(", sectionId=").append(sectionId);
        sb.append(", paperVersionNumber=").append(paperVersionNumber);
        sb.append(", paperId=").append(paperId);
        sb.append(", referenceNumber=").append(referenceNumber);
        sb.append('}');
        return sb.toString();
    }
}
