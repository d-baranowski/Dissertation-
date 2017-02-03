package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Arrays;
import java.util.Objects;

/**
 * Sometimes answers might contain different assets. For example an image containing a diagram.
 */
public class AnswerAsset {
    private final int id;
    private final int questionId;
    private final int questionVersionNo;
    private final int testDayEntryId;
    private final String referenceName;
    private final byte[] file;
    private final String fileType;

    private AnswerAsset(Builder b) {
        this.id = b.id;
        this.questionId = b.questionId;
        this.questionVersionNo = b.questionVersionNo;
        this.testDayEntryId = b.testDayEntryId;
        this.referenceName = b.referenceName;
        this.file = b.file;
        this.fileType = b.fileType;
    }

    public int getId() {
        return id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public int getQuestionVersionNo() {
        return questionVersionNo;
    }

    public int getTestDayEntryId() {
        return testDayEntryId;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public byte[] getFile() {
        return file;
    }

    public String getFileType() {
        return fileType;
    }

    @Override
    public boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        AnswerAsset that = (AnswerAsset) o;
        return getId() == that.getId() && //NOSONAR
                getQuestionId() == that.getQuestionId() &&
                getQuestionVersionNo() == that.getQuestionVersionNo() &&
                getTestDayEntryId() == that.getTestDayEntryId() &&
                Objects.equals(getReferenceName(), that.getReferenceName()) &&
                Arrays.equals(getFile(), that.getFile()) &&
                Objects.equals(getFileType(), that.getFileType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuestionId(), getQuestionVersionNo(), getTestDayEntryId(), getReferenceName(), getFileType()) * Arrays.hashCode(file);
    }

    public static class Builder {
        private int id;
        private int questionId;
        private int questionVersionNo;
        private int testDayEntryId;
        private String referenceName;
        private byte[] file;
        private String fileType;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setQuestionId(int questionId) {
            this.questionId = questionId;
            return this;
        }

        public Builder setQuestionVersionNo(int questionVersionNo) {
            this.questionVersionNo = questionVersionNo;
            return this;
        }

        public Builder setTestDayEntryId(int testDayEntryId) {
            this.testDayEntryId = testDayEntryId;
            return this;
        }

        public Builder setReferenceName(String referenceName) {
            this.referenceName = referenceName;
            return this;
        }

        public Builder setFile(byte[] file) {
            this.file = file;
            return this;
        }

        public Builder setFileType(String fileType) {
            this.fileType = fileType;
            return this;
        }

        public AnswerAsset build() {
            return new AnswerAsset(this);
        }
    }
}
