package tests;

import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.PaperRepo;
import uk.ac.ncl.daniel.baranowski.data.access.AnswerAssetDAO;
import uk.ac.ncl.daniel.baranowski.data.access.AnswerDAO;
import uk.ac.ncl.daniel.baranowski.data.access.CandidateDAO;
import uk.ac.ncl.daniel.baranowski.data.access.MarkDAO;
import uk.ac.ncl.daniel.baranowski.data.access.PaperDAO;
import uk.ac.ncl.daniel.baranowski.data.access.TestDayDAO;
import uk.ac.ncl.daniel.baranowski.data.access.TestDayEntryDAO;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.TestDayEntry;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import java.util.Collections;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;

import static uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus.FINISHED;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AttemptRepoUnitTest {
    @Mock
    private PaperRepo paperRepo;
    @Mock
    private MarkDAO markDao;
    @Mock
    private AnswerDAO answerDao;
    @Mock
    private AnswerAssetDAO assetDao;
    @Mock
    private TestDayEntryDAO attemptDao;
    @Mock
    private TestDayDAO dayDao;
    @Mock
    private CandidateDAO candidateDao;
    @Mock
    private PaperDAO paperDAO;

    @InjectMocks
    private AttemptRepo repo;

    /*private final TestDayEntry nonExistingId = new TestDayEntry.Builder().setTestDayId(15).build();*/

    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @After
    public void reset() {
        expectedEx = ExpectedException.none();
    }

  /*  @Test
    public void canGetAllAttemptReferencesWithError() throws AccessException {
        expectedEx.expect(AccessException.class);
        expectedEx.expectMessage("Failed to get all attempt references");

        mockDayDaoThrowException();
        when(attemptDao.readAll()).thenReturn(Collections.singletonList(nonExistingId));
        repo.getAllAttemptReferences();
    }*/

   /* @Test
    public void canGetAttemptReferencesByCandidateWithError() throws AccessException {
        expectedEx.expect(AccessException.class);
        expectedEx.expectMessage("Failed to get all attempt references by candidate id 99");

        mockDayDaoThrowException();
        when(attemptDao.getByCandidateId(99)).thenReturn(Collections.singletonList(nonExistingId));
        repo.getAttemptReferencesByCandidate(99);
    }*/

   /* @Test
    public void canGetAttemptReferencesByDateLocationWithError() throws AccessException {
        expectedEx.expect(AccessException.class);
        expectedEx.expectMessage("Failed to get all attempt references by date FAIL and location FAIL");

        mockDayDaoThrowException();
        when(attemptDao.getByDateLocation(anyString(), anyString())).thenReturn(Collections.singletonList(nonExistingId));
        repo.getAttemptReferencesByDateLocation("FAIL", "FAIL");
    }*/

    @Test
    public void canCreateAndGetCandidateWithError() throws Exception {
        expectedEx.expect(AccessException.class);
        CandidateModel create = new CandidateModel();
        create.setFirstName("Daniel");
        create.setSurname("Baranowski");
        expectedEx.expectMessage("Failed to submit a candidate: " + create);

        when(candidateDao.createAndGet(anyObject())).thenThrow(new EmptyResultDataAccessException(1));
        repo.createAndGetCandidate(create);
    }

    @Test
    public void canSubmitAnswerWithError() throws Exception {
        expectedEx.expect(AccessException.class);
        AnswerModel wrongAnswer = new AnswerModel();
        expectedEx.expectMessage("Failed to submit answer " + wrongAnswer + " for attempt with id 1, for question id 1 version no 1");
        doThrow(new EmptyResultDataAccessException(1)).when(answerDao).submitAnswer(anyObject());
        repo.submitAnswer(wrongAnswer, 1, 1, 1);
    }

    /*@Test
    public void canCreateAndGetTestAttemptReference() throws Exception {
        expectedEx.expect(AccessException.class);
        expectedEx.expectMessage(String.format(
                "Could not submit test attempt reference for candidate: %s \n" +
                        " Test Day: %s \n" +
                        " and Paper: %s \n",
                TestResources.CANDIDATE_MODEL_ID_1, TestResources.SAMPLE_DAY, TestResources.PAPER_REFERENCE_MODEL_ID_1));
        when(dayDao.getOrCreate(anyObject(), anyObject())).thenThrow(new EmptyResultDataAccessException(1));
        doThrow(new EmptyResultDataAccessException(1)).when(attemptDao).createAndGet(anyObject());
        repo.createAndGet(TestResources.CANDIDATE_MODEL_ID_1, TestResources.SAMPLE_DAY, TestResources.PAPER_REFERENCE_MODEL_ID_1, null, 0, null);
    }*/

    @Test
    public void canSetAttemptStatusWithError() throws AccessException {
        expectedEx.expect(AccessException.class);
        expectedEx.expectMessage(String.format("Failed to set attempt with id %s to status %s", 99, "FINISHED"));
        doThrow(new EmptyResultDataAccessException(1)).when(attemptDao).updateStatus("FINISHED", 99);
        repo.setAttemptStatus(FINISHED, 99);
    }

    private void mockDayDaoThrowException() {
        expectedEx.expect(AccessException.class);
        when(dayDao.read(anyInt())).thenThrow(new EmptyResultDataAccessException(1));
    }
}
