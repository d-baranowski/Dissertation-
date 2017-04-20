package uk.ac.ncl.daniel.baranowski.views.components;

public class ThumbnailLink {
    private String image;
    private String label;
    private String description;
    private String link;

    public ThumbnailLink(String image, String label, String description, String link) {
        this.image = image;
        this.label = label;
        this.description = description;
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public ThumbnailLink setImage(String image) {
        this.image = image;
        return this;
    }

    public String getLabel() {
        return label;
    }

    public ThumbnailLink setLabel(String label) {
        this.label = label;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ThumbnailLink setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getLink() {
        return link;
    }

    public ThumbnailLink setLink(String link) {
        this.link = link;
        return this;
    }
}
