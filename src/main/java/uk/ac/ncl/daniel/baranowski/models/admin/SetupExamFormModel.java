package uk.ac.ncl.daniel.baranowski.models.admin;

import uk.ac.ncl.daniel.baranowski.models.TestDayModel;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.EXAM_SETUP_COMPLETE;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.EXAM_PREFIX;

public final class SetupExamFormModel {
    private static final String ENDPOINT = EXAM_PREFIX + EXAM_SETUP_COMPLETE;

    @NotNull(message = "Pick a valid paper")
    private int paperId;
    @Valid
    private TestDayModel day;

    @NotNull(message = "You must specify time allowed")
    @Min(10)
    @Max(1000)
    private Integer timeAllowed;

    @NotNull(message = "Pick a valid module")
    private int moduleId;

    public Integer getModuleId() {
        return moduleId;
    }

    public Integer getTimeAllowed() {
        return timeAllowed;
    }

    public void setTimeAllowed(Integer timeAllowed) {
        this.timeAllowed = timeAllowed;
    }

    public SetupExamFormModel setModuleId(int moduleId) {
        this.moduleId = moduleId;
        return this;
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
                Objects.equals(getModuleId(), that.getModuleId()) &&
                Objects.equals(getDay(), that.getDay()) &&
                Objects.equals(getTimeAllowed(), that.getTimeAllowed());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getModuleId(), getPaperId(), getDay(), getTimeAllowed());
    }

    @Override
    public String toString() {
        return String.format("SetupExamFormModel [module=%s, paperId=%s, day=%s]", moduleId, paperId, day);
    }


}