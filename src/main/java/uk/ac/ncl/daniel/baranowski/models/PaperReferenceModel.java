package uk.ac.ncl.daniel.baranowski.models;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class PaperReferenceModel {
    private int id;
    private int versionNo;
    @NotNull(message = "Please specify the reference name for this paper.")
    @Size(min = 5, max = 100, message = "Reference name needs to be between 5 and 100 characters long. ")
    private String referenceName;
    @Min(value = 1, message = "Minimum time scale is 1")
    @Max(value = 100, message = "Maximum time scale is 99")
    private Integer timeAllowed;

    public PaperReferenceModel() {
        id = 0;
        versionNo = 0;
        referenceName = "";
        timeAllowed = 5;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(int versionNo) {
        this.versionNo = versionNo;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public Integer getTimeAllowed() {
        return timeAllowed;
    }

    public void setTimeAllowed(Integer timeAllowed) {
        this.timeAllowed = timeAllowed;
    }

    @Override
    public boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || !(o instanceof PaperReferenceModel)) return false; //NOSONAR
        PaperReferenceModel that = (PaperReferenceModel) o;
        return getId() == that.getId() && //NOSONAR
                getVersionNo() == that.getVersionNo() &&
                Objects.equals(getReferenceName(), that.getReferenceName()) &&
                Objects.equals(getTimeAllowed(), that.getTimeAllowed()) &&
                that.canEqual(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVersionNo(), getReferenceName(), getTimeAllowed());
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    public boolean canEqual(Object other) {
        return (other instanceof PaperReferenceModel);
    }

    @Override
    public String toString() {
        return "PaperReferenceModel{" +
                "id=" + id +
                ", versionNo=" + versionNo +
                ", referenceName='" + referenceName + '\'' +
                ", timeAllowed=" + timeAllowed +
                '}';
    }
}
