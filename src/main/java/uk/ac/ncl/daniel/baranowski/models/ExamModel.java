package uk.ac.ncl.daniel.baranowski.models;

import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;

import java.util.ArrayList;
import java.util.List;

public class ExamModel {
    private final int id;
    private final List<AttemptReferenceModel> attempts;
    private final PaperReferenceModel paperRef;
    private final ExamStatus status;
    private final TestDayModel testDayModel;
    private final ModuleModel module;
    private final String termsAndConditions;

    private ExamModel(Builder builder) {
        this.module = builder.module;
        this.id = builder.id;
        this.attempts = builder.attempts;
        this.paperRef = builder.paperRef;
        this.status = builder.status;
        this.testDayModel = builder.testDayModel;
        this.termsAndConditions = builder.termsAndConditions;
    }

    public ModuleModel getModule() {
        return module;
    }

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

    public ExamStatus getStatus() {
        return status;
    }

    public TestDayModel getTestDayModel() {
        return testDayModel;
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

        public Builder addAttemp(AttemptReferenceModel attempt) {
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
