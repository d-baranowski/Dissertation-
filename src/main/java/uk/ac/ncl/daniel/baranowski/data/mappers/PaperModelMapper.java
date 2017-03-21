package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Paper;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.PaperVersion;
import uk.ac.ncl.daniel.baranowski.models.PaperModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.SectionModel;

import java.util.Map;

public class PaperModelMapper {
    private PaperModelMapper() {
        //Hiding implicit public constructor
    }

    public static PaperReferenceModel mapPaperReferenceModelFrom(Paper p, int versionNo) {
        final PaperReferenceModel paperReference = new PaperReferenceModel();
        paperReference.setId(p.getId());
        paperReference.setVersionNo(versionNo);
        paperReference.setReferenceName(p.getName());
        paperReference.setTimeAllowed(p.getTimeAllowed());
        return paperReference;
    }

    public static PaperModel mapPaperModelFrom(Paper p, PaperVersion pv, Map<Integer, SectionModel> sections) {
        PaperModel result = new PaperModel();

        result.setId(p.getId());
        result.setVersionNo(pv.getVersionNo());
        result.setReferenceName(p.getName());
        result.setInstructionsText(pv.getInstructionsText() != null ? pv.getInstructionsText(): "");
        result.setSections(sections);
        result.setTimeAllowed(p.getTimeAllowed());

        return result;
    }

    public static Paper mapPaperFrom(PaperReferenceModel paperReferenceModel) {
        return new Paper.Builder()
                .setId(paperReferenceModel.getId())
                .setName(paperReferenceModel.getReferenceName())
                .setTimeAllowed(paperReferenceModel.getTimeAllowed())
                .build();
    }

    public static PaperVersion mapPaperVersionFrom(PaperModel p, String authodId) {
        return new PaperVersion.Builder()
                .setAuthorId(authodId)
                .setInstructionsText(p.getInstructionsText())
                .setPaperId(p.getId())
                .setVersionNo(p.getVersionNo())
                .build();
    }
}
