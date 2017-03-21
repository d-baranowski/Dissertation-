package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Mark;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.UserReferenceModel;

public class MarkModelMapper {
    private MarkModelMapper() {
        //Hiding implicit public constructor
    }

    public static MarkModel mapMarkModelFrom(Mark mark, UserReferenceModel marker) {
        final MarkModel result = new MarkModel();
        result.setId(mark.getId());
        result.setComment(mark.getComment());
        result.setMark(mark.getActualMark());
        result.setMarker(marker);
        return result;
    }

    public static Mark mapMarkFrom(MarkModel model) {
        return new Mark.Builder()
                .setActualMark(model.getMark())
                .setMarkerId(model.getMarker() != null ? model.getMarker().getId() : null)
                .setComment(model.getComment())
                .setId(model.getId()).build();
    }
}
