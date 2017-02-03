package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * A candidate sitting a test on a particular day.
 */
public final class TestDayEntry {
    private final int id;
    private final int testDayId;
    private final int paperVersionNo;
    private final int paperId;
    private final int candidateId;
    private final String status;
    private final Integer finalMark;
    private final int termsAndConditionsId;
    private final Integer timeAllowed;

    private TestDayEntry(Builder builder) {
        id = builder.id;
        testDayId = builder.testDayId;
        paperVersionNo = builder.paperVersionNo;
        candidateId = builder.candidateId;
        paperId = builder.paperId;
        status = builder.status;
        finalMark = builder.finalMark;
        termsAndConditionsId = builder.termsAndConditionsId;
        timeAllowed = builder.timeAllowed;
    }

    public int getId() {
        return id;
    }

    public int getTestDayId() {
        return testDayId;
    }

    public int getPaperVersionNo() {
        return paperVersionNo;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public int getPaperId() {
        return paperId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getFinalMark() {
        return finalMark;
    }

    public int getTermsAndConditionsId(){
        return termsAndConditionsId;
    }
        public Integer getTimeAllowed() {
        return timeAllowed;
    }

    @Override
    public boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        TestDayEntry that = (TestDayEntry) o;
        return getId() == that.getId() && //NOSONAR
                getTestDayId() == that.getTestDayId() &&
                getPaperVersionNo() == that.getPaperVersionNo() &&
                getPaperId() == that.getPaperId() &&
                getCandidateId() == that.getCandidateId() &&
                getTermsAndConditionsId() == that.getTermsAndConditionsId() &&
                Objects.equals(getTimeAllowed(), that.getTimeAllowed()) &&
                Objects.equals(status, that.status) &&
                Objects.equals(finalMark, that.getFinalMark());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTestDayId(), getPaperVersionNo(), getPaperId(), getCandidateId(), getStatus(), getFinalMark(), getTermsAndConditionsId(), getTimeAllowed());
    }

    public static class Builder {
        private int paperId;
        private int id;
        private int testDayId;
        private int paperVersionNo;
        private int candidateId;
        private String status;
        private Integer finalMark;
        private int termsAndConditionsId;
        private Integer timeAllowed;

        public Builder setTimeAllowed(Integer timeAllowed) {
            this.timeAllowed = timeAllowed;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setTestDayId(int testDayId) {
            this.testDayId = testDayId;
            return this;
        }

        public Builder setPaperVersionNo(int paperVersionNo) {
            this.paperVersionNo = paperVersionNo;
            return this;
        }

        public Builder setCandidateId(int candidateId) {
            this.candidateId = candidateId;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPaperId(int paperId) {
            this.paperId = paperId;
            return this;
        }

        public Builder setFinalMark(Integer finalMark) {
            this.finalMark = finalMark;
            return this;
        }

        public Builder setTermsAndConditions(int termsAndConditionsId){
            this.termsAndConditionsId = termsAndConditionsId;
            return this;
        }

        public TestDayEntry build() {
            return new TestDayEntry(this);
        }
    }
}
