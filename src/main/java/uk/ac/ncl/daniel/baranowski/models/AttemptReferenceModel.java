package uk.ac.ncl.daniel.baranowski.models;

import java.util.Objects;

public class AttemptReferenceModel {
    private int id;
    private CandidateModel candidate;
    private TestDayModel day;
    private PaperReferenceModel paperRef;
    private String status;
    private Integer finalMark;
    private Integer timeAllowed;
    private int termsAndConditionsId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTimeAllowed() {
        return timeAllowed;
    }

    public void setTimeAllowed(Integer timeAllowed) {
        this.timeAllowed = timeAllowed;
    }

    public void setTermsAndConditionsId(int termsAndConditionsId){
        this.termsAndConditionsId = termsAndConditionsId;
    }

    public int getTermsAndConditionsId(){return termsAndConditionsId;}

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
        return day == null ? "" : day.getDate().toString();
    }

    public String getLocation() {
        return day == null ? "" : day.getLocation();
    }

    public void setTestDayModel(TestDayModel day) {
        this.day = day;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || !(o.getClass() == this.getClass())) return false; //NOSONAR
        AttemptReferenceModel that = (AttemptReferenceModel) o;
        return getId() == that.getId() && //NOSONAR
                Objects.equals(getCandidate(), that.getCandidate()) &&
                Objects.equals(day, that.day) &&
                Objects.equals(getPaperRef(), that.getPaperRef()) &&
                Objects.equals(getStatus(), that.getStatus()) &&
                Objects.equals(getFinalMark(), that.getFinalMark()) &&
                Objects.equals(getTimeAllowed(), that.getTimeAllowed()) &&
                Objects.equals(getTermsAndConditionsId(), that.getTermsAndConditionsId()) &&
                that.canEqual(this);
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    public boolean canEqual(Object other) {
        return other instanceof AttemptReferenceModel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCandidate(), day, getPaperRef(), getStatus(), getFinalMark(), getTimeAllowed(), getTermsAndConditionsId());
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("AttemptReferenceModel{");
        sb.append("id=").append(id);
        sb.append(", candidate=").append(candidate);
        sb.append(", day=").append(day);
        sb.append(", paperRef=").append(paperRef);
        sb.append(", status='").append(status).append('\'');
        sb.append(", finalMark=").append(finalMark);
        sb.append(", timeAllowed=").append(timeAllowed);
        sb.append(", termsandConditionsId=").append(termsAndConditionsId);
        sb.append('}');
        return sb.toString();
    }
}