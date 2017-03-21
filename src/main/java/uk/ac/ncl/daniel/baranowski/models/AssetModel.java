package uk.ac.ncl.daniel.baranowski.models;

import org.apache.tomcat.util.codec.binary.Base64;

import java.util.Arrays;
import java.util.Objects;

public class AssetModel {
    private int id;
    private String referenceName;
    private byte[] file;
    private String fileType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getBase64StringFile() {
        if (file != null) {
            return String.format("data:image/%s;base64,%s", fileType, Base64.encodeBase64String(file));
        } else {
            return "";
        }
    }

    @Override
    public final boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (o == null || !(o instanceof AssetModel)) return false; //NOSONAR
        AssetModel that = (AssetModel) o;
        return Objects.equals(getId(), that.getId()) && //NOSONAR
                Objects.equals(getReferenceName(), that.getReferenceName()) &&
                (getFile() == null || that.getFile() == null ? true : Objects.equals(getFile().length, that.getFile().length)) &&
                Objects.equals(getFileType(), that.getFileType());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getId(), getReferenceName(), getFileType()) * (file == null ? 1 : Objects.hashCode(file.length));
    }

    @Override
    public String toString() {
        return "AssetModel{" +
                "id=" + id +
                ", referenceName='" + referenceName + '\'' +
                ", file=" + Arrays.toString(file) +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
