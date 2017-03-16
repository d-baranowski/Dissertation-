package tests.maper;

import org.junit.Test;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Candidate;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;

import static org.junit.Assert.assertTrue;
import static tests.TestResources.CANDIDATE_ID_1;
import static tests.TestResources.CANDIDATE_MODEL_ID_1;
import static tests.utility.MapperUnitTestUtility.areAllFieldsDifferent;
import static uk.ac.ncl.daniel.baranowski.data.mappers.CandidateModelMapper.mapCandidateFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.CandidateModelMapper.mapCandidateModelFrom;

public class CandidateModelMapperUnitTest {
    @Test
    public void canSetAllFieldsToCandidateModel() throws IllegalAccessException {
        final CandidateModel before = new CandidateModel();
        final CandidateModel after = mapCandidateModelFrom(CANDIDATE_ID_1);
        assertTrue(areAllFieldsDifferent(before, after, null));
    }

    @Test
    public void canSetAllFieldsToCandidate() throws IllegalAccessException {
        final Candidate before = new Candidate.Builder().build();
        final Candidate after = mapCandidateFrom(CANDIDATE_MODEL_ID_1);
        assertTrue(areAllFieldsDifferent(before, after, null));
    }
}
