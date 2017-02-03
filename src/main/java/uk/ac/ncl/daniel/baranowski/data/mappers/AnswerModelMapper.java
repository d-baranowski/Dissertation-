package uk.ac.ncl.daniel.baranowski.data.mappers;

import uk.ac.ncl.daniel.baranowski.data.access.pojos.Answer;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.AssetModel;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;

public class AnswerModelMapper {
    private AnswerModelMapper() {
        //Hiding implicit public constructor
    }

    public static AnswerModel mapAnswerModelFrom(Answer answer, MarkModel mark, AssetModel asset) {
        final AnswerModel result = new AnswerModel();
        result.setText(answer.getText());
        result.setMark(mark);
        result.setAssets(asset);
        return result;
    }

    public static Answer mapAnswerFrom(AnswerModel answerModel, int attemptId, int questionId, int questionVersionNo) {
        return new Answer.Builder()
                .setText(answerModel.getText())
                .setTestDayEntryId(attemptId)
                .setQuesionId(questionId)
                .setQuesionVersionNo(questionVersionNo)
                .build();
    }
}
