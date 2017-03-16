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

    public static SectionReferenceModel mapSectionReferenceModelFrom(SectionVersion s, String referenceName) {
        final SectionReferenceModel ref = new SectionReferenceModel();
        ref.setId(s.getSectionId());
        ref.setTimeScale(s.getTimeScale());
        ref.setVersionNumber(s.getVersionNo());
        ref.setReferenceName(referenceName);
        ref.setNoOfQuestionsToAnswer(s.getNoOfQuestionsToAnswer());
        return ref;
    }

    public static SectionModel mapSectionModelFrom(Section s, SectionVersion sv, Map<Integer, QuestionModel> questions) {
        final SectionModel section = new SectionModel();
        section.setId(s.getId());
        section.setVersionNumber(sv.getVersionNo());
        section.setNoOfQuestionsToAnswer(sv.getNoOfQuestionsToAnswer());
        section.setTimeScale(sv.getTimeScale());
        section.setReferenceName(s.getReferenceName());
        section.setInstructionsText(sv.getDescription() != null ? sv.getDescription() : "");
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
