package tests;

import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.service.AttemptService;
import java.util.Arrays;
import java.util.Collections;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import static uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus.CREATED;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tests.TestResources.CANDIDATE_MODEL_ID_1;
import static tests.TestResources.PAPER_REFERENCE_MODEL_ID_1;
import static tests.TestResources.PAPER_REFERENCE_MODEL_ID_2;
import static tests.TestResources.SAMPLE_DAY;
import static tests.TestResources.TEST_ATTEMPT_REFERENCE_MODEL_ID_2;

@RunWith(MockitoJUnitRunner.class)
public class AttemptSetupUnitTest {

    @Mock
    private PaperRepo paperRepo;
    @Mock
    private AttemptRepo attemptRepo;

    @InjectMocks
    AttemptService attemptService;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @After
    public void reset() {
        expectedEx = ExpectedException.none();
    }

    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void canSaveTestAttemptSetupInformation() throws AccessException {
        SetupExamFormModel form = new SetupExamFormModel();
        form.setCandidate(CANDIDATE_MODEL_ID_1);
        form.setDay(SAMPLE_DAY);
        form.setPaperId(2);

        when(paperRepo.getLatestVersionNo(eq(2))).thenReturn(1);
        when(paperRepo.getPaperReference(eq(2), eq(1))).thenReturn(PAPER_REFERENCE_MODEL_ID_2);

        when(attemptRepo.getCandidate(eq(1))).thenReturn(CANDIDATE_MODEL_ID_1);
        when(attemptRepo.createAndGet(eq(CANDIDATE_MODEL_ID_1), eq(SAMPLE_DAY), eq(PAPER_REFERENCE_MODEL_ID_2), eq(CREATED.name()), anyInt(), any()))
                .thenReturn(TEST_ATTEMPT_REFERENCE_MODEL_ID_2);

        AttemptReferenceModel actual = attemptService.createAttemptModelFromSetupInformation(form);
        assertEquals(TEST_ATTEMPT_REFERENCE_MODEL_ID_2, actual);
    }

    @Test
    public void canSaveTestAttemptSetupInformationNewCandidate() throws AccessException {
        SetupExamFormModel form = new SetupExamFormModel();
        CandidateModel newCandidate = mock(CandidateModel.class);
        when(newCandidate.hasId()).thenReturn(false);
        form.setCandidate(newCandidate);
        form.setDay(SAMPLE_DAY);
        form.setPaperId(2);

        when(paperRepo.getLatestVersionNo(eq(2))).thenReturn(1);
        when(paperRepo.getPaperReference(eq(2), eq(1))).thenReturn(PAPER_REFERENCE_MODEL_ID_2);

        when(attemptRepo.createAndGetCandidate(eq(newCandidate))).thenReturn(CANDIDATE_MODEL_ID_1);
        when(attemptRepo.createAndGet(eq(CANDIDATE_MODEL_ID_1), eq(SAMPLE_DAY), eq(PAPER_REFERENCE_MODEL_ID_2), eq(CREATED.name()), anyInt(), any()))
                .thenReturn(TEST_ATTEMPT_REFERENCE_MODEL_ID_2);

        AttemptReferenceModel actual = attemptService.createAttemptModelFromSetupInformation(form);
        assertEquals(TEST_ATTEMPT_REFERENCE_MODEL_ID_2, actual);
    }

    @Test
    public void failToGetCandidate() throws AccessException {
        expectedEx.expect(HttpServerErrorException.class);

        SetupExamFormModel form = new SetupExamFormModel();
        CandidateModel newCandidate = mock(CandidateModel.class);
        when(newCandidate.getId()).thenReturn(1);
        when(newCandidate.hasId()).thenReturn(true);
        form.setCandidate(newCandidate);

        doThrow(new AccessException("")).when(attemptRepo).getCandidate(eq(1));

        attemptService.createAttemptModelFromSetupInformation(form);
    }

    @Test
    public void failToCreateCandidate() throws AccessException {
        expectedEx.expect(HttpServerErrorException.class);

        SetupExamFormModel form = new SetupExamFormModel();
        CandidateModel newCandidate = mock(CandidateModel.class);
        when(newCandidate.hasId()).thenReturn(false);
        form.setCandidate(newCandidate);

        doThrow(new AccessException("")).when(attemptRepo).createAndGetCandidate(eq(newCandidate));

        attemptService.createAttemptModelFromSetupInformation(form);
    }

    @Test
    public void canGetModelAndViewForTestAttemptSetupPage() throws AccessException {
        ModelAndView expected = new ModelAndView("admin/setupExam");
        expected.addObject("paperReferences", Arrays.asList(PAPER_REFERENCE_MODEL_ID_1, PAPER_REFERENCE_MODEL_ID_2));
        expected.addObject("previousCandidates", Collections.singletonList(CANDIDATE_MODEL_ID_1));
        expected.addObject("formBody", new SetupExamFormModel());

        when(paperRepo.getPaperReferencesToLatestVersions()).thenReturn(Arrays.asList(PAPER_REFERENCE_MODEL_ID_1, PAPER_REFERENCE_MODEL_ID_2));
        when(attemptRepo.getAllCandidates()).thenReturn(Collections.singletonList(CANDIDATE_MODEL_ID_1));

        ModelAndView actual = attemptService.getModelAndViewForAttemptSetupPage();
        Assert.assertThat(expected, new ReflectionEquals(actual, ""));
    }

    @Test
    public void canFailToGetPreviousPaperReferences() throws AccessException {
        expectedEx.expect(HttpServerErrorException.class);
        when(paperRepo.getPaperReferencesToLatestVersions()).thenThrow(new AccessException(""));
        attemptService.getModelAndViewForAttemptSetupPage();
    }

    @Test
    public void canFailToGetExistingCandidates() throws AccessException {
        expectedEx.expect(HttpServerErrorException.class);
        when(paperRepo.getPaperReferencesToLatestVersions()).thenReturn(Arrays.asList(PAPER_REFERENCE_MODEL_ID_1, PAPER_REFERENCE_MODEL_ID_2));
        when(attemptRepo.getAllCandidates()).thenThrow(new AccessException(""));
        attemptService.getModelAndViewForAttemptSetupPage();
    }
}
