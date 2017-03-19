package tests.maper;

import org.junit.Test;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.AnswerAsset;
import uk.ac.ncl.daniel.baranowski.models.AssetModel;

import static org.junit.Assert.assertTrue;
import static tests.TestResources.*;
import static tests.utility.MapperUnitTestUtility.areAllFieldsDifferent;
import static uk.ac.ncl.daniel.baranowski.data.mappers.AssetModelMapper.mapAnswerAssetFrom;
import static uk.ac.ncl.daniel.baranowski.data.mappers.AssetModelMapper.mapAssetModelFrom;

public class AssetModelMapperUnitTest {
    @Test
    public void canSetAllFieldsToAnswerAsset() throws IllegalAccessException {
        final AnswerAsset before = new AnswerAsset.Builder().build();
        final AnswerAsset after = mapAnswerAssetFrom(QUESTION_ASSET_MODEL_ID_1, 1, 1, 1);
        assertTrue(areAllFieldsDifferent(before, after, null));
    }

    @Test
    public void canSetAllFieldsToAssetModelFromAnswerAsset() throws IllegalAccessException {
        final AssetModel before = new AssetModel();
        final AssetModel after = mapAssetModelFrom(ANSWER_ASSET_EXAMPLE);

        assertTrue(areAllFieldsDifferent(before, after, null));
    }

    @Test
    public void canSetAllFieldsToAssetModelFromQuestionVersionAsset() throws IllegalAccessException {
        final AssetModel before = new AssetModel();
        final AssetModel after = mapAssetModelFrom(QUESTION_VERSION_ASSET_ID_1);

        assertTrue(areAllFieldsDifferent(before, after, null));
    }
}
