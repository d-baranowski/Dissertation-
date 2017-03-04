package uk.ac.ncl.daniel.baranowski.models.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class RemoveSectionFromPaper {
    @NotNull(message = "Section ID Missing")
    @Min(value = 1, message = "Wrong Section id")
    private int sectionId;
    @NotNull(message = "Section Version Missing")
    @Min(value = 1, message = "Wrong Section version")
    private int sectionVersion;
    @NotNull(message = "Paper ID Missing")
    @Min(value = 1, message = "Wrong Paper id")
    private int paperId;
    @NotNull(message = "Paper Version Missing")
    @Min(value = 1, message = "Wrong Paper version")
    private int paperVersion;
    @NotNull(message = "Section number Missing")
    @Min(value = 1, message = "Wrong Section number")
    private int sectionNo;

    public RemoveSectionFromPaper() {
        this.sectionId = 0;
        this.sectionVersion = 0;
        this.paperId = 0;
        this.paperVersion = 0;
        this.sectionNo = 0;
    }

    public RemoveSectionFromPaper setSectionNo(int sectionNo) {
        this.sectionNo = sectionNo;
        return this;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getSectionVersion() {
        return sectionVersion;
    }

    public int getPaperId() {
        return paperId;
    }

    public int getPaperVersion() {
        return paperVersion;
    }

    public RemoveSectionFromPaper setSectionId(int sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public RemoveSectionFromPaper setSectionVersion(int sectionVersion) {
        this.sectionVersion = sectionVersion;
        return this;
    }

    public RemoveSectionFromPaper setPaperId(int paperId) {
        this.paperId = paperId;
        return this;
    }

    public RemoveSectionFromPaper setPaperVersion(int paperVersion) {
        this.paperVersion = paperVersion;
        return this;
    }

    public int getSectionNo() {
        return sectionNo;
    }
}
