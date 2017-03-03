package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Section;
import uk.ac.ncl.daniel.baranowski.models.SectionReferenceModel;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.SectionVersion;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.SectionModel;

import java.util.Map;

public class SectionReferenceModelMapper {
    private SectionReferenceModelMapper() {
        //Hiding implicit public constructor
    }

    public static SectionReferenceModel mapSectionReferenceModelFrom(Section s, int versionNo) {
        final SectionReferenceModel ref = new SectionReferenceModel();
        ref.setId(s.getId());
        ref.setVersionNumber(versionNo);
        ref.setReferenceName(s.getReferenceName());
        return ref;
    }

    public static SectionModel mapSectionModelFrom(Section s, SectionVersion sv, Map<Integer, QuestionModel> questions) {
        final SectionModel section = new SectionModel();
        section.setId(s.getId());
        section.setVersionNumber(sv.getVersionNo());
        section.setNoOfQuestionsToAnswer(sv.getNoOfQuestionsToAnswer());
        section.setTimeScale(sv.getTimeScale());
        section.setReferenceName(s.getReferenceName());
        section.setInstructionsText(sv.getDescription());
        section.setQuestions(questions);

        return section;
    }

    public static Section mapSectionFrom(SectionReferenceModel model) {
        return new Section.Builder()
                .setId(model.getId())
                .setReferenceName(model.getReferenceName())
                .build();
    }

    public static SectionVersion mapSectionVersionFrom(SectionModel model) {
        return new SectionVersion.Builder()
                .setVersionNo(model.getVersionNumber())
                .setDescription(model.getInstructionsText())
                .setSectionId(model.getId())
                .setNoOfQuestionsToAnswer(model.getNoOfQuestionsToAnswer())
                .setTimeScale(model.getTimeScale())
                .build();
    }
}
