package uk.ac.ncl.daniel.baranowski.models;

import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.tables.annotations.ColumnGetter;
import uk.ac.ncl.daniel.baranowski.tables.annotations.ViewEndpoint;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.EXAM_PREFIX;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.EXAM_REVIEW;

public class ExamModel {
    private static final String FRIENDLY_NAME = "Exams";
    private final String TIME_PATTERN = "HH:mm";
    private final int id;
    private final List<AttemptReferenceModel> attempts;
    private final PaperReferenceModel paperRef;
    private final ExamStatus status;
    private final TestDayModel day;
    private final ModuleModel module;
    private final String termsAndConditions;

    private ExamModel(Builder builder) {
        this.module = builder.module;
        this.id = builder.id;
        this.attempts = builder.attempts;
        this.paperRef = builder.paperRef;
        this.status = builder.status;
        this.day = builder.testDayModel;
        this.termsAndConditions = builder.termsAndConditions;
    }

    @ViewEndpoint
    public String getViewEndpoint() {
        return EXAM_PREFIX + EXAM_REVIEW.replace("{examId}", getId() + "");
    }

    public ModuleModel getModule() {
        return module;
    }

    @ColumnGetter(name = "Module", order = 1)
    public String getModuleName() {
        return module != null ? module.getReferenceName() : "";
    }

    @ColumnGetter(name = "id", order = 0)
    public int getId() {
        return id;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public List<AttemptReferenceModel> getAttempts() {
        return attempts;
    }

    public PaperReferenceModel getPaperRef() {
        return paperRef;
    }

    @ColumnGetter(name = "Test Paper", order = 3)
    public String getPaperName() { return paperRef != null ? paperRef.getReferenceName() : "";}

    public ExamStatus getStatus() {
        return status;
    }

    @ColumnGetter(name = "Status", order = 2)
    public String getStatusString() { return status.toString(); }

    public TestDayModel getTestDayModel() {
        return day;
    }

    @ColumnGetter(name = "Date", order = 4)
    public String getDate() {
        return day != null ? day.getDate() : "";
    }

    @ColumnGetter(name = "Start Time", order = 5)
    public String getStartTime() {
        return day != null ? day.getStartTimeAsLocalTime().toString(TIME_PATTERN) : "";
    }

    @ColumnGetter(name = "End Time", order = 6)
    public String getEndTime() {
        return day != null ? day.getEndTimeAsLocalTime().toString(TIME_PATTERN) : "";
    }

    @ColumnGetter(name = "End Time Extra", order = 7)
    public String getEndTimeExtra() {
        return day != null ? day.getEndTimeWithExtraTimeAsLocalTime().toString(TIME_PATTERN) : "";
    }

    public static class Builder {
        private int id;
        private List<AttemptReferenceModel> attempts;
        private PaperReferenceModel paperRef;
        private ExamStatus status;
        private TestDayModel testDayModel;
        private ModuleModel module;
        private String termsAndConditions;

        public Builder setTermsAndConditions(String termsAndConditions) {
            this.termsAndConditions = termsAndConditions;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setModule(ModuleModel module) {
            this.module = module;
            return this;
        }

        public Builder setTestDayModel(TestDayModel model) {
            this.testDayModel = model;
            return this;
        }

        public Builder setAttempts(List<AttemptReferenceModel> attempts) {
            this.attempts = attempts;
            return this;
        }

        public Builder addAttempt(AttemptReferenceModel attempt) {
            if (this.attempts != null) {
                this.attempts.add(attempt);
            } else {
                this.attempts = new ArrayList<>();
            }

            return this;
        }

        public Builder setPaperRef(PaperReferenceModel paperRef) {
            this.paperRef = paperRef;
            return this;
        }

        public Builder setStatus(ExamStatus status) {
            this.status = status;
            return this;
        }

        public ExamModel build() {
            return new ExamModel(this);
        }
    }
}
