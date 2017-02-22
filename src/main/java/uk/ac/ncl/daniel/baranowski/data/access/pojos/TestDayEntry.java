package uk.ac.ncl.daniel.baranowski.data.access.pojos;

import java.util.Objects;

/**
 * A candidate sitting a test on a particular day.
 */
public final class TestDayEntry {
    private final int id;
    private final int candidateId;
    private final String status;
    private final Integer finalMark;
    private final Integer examId;
    private final String password;
    private final String login;
    private final String markingLock;

    private TestDayEntry(Builder builder) {
        id = builder.id;
        candidateId = builder.candidateId;
        status = builder.status;
        finalMark = builder.finalMark;
        examId = builder.examId;
        markingLock = builder.markingLock;
        password = builder.password;
        login = builder.login;
    }

    public String getMarkingLock() {
        return markingLock;
    }

    public int getExamId() {
        return examId;
    }

    public int getId() {
        return id;
    }

    public int getCandidateId() {
        return candidateId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getFinalMark() {
        return finalMark;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        TestDayEntry that = (TestDayEntry) o;
        return getId() == that.getId() && //NOSONAR
                getCandidateId() == that.getCandidateId() &&
                Objects.equals(status, that.status) &&
                Objects.equals(finalMark, that.getFinalMark()) &&
                Objects.equals(password, that.getPassword()) &&
                Objects.equals(login, that.getLogin()) &&
        Objects.equals(examId, that.getExamId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCandidateId(), getStatus(), getFinalMark(), getExamId(), getLogin(), getPassword());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TestDayEntry{");
        sb.append("id=").append(id);
        sb.append(", candidateId=").append(candidateId);
        sb.append(", status='").append(status).append('\'');
        sb.append(", finalMark=").append(finalMark);
        sb.append(", examId=").append(examId);
        sb.append(", password='").append(password).append('\'');
        sb.append(", login='").append(login).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private int id;
        private int candidateId;
        private String status;
        private Integer finalMark;
        private int examId;
        private String password;
        private String login;
        private String markingLock;

        public Builder setMarkingLock(String markingLock) {
            this.markingLock = markingLock;
            return this;
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setCandidateId(int candidateId) {
            this.candidateId = candidateId;
            return this;
        }

        public Builder setFinalMark(Integer finalMark) {
            this.finalMark = finalMark;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public TestDayEntry build() {
            return new TestDayEntry(this);
        }

        public Builder setExamId(int examId) {
            this.examId = examId;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }
    }
}
