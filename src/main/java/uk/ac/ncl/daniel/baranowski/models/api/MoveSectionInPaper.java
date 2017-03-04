package uk.ac.ncl.daniel.baranowski.models.api;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MoveSectionInPaper {
    @NotNull(message = "Section is is missing")
    @Min(value = 1, message = "Wrong section id")
    private int sectionId;
    @NotNull(message = "Section version Missing")
    @Min(value = 1, message = "Wrong section version")
    private int sectionVer;
    @NotNull(message = "New Reference Missing")
    @Min(value = 1, message = "New reference is wrong")
    private int newRef;
    @NotNull(message = "Paper Id is missing")
    @Min(value = 1, message = "Paper Id is wrong")
    private int paperId;
    @NotNull(message = "Paper Version Number is missing")
    @Min(value = 1, message = "Paper Version Number is wrong")
    private int paperVer;


    public MoveSectionInPaper() {
        sectionVer = 0;
        newRef = 0;
        sectionId = 0;
        paperId = 0;
        paperVer = 0;
    }

    public MoveSectionInPaper setPaperId(int paperId) {
        this.paperId = paperId;
        return this;
    }

    public MoveSectionInPaper setPaperVer(int paperVer) {
        this.paperVer = paperVer;
        return this;
    }

    public int getPaperId() {
        return paperId;
    }

    public int getPaperVer() {
        return paperVer;
    }

    public int getNewRef() {
        return newRef;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getSectionVer() {
        return sectionVer;
    }

    public MoveSectionInPaper setSectionVer(int sectionVer) {
        this.sectionVer = sectionVer;
        return this;
    }

    public MoveSectionInPaper setNewRef(int newRef) {
        this.newRef = newRef;
        return this;
    }

    public MoveSectionInPaper setSectionId(int sectionId) {
        this.sectionId = sectionId;
        return this;
    }
}
