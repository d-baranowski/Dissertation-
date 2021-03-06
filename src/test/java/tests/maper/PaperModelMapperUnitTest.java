package tests.maper;

import org.junit.Test;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Paper;
import uk.ac.ncl.daniel.baranowski.models.PaperModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;

import static org.junit.Assert.assertTrue;
import static tests.TestResources.*;
import static tests.utility.MapperUnitTestUtility.areAllFieldsDifferent;
import static uk.ac.ncl.daniel.baranowski.data.mappers.PaperModelMapper.*;

public class PaperModelMapperUnitTest {
    @Test
    public void canSetAllFieldsToPaperReferenceModel() throws IllegalAccessException {
        final PaperReferenceModel before = new PaperReferenceModel();
        final PaperReferenceModel after = mapPaperReferenceModelFrom(PAPER_ID_1, 1);
        assertTrue(areAllFieldsDifferent(before, after, null));
    }

    @Test
    public void canSetAllFieldToPaperModel() throws IllegalAccessException {
        final PaperModel before = new PaperModel();
        final PaperModel after = mapPaperModelFrom(PAPER_ID_1, PAPER_VERSION_ID_1_VERNO_1, PAPER_SECTIONS_FOR_PAPER_MODEL_ID_1);
        assertTrue(areAllFieldsDifferent(before, after, null));
    }

    @Test
    public void canSetALlFieldToPaper() throws IllegalAccessException {
        final Paper before = new Paper.Builder().build();
        final Paper after = mapPaperFrom(PAPER_REFERENCE_MODEL_ID_1);
        assertTrue(areAllFieldsDifferent(before, after, null));
    }
}
