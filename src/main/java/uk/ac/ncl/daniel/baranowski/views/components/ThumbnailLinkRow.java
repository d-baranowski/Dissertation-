package uk.ac.ncl.daniel.baranowski.views.components;

import java.util.ArrayList;
import java.util.List;

public class ThumbnailLinkRow {
    private final List<ThumbnailLink>  columns;

    public ThumbnailLinkRow() {
        this.columns = new ArrayList<>();
    }

    public ThumbnailLinkRow add(ThumbnailLink column) {
        columns.add(column);
        return this;
    }

    public List<ThumbnailLink> getColumns() {
        return columns;
    }
}
