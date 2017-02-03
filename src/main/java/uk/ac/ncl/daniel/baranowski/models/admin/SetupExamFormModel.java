package uk.ac.ncl.daniel.baranowski.models.admin;

import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import uk.ac.ncl.daniel.baranowski.models.TestDayModel;
import java.util.Objects;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.ATTEMPT_PREFIX;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.ATTEMPT_SETUP_COMPLETE;

public final class SetupExamFormModel {
    private static final String ENDPOINT = ATTEMPT_PREFIX + ATTEMPT_SETUP_COMPLETE;
    @Valid
    private CandidateModel candidate;
    @NotNull(message = "Pick a valid paper")
    private int paperId;
    @Valid
    private TestDayModel day;

    @NotNull(message = "You must specify time allowed")
    @Min(10)
    @Max(1000)
    private Integer timeAllowed;

    public Integer getTimeAllowed() {
        return timeAllowed;
    }

    public void setTimeAllowed(Integer timeAllowed) {
        this.timeAllowed = timeAllowed;
    }

    public CandidateModel getCandidate() {
        return candidate;
    }

    public void setCandidate(CandidateModel candidate) {
        this.candidate = candidate;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public TestDayModel getDay() {
        return day;
    }

    public void setDay(TestDayModel day) {
        this.day = day;
    }

    public static String getENDPOINT() {
        return ENDPOINT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SetupExamFormModel that = (SetupExamFormModel) o;
        return getPaperId() == that.getPaperId() &&
                Objects.equals(getCandidate(), that.getCandidate()) &&
                Objects.equals(getDay(), that.getDay()) &&
                Objects.equals(getTimeAllowed(), that.getTimeAllowed());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCandidate(), getPaperId(), getDay(), getTimeAllowed());
    }

    @Override
    public String toString() {
        return String.format("SetupExamFormModel [candidate=%s, paperId=%s, day=%s]", candidate, paperId, day);
    }


}