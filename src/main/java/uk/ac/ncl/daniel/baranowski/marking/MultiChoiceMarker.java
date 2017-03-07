package uk.ac.ncl.daniel.baranowski.marking;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import uk.ac.ncl.daniel.baranowski.common.enums.QuestionType;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;

import java.util.Map;

@Marker
public class MultiChoiceMarker implements AutoMarker {
    private final JsonParser jsonParser;

    public MultiChoiceMarker() {
        jsonParser = JsonParserFactory.getJsonParser();
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.MULTIPLE_CHOICE;
    }

    @Override
    public MarkModel getMark(QuestionModel question, AnswerModel answer) {
        String answerText = answer.getText() != null ? answer.getText() : "";
        Map<String, Object> markMap = jsonParser.parseMap(question.getCorrectAnswer());
        int markVal = 0;

        for (String regex : markMap.keySet()) {
            if (answerText.matches(regex) | answerText.toLowerCase().matches(regex)){
                if ((int)markMap.get(regex) > markVal) {
                    markVal = (int)markMap.get(regex);
                }
            }
        }

        MarkModel mark = new MarkModel();
        mark.setComment("Auto Marked");
        mark.setMark(markVal);
        return mark;
    }

}
