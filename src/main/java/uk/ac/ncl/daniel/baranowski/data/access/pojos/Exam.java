package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;

import java.util.Objects;

public final class Exam {
    private final int id;

    private final int paperVersionNo;
    private final int paperId;
    private final ExamStatus status;
    private final int termsAndConditionsId;
    private final int testDayId;
    private final int moduleId;

    private Exam(Builder builder) {
        this.moduleId = builder.moduleId;
        this.id = builder.id;
        this.paperVersionNo = builder.paperVersionNo;
        this.paperId = builder.paperId;
        this.status = builder.status;
        this.termsAndConditionsId = builder.termsAndConditionsId;
        this.testDayId = builder.testDayId;
    }

    public int getId() {
        return id;
    }

    public int getPaperVersionNo() {
        return paperVersionNo;
    }

    public int getPaperId() {
        return paperId;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public int getTermsAndConditionsId() {
        return termsAndConditionsId;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;  //NOSONAR
        if (o == null || !(o.getClass() == this.getClass())) return false;  //NOSONAR

        Exam that = (Exam) o;
        return Objects.equals(id, that.getId()) &&
                Objects.equals(paperVersionNo, that.getPaperVersionNo()) &&
                Objects.equals(paperId, that.getPaperId()) &&
                Objects.equals(status, that.getStatus()) &&
                Objects.equals(termsAndConditionsId, that.getTermsAndConditionsId()) &&
                Objects.equals(testDayId, that.getTestDayId()) &&
                Objects.equals(moduleId, that.getModuleId()) &&
                that.canEqual(this);
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    public final boolean canEqual(Object other) {
        return other instanceof Exam;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(
                id,
                paperVersionNo,
                paperId,
                status,
                termsAndConditionsId,
                testDayId,
                moduleId
        );
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", paperVersionNo=" + paperVersionNo +
                ", paperId=" + paperId +
                ", status='" + status + '\'' +
                ", termsAndConditionsId=" + termsAndConditionsId +
                ", testDayId=" + testDayId +
                ", moduleId=" + moduleId +
                '}';
    }

    public int getTestDayId() {
        return testDayId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public static class Builder {
        private int id;
        private int paperVersionNo;
        private int paperId;
        private ExamStatus status;
        private int termsAndConditionsId;
        private int testDayId;
        private int moduleId;

        public Builder setModuleId(int id) {
            this.moduleId = id;
            return this;
        }

        public Builder setTestDayId(int id) {
            this.testDayId = id;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPaperVersionNo(int paperVersionNo) {
            this.paperVersionNo = paperVersionNo;
            return this;
        }

        public Builder setPaperId(int paperId) {
            this.paperId = paperId;
            return this;
        }

        public Builder setStatus(ExamStatus status) {
            this.status = status;
            return this;
        }

        public Builder setTermsAndConditionsId(int termsAndConditionsId) {
            this.termsAndConditionsId = termsAndConditionsId;
            return this;
        }

        public Exam build() {
            return new Exam(this);
        }
    }
}
