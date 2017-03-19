package uk.ac.ncl.daniel.baranowski.marking;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import uk.ac.ncl.daniel.baranowski.common.enums.QuestionType;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Marker
public class MultiChoiceMarker implements AutoMarker {
    private final JsonParser jsonParser;
    private static final Logger LOGGER = Logger.getLogger(MultiChoiceMarker.class.getName());


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
        Map<String, String> markMap;
        int markVal = 0;
        try {
            markMap = (Map) jsonParser.parseMap(question.getCorrectAnswer());
        } catch (IllegalArgumentException e) {
            MarkModel mark = new MarkModel();
            mark.setComment("Auto marking failed because correct answer isn't in correct format.");
            mark.setMark(0);
            LOGGER.log(Level.WARNING, "Could not parse correct answer for multiple choice question", e);
            return mark;
        }
            for (String score : markMap.keySet()) {
                if (answerText.matches(markMap.get(score)) | answerText.toLowerCase().matches(markMap.get(score))) {
                    if (Integer.parseInt(score) > markVal) {
                        markVal = Integer.parseInt(score);
                    }
                }
            }

            MarkModel mark = new MarkModel();
            mark.setComment("Auto Marked");
            mark.setMark(markVal);
            return mark;
    }

}
