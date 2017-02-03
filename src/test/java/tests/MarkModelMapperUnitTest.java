package tests;

import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.UserModel;
import org.junit.Test;

import static uk.ac.ncl.daniel.baranowski.data.mappers.MarkModelMapper.mapMarkModelFrom;
import static org.junit.Assert.assertTrue;
import static tests.TestResources.SAMPLE_MARK;
import static tests.utility.MapperUnitTestUtility.areAllFieldsDifferent;

public class MarkModelMapperUnitTest {
    @Test
    public void canSetAllFieldsToMarkModel() throws IllegalAccessException {
        final MarkModel before = new MarkModel();
        final MarkModel after = mapMarkModelFrom(SAMPLE_MARK, new UserModel());
        assertTrue(areAllFieldsDifferent(before, after, null));
    }
}
