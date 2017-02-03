package uk.ac.ncl.daniel.baranowski.models;

import java.util.Objects;

public class AnswerModel {
    private MarkModel mark;
    private String text;
    private AssetModel asset;

    public MarkModel getMark() {
        return mark;
    }

    public void setMark(MarkModel mark) {
        this.mark = mark;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public AssetModel getAsset() {
        return asset;
    }

    public String getBase64ImageStringOrEmpty() {
        if (asset != null) {
            return asset.getBase64StringFile();
        } else {
            return "";
        }
    }

    public void setAssets(AssetModel assets) {
        this.asset = assets;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (!(o instanceof AnswerModel)) {
            return false;
        }
        AnswerModel that = (AnswerModel) o;
        return Objects.equals(getMark(), that.getMark()) && //NOSONAR
                Objects.equals(getText(), that.getText()) &&
                Objects.equals(getAsset(), that.getAsset());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getMark(), getText(), getAsset());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AnswerModel{");
        sb.append("mark=").append(mark);
        sb.append(", text='").append(text).append('\'');
        sb.append(", asset=").append(asset);
        sb.append('}');
        return sb.toString();
    }
}
