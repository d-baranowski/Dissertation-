package tests;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.*;
import uk.ac.ncl.daniel.baranowski.models.*;

public class EqualsVerificationTest {
    @Test
    public void equalsContractForDataObjects() {
        EqualsVerifier.forClass(Module.class)
                .verify();

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

        EqualsVerifier.forClass(Exam.class)
                .verify();
    }

    @Test
    public void equalsContractForModels() {
        EqualsVerifier.forClass(ModuleModel.class)
                .withRedefinedSuperclass()
                .verify();

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
