package tests.maper;

import org.junit.Test;
import uk.ac.ncl.daniel.baranowski.models.SectionModel;
import uk.ac.ncl.daniel.baranowski.models.SectionReferenceModel;

import static org.junit.Assert.assertTrue;
import static tests.TestResources.*;
import static tests.utility.MapperUnitTestUtility.areAllFieldsDifferent;
import static uk.ac.ncl.daniel.baranowski.data.mappers.SectionReferenceModelMapper.mapSectionModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.SectionReferenceModelMapper.mapSectionReferenceModelFrom;

public class SectionReferenceModelMapperUnitTest {
    @Test
    public void canSetAllFieldsToSectionReferenceModel() throws IllegalAccessException {
        final SectionReferenceModel before = new SectionReferenceModel();
        final SectionReferenceModel after = mapSectionReferenceModelFrom(SECTION_VERSION_ID_1_VERNO_1, 1 + "Hello");
        assertTrue(areAllFieldsDifferent(before, after, null));
    }

    @Test
    public void canSetAllFieldsToSectionModel() throws IllegalAccessException {
        final SectionModel before = new SectionModel();
        final SectionModel after = mapSectionModelFrom(SECTION_SAMPLE, SECTION_VERSION_SAMPLE, SECTION_QUESTIONS_FOR_ID_1_VER_1);
        assertTrue(areAllFieldsDifferent(before, after, null));
    }
}
