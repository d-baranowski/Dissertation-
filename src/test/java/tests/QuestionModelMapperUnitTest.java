package tests;

import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionReferenceModel;
import java.util.ArrayList;
import org.junit.Test;

import static uk.ac.ncl.daniel.baranowski.data.mappers.QuestionModelMapper.mapQuestionModelFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.QuestionModelMapper.mapQuestionReferenceModelFrom;
import static org.junit.Assert.assertTrue;
import static tests.TestResources.QUESTION_ID_1;
import static tests.TestResources.QUESTION_VERSION_SAMPLE;
import static tests.TestResources.SAMPLE_QUESTION;
import static tests.utility.MapperUnitTestUtility.areAllFieldsDifferent;

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
