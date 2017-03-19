package uk.ac.ncl.daniel.baranowski.marking;

import uk.ac.ncl.daniel.baranowski.common.enums.QuestionType;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;

public interface AutoMarker {
    public QuestionType getQuestionType();
    public MarkModel getMark(QuestionModel question, AnswerModel answer);
}
