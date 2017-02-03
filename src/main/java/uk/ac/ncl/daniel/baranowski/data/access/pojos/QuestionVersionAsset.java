package uk.ac.ncl.daniel.baranowski.data.access.pojos;


import java.util.Arrays;
import java.util.Objects;

/**
 * Question versions can contain assets like pictures of diagrams etc.
 */
public final class QuestionVersionAsset {
    private final int id;
    private final int questionId;
    private final int questionVersionNo;
    private final String referenceName;
    private final byte[] file;
    private final String fileType;

    private QuestionVersionAsset(Builder b) {
        this.id = b.id;
        this.questionId = b.questionId;
        this.questionVersionNo = b.questionVersionNo;
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
        QuestionVersionAsset that = (QuestionVersionAsset) o;
        return getId() == that.getId() && //NOSONAR
                getQuestionId() == that.getQuestionId() &&
                getQuestionVersionNo() == that.getQuestionVersionNo() &&
                Objects.equals(getReferenceName(), that.getReferenceName()) &&
                Arrays.equals(getFile(), that.getFile()) &&
                Objects.equals(getFileType(), that.getFileType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuestionId(), getQuestionVersionNo(), getReferenceName(), getFileType()) * Arrays.hashCode(getFile());
    }

    public static class Builder {
        private int id;
        private int questionId;
        private int questionVersionNo;
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

        public QuestionVersionAsset build() {
            return new QuestionVersionAsset(this);
        }
    }
}
