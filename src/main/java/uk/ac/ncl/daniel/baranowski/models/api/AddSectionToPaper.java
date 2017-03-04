package uk.ac.ncl.daniel.baranowski.models.api;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class AddSectionToPaper {
    @NotNull(message = "Section ID Missing")
    @Min(value = 1, message = "Wrong Section id")
    private int sectionId;
    @NotNull(message = "Section Version Number Missing")
    @Min(value = 1, message = "Wrong Section Version Number")
    private int sectionVersion;
    @NotNull(message = "Paper ID Missing")
    @Min(value = 1, message = "Wrong Paper id")
    private int paperId;
    @NotNull(message = "Paper Version Number Missing")
    @Min(value = 1, message = "Wrong Paper Version Number")
    private int paperVersion;

    public int getSectionId() {
        return sectionId;
    }

    public AddSectionToPaper setSectionId(int sectionId) {
        this.sectionId = sectionId;
        return this;
    }

    public int getSectionVersion() {
        return sectionVersion;
    }

    public AddSectionToPaper setSectionVersion(int sectionVersion) {
        this.sectionVersion = sectionVersion;
        return this;
    }

    public int getPaperId() {
        return paperId;
    }

    public AddSectionToPaper setPaperId(int paperId) {
        this.paperId = paperId;
        return this;
    }

    public int getPaperVersion() {
        return paperVersion;
    }

    public AddSectionToPaper setPaperVersion(int paperVersion) {
        this.paperVersion = paperVersion;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AddSectionToPaper)) return false;
        AddSectionToPaper that = (AddSectionToPaper) o;
        return getSectionId() == that.getSectionId() &&
                getSectionVersion() == that.getSectionVersion() &&
                getPaperId() == that.getPaperId() &&
                getPaperVersion() == that.getPaperVersion();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSectionId(), getSectionVersion(), getPaperId(), getPaperVersion());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddSectionToPaper{");
        sb.append("sectionId=").append(sectionId);
        sb.append(", sectionVersion=").append(sectionVersion);
        sb.append(", paperId=").append(paperId);
        sb.append(", paperVersion=").append(paperVersion);
        sb.append('}');
        return sb.toString();
    }
}
