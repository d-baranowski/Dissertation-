package tests.repo;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tests.TestResources;
import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.data.AttemptRepo;
import uk.ac.ncl.daniel.baranowski.data.exceptions.AccessException;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;

import static tests.TestResources.TEST_ATTEMPT_MODEL_ID_1;
import static tests.TestResources.USER_ALL;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@TestPropertySource(locations="classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@WebAppConfiguration
public class AttemptRepoIntegrationTest {
    @Autowired
    private AttemptRepo repo;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @After
    public void reset() {
        expectedEx = ExpectedException.none();
    }

    private static CandidateModel sampleCandidate;

    @BeforeClass
    public static void setup() {
        sampleCandidate = new CandidateModel();
        sampleCandidate.setFirstName("Ania");
        sampleCandidate.setSurname("Przybylska");
        sampleCandidate.setId(3);
    }

    @Test
    public void canGetAttemptReferenceModelWithError() throws AccessException {
        expectedEx.expect(AccessException.class);
        expectedEx.expectMessage("Failed to get test attempt reference with id 99 ");
        repo.getAttemptReferenceModel(99);
    }

    @Test
    public void canGetAttemptModelWithError() throws AccessException {
        expectedEx.expect(AccessException.class);
        expectedEx.expectMessage("Failed to get attempt model with id 99");
        repo.getAttemptModel(99);
    }

    @Test
    public void canGetCandidate() throws AccessException {
        Assert.assertEquals(TestResources.CANDIDATE_MODEL_ID_1, repo.getCandidate(1));
    }

    @Test
    public void canLockAttemptForMarking() throws AccessException {
        repo.lockAttemptForMarking(TEST_ATTEMPT_MODEL_ID_1.getId(), USER_ALL.getId());
    }

    @Test
    public void canUnlockAttempt() throws AccessException {
        repo.unlockAttemptForMarking(TEST_ATTEMPT_MODEL_ID_1.getId());
    }
}
