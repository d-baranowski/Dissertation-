package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.AnswerAsset;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersionAsset;
import uk.ac.ncl.daniel.baranowski.models.AssetModel;

public class AssetModelMapper {
    private AssetModelMapper() {
        //Hiding implicit public constructor
    }

    public static AnswerAsset mapAnswerAssetFrom(AssetModel assetModel, int questionId, int questionVersionNo, int attemptId) {
        return new AnswerAsset.Builder()
                .setQuestionId(questionId)
                .setQuestionVersionNo(questionVersionNo)
                .setTestDayEntryId(attemptId)
                .setReferenceName(assetModel.getReferenceName())
                .setFileType(assetModel.getFileType())
                .setId(assetModel.getId())
                .setFile(assetModel.getFile())
                .build();
    }

    public static AssetModel mapAssetModelFrom(AnswerAsset asset) {
        final AssetModel assetModel = new AssetModel();
        assetModel.setId(asset.getId());
        assetModel.setFileType(asset.getFileType());
        assetModel.setFile(asset.getFile());
        assetModel.setReferenceName(asset.getReferenceName());

        return assetModel;
    }

    public static AssetModel mapAssetModelFrom(QuestionVersionAsset qa) {
        final AssetModel result = new AssetModel();
        result.setId(qa.getId());
        result.setFileType(qa.getFileType());
        result.setFile(qa.getFile());
        result.setReferenceName(qa.getReferenceName());

        return result;
    }

}
