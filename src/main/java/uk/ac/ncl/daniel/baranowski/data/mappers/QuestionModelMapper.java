package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Question;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersion;
import uk.ac.ncl.daniel.baranowski.models.AssetModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionReferenceModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionModelMapper {
    private QuestionModelMapper() {
        //Hiding implicit public constructor
    }

    public static QuestionReferenceModel mapQuestionReferenceModelFrom(Question q, int versionNo, int timeScale) {
        final QuestionReferenceModel ref = new QuestionReferenceModel();
        ref.setId(q.getId());
        ref.setLanguage(q.getLanguage());
        ref.setReferenceName(q.getReferenceName());
        ref.setVersionNo(versionNo);
        ref.setDifficulty(q.getDifficulty());
        ref.setType(q.getType());
        ref.setTimeScale(timeScale);
        return ref;
    }

    public static QuestionModel mapQuestionModelFrom(Question q, QuestionVersion qv, List<AssetModel> assets) {
        final QuestionModel result = new QuestionModel();

        result.setId(q.getId());
        result.setVersionNo(qv.getVersionNo());
        result.setLanguage(q.getLanguage());
        result.setDifficulty(q.getDifficulty());
        result.setTimeScale(qv.getTimeScale());
        result.setReferenceName(q.getReferenceName());
        result.setType(q.getType());
        result.setText(qv.getText());
        result.setCorrectAnswer(qv.getCorrectAnswer());
        result.setMarkingGuide(qv.getMarkingGuide());
        result.setAssets(assets);

        return result;
    }

    public static Question mapQuestionFrom(QuestionModel q) {
        return new Question.Builder()
                .setId(q.getId())
                .setDifficulty(q.getDifficulty())
                .setLanguage(q.getLanguage())
                .setReferenceName(q.getReferenceName())
                .setType(q.getType())
                .build();
    }

    public static QuestionVersion mapQuestionVersionFrom(QuestionModel q) {
        return new QuestionVersion.Builder()
                .setCorrectAnswer(q.getCorrectAnswer())
                .setMarkingGuide(q.getMarkingGuide())
                .setQuestionId(q.getId())
                .setText(q.getText())
                .setTimeScale(q.getTimeScale())
                .setVersionNo(q.getVersionNo())
                .build();
    }

    public static List<AssetModel> mapQuestionAssetsFrom(QuestionModel q) {
        return q.getAssets() != null ? q.getAssets() : new ArrayList<AssetModel>(0);
    }

}
