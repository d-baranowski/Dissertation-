package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.PaperModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.SectionReferenceModel;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static tests.TestResources.QUESTION_REFERENCE_MODEL_ID_1_VER_1;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
public class PaperRepoIntegrationTest {
    @Autowired
    private PaperRepo repo;

    public PaperRepoIntegrationTest() {
    }

    @Test
    public void canGetAllQuestionReferences() throws AccessException {
        QuestionReferenceModel expected = QUESTION_REFERENCE_MODEL_ID_1_VER_1;

        List<QuestionReferenceModel> actual = repo.getAllQuestionReferences();

        assertTrue(actual.containsAll(Collections.singletonList(expected)) && actual.size() == 18);
    }

    @Test
    public void canGetQuestionById() throws AccessException {
        final QuestionModel actual = repo.getQuestionById(1, 1);
        final QuestionModel expected = TestResources.QUESTION_MODEL_ID_1_VER_1;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void canGetAllSectionReferences() throws AccessException {
        List<SectionReferenceModel> expected = Arrays.asList(
                TestResources.SECTION_REFERENCE_MODEL_ID_1,
                TestResources.SECTION_REFERENCE_MODEL_ID_2,
                TestResources.SECTION_REFERENCE_MODEL_ID_3,
                TestResources.SECTION_REFERENCE_MODEL_ID_4,
                TestResources.SECTION_REFERENCE_MODEL_ID_5,
                TestResources.SECTION_REFERENCE_MODEL_ID_6
        );

        assertEquals(expected, repo.getAllSectionReferences());
    }

    @Test
    public void canGetAllPaperReferences() throws AccessException {
        List<PaperReferenceModel> expected = Arrays.asList(
                TestResources.PAPER_REFERENCE_MODEL_ID_1,
                TestResources.PAPER_REFERENCE_MODEL_ID_2
        );

        assertEquals(expected, repo.getAllPaperReferences());
    }

    @Test
    public void canGetPaperReferenceTwoVersionOne() throws AccessException {
        PaperReferenceModel expected = TestResources.PAPER_REFERENCE_MODEL_ID_2;

        PaperReferenceModel actual = repo.getPaperReference(2, 1);
        assertEquals(expected, actual);
    }

    @Test
    public void canGetPaperReferencesToLatestVersions() throws AccessException {
        List<PaperReferenceModel> expected = Arrays.asList(
                TestResources.PAPER_REFERENCE_MODEL_ID_1,
                TestResources.PAPER_REFERENCE_MODEL_ID_2
        );

        List<PaperReferenceModel> actual = repo.getPaperReferencesToLatestVersions();
        assertEquals(expected, actual);
    }

    @Test
    public void canGetLatestVersionNumber() {
        assertEquals(1, repo.getLatestVersionNo(2));
    }

    @Test
    public void canGetPaper() throws AccessException {
        Assert.assertEquals(TestResources.PAPER_MODEL_ID_1_VER_1, repo.getPaper(1, 1));
    }

/*    @Test
    public void canGetSecondPaper() throws AccessException {
        final PaperModel actual = repo.getPaper(2, 1);
        final PaperModel expected = PAPER_MODEL_ID_2_VER_1;
        Assert.assertEquals(expected, actual);
    }*/

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void canGetQuestionByIdWithException() throws AccessException {
        expectedEx.expect(AccessException.class);
        expectedEx.expectMessage("Incorrect result size: expected 1, actual 0");
        repo.getQuestionById(99, 99);
    }

    @Test
    public void canGetNullPaperIfNotExists() throws AccessException {
        PaperModel paperModel =  repo.getPaper(99, 99);
        assertEquals(paperModel, null);
    }
}
