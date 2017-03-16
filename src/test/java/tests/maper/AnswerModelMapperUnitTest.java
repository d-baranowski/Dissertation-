package tests.maper;

import org.junit.Test;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Answer;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.Assert.assertTrue;
import static tests.TestResources.*;
import static tests.utility.MapperUnitTestUtility.areAllFieldsDifferent;
import static uk.ac.ncl.daniel.baranowski.data.mappers.AnswerModelMapper.mapAnswerFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.AnswerModelMapper.mapAnswerModelFrom;

public class AnswerModelMapperUnitTest {
    @Test
    public void canToSetAllFieldsToAnswerModel() throws IllegalAccessException {
        AnswerModel before = new AnswerModel();
        AnswerModel after = mapAnswerModelFrom(ANSWER_ID_1, SAMPLE_MARK_MODEL, QUESTION_ASSET_MODEL_ID_1);
        assertTrue(areAllFieldsDifferent(before, after, null));
    }

    @Test
    public void canToSetAllFieldsExceptMarkIdToAnswer() throws IllegalAccessException, NoSuchFieldException {
        Field exceptThis = Answer.class.getDeclaredField("markId");
        Answer before = new Answer.Builder().build();
        Answer after = mapAnswerFrom(ANSWER_MODEL_ID_1, 1, 1, 1);
        assertTrue(areAllFieldsDifferent(before, after, Collections.singletonList(exceptThis)));
    }
}
