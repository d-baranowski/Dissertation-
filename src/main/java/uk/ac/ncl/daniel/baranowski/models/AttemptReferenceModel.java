package uk.ac.ncl.daniel.baranowski.models;

import java.util.Objects;

public class AttemptReferenceModel {
    private int id;
    private CandidateModel candidate;
    private String date;
    private PaperReferenceModel paperRef;
    private String status;
    private Integer finalMark;
    private int examId;
    private String login;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCandidateName() {
        return candidate == null ? "" : candidate.getFirstName();
    }

    public String getCandidateSurname() {
        return candidate == null ? "" : candidate.getSurname();
    }

    public int getCandidateId() {
        return candidate == null ? 0 : candidate.getId();
    }

    public void setCandidate(CandidateModel candidate) {
        this.candidate = candidate;
    }

    public CandidateModel getCandidate() {
        return this.candidate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public PaperReferenceModel getPaperRef() {
        return paperRef;
    }

    public void setPaperRef(PaperReferenceModel paperRef) {
        this.paperRef = paperRef;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFinalMark() {
        return finalMark;
    }

    public void setFinalMark(Integer finalMark) {
        this.finalMark = finalMark;
    }

    public int getExamId() {
        return examId;
    }

    public AttemptReferenceModel setExamId(int examId) {
        this.examId = examId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || !(o.getClass() == this.getClass())) return false; //NOSONAR
        AttemptReferenceModel that = (AttemptReferenceModel) o;
        return getId() == that.getId() && //NOSONAR
                Objects.equals(getCandidate(), that.getCandidate()) &&
                Objects.equals(getPaperRef(), that.getPaperRef()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getFinalMark(), that.getFinalMark()) &&
                Objects.equals(getExamId(), that.getExamId()) &&
                Objects.equals(getLogin(), that.getLogin()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getDate(), that.getDate()) &&
                that.canEqual(this);
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    public boolean canEqual(Object other) {
        return other instanceof AttemptReferenceModel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCandidate(), getDate() ,getPaperRef(), getStatus(), getFinalMark(), getExamId(), getLogin(), getPassword());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AttemptReferenceModel{");
        sb.append("id=").append(id);
        sb.append(", candidate=").append(candidate);
        sb.append(", date=").append(date);
        sb.append(", paperRef=").append(paperRef);
        sb.append(", status='").append(status).append('\'');
        sb.append(", finalMark=").append(finalMark);
        sb.append(", examId=").append(examId);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}