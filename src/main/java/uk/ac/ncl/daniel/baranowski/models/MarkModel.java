package uk.ac.ncl.daniel.baranowski.models;

import java.util.Objects;

public class MarkModel {
    private int id;
    private UserReferenceModel marker;
    private String comment;
    private int mark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserReferenceModel getMarker() {
        return marker;
    }

    public void setMarker(UserReferenceModel marker) {
        this.marker = marker;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public final boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || !(o instanceof MarkModel)) return false; //NOSONAR
        MarkModel markModel = (MarkModel) o;
        return getId() == markModel.getId() &&
                getMark() == markModel.getMark() &&
                Objects.equals(getMarker(), markModel.getMarker()) &&
                Objects.equals(getComment(), markModel.getComment());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getMarker(), getComment(), getMark());
    }

    @Override
    public String toString() {
        return String.format("MarkModel [id=%s, marker=%s, comment=%s, mark=%s]", id, marker, comment, mark);
    }
}
