package uk.ac.ncl.daniel.baranowski.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SectionReferenceModel {
    private int id;
    private int versionNumber;
    @NotNull(message = "Please specify the reference name for this section.")
    @Size(min = 5, max = 100, message = "Reference name needs to be between 5 and 100 characters long. ")
    private String referenceName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    public boolean canEqual(Object other) {
        return other instanceof SectionReferenceModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || !(o instanceof SectionReferenceModel)) return false; //NOSONAR
        SectionReferenceModel that = (SectionReferenceModel) o;
        return getId() == that.getId() &&
                getVersionNumber() == that.getVersionNumber() &&
                Objects.equals(getReferenceName(), that.getReferenceName()) &&
                that.canEqual(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVersionNumber(), getReferenceName());
    }

    @Override
    public String toString() {
        return String.format("SectionReference [id=%s, versionNumber=%s, referenceName=%s]", id, versionNumber,
                referenceName);
    }
}
