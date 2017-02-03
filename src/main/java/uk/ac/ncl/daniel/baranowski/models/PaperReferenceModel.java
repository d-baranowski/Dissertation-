package uk.ac.ncl.daniel.baranowski.models;

import java.util.Objects;

public class PaperReferenceModel {
    private int id;
    private int versionNo;
    private String referenceName;
    private Integer timeAllowed;

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
