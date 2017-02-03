package tests;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Answer;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.AnswerAsset;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Candidate;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Mark;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Paper;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.PaperVersion;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Question;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersion;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersionAsset;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Role;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Section;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.SectionVersion;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDay;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDayEntry;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.User;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.AnswersMapModel;
import uk.ac.ncl.daniel.baranowski.models.AssetModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.PaperModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.SectionModel;
import uk.ac.ncl.daniel.baranowski.models.SectionReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.TestDayModel;
import uk.ac.ncl.daniel.baranowski.models.UserModel;
import uk.ac.ncl.daniel.baranowski.models.UserReferenceModel;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

public class EqualsVerificationTest {
    @Test
    public void equalsContractForDataObjects() {
        EqualsVerifier.forClass(Answer.class)
                .verify();

        EqualsVerifier.forClass(AnswerAsset.class)
                .usingGetClass()
                .verify();

        EqualsVerifier.forClass(Candidate.class)
                .verify();

        EqualsVerifier.forClass(Mark.class)
                .verify();

        EqualsVerifier.forClass(Paper.class)
                .verify();

        EqualsVerifier.forClass(PaperVersion.class)
                .verify();

        EqualsVerifier.forClass(Question.class)
                .verify();

        EqualsVerifier.forClass(QuestionVersion.class)
                .verify();

        EqualsVerifier.forClass(QuestionVersionAsset.class)
                .verify();

        EqualsVerifier.forClass(Role.class)
                .verify();

        EqualsVerifier.forClass(Section.class)
                .verify();

        EqualsVerifier.forClass(SectionVersion.class)
                .verify();

        EqualsVerifier.forClass(TestDay.class)
                .verify();

        EqualsVerifier.forClass(TestDayEntry.class)
                .verify();

        EqualsVerifier.forClass(User.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }

    @Test
    public void equalsContractForModels() {
        EqualsVerifier.forClass(AnswerModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(AnswersMapModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(AssetModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.ALL_FIELDS_SHOULD_BE_USED)
                .verify();

        EqualsVerifier.forClass(CandidateModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(MarkModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(PaperModel.class)
                .withRedefinedSuperclass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(PaperReferenceModel.class)
                .withRedefinedSubclass(PaperModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(QuestionModel.class)
                .withRedefinedSuperclass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(QuestionReferenceModel.class)
                .withRedefinedSubclass(QuestionModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(SectionModel.class)
                .withRedefinedSuperclass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(SectionReferenceModel.class)
                .withRedefinedSubclass(SectionModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(AttemptModel.class)
                .withRedefinedSuperclass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(AttemptReferenceModel.class)
                .withRedefinedSubclass(AttemptModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .usingGetClass()
                .verify();

        EqualsVerifier.forClass(TestDayModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(UserModel.class)
                .withRedefinedSuperclass()
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();

        EqualsVerifier.forClass(UserReferenceModel.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .withRedefinedSubclass(UserModel.class)
                .usingGetClass()
                .verify();
    }
}
