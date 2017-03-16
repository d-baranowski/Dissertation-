package tests.maper;

import org.junit.Test;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionReferenceModel;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static tests.TestResources.*;
import static tests.utility.MapperUnitTestUtility.areAllFieldsDifferent;
import static uk.ac.ncl.daniel.baranowski.data.mappers.QuestionModelMapper.mapQuestionModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.QuestionModelMapper.mapQuestionReferenceModelFrom;

public class QuestionModelMapperUnitTest {
    @Test
    public void canSetAllFieldsToQuestionReferenceModel() throws IllegalAccessException {
        final QuestionReferenceModel before = new QuestionReferenceModel();
        final QuestionReferenceModel after = mapQuestionReferenceModelFrom(QUESTION_ID_1, 1, 60);
        assertTrue(areAllFieldsDifferent(before, after, null));
    }

    @Test
    public void canSetAllFieldsToQuestionModel() throws IllegalAccessException, NoSuchFieldException {
        final QuestionModel before = new QuestionModel();
        final QuestionModel after = mapQuestionModelFrom(SAMPLE_QUESTION, QUESTION_VERSION_SAMPLE, new ArrayList<>());
        assertTrue(areAllFieldsDifferent(before, after, null));
    }


}
