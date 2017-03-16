package uk.ac.ncl.daniel.baranowski.marking;

import com.fasterxml.jackson.core.JsonParseException;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.expression.ParseException;
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
        Map<String, Object> markMap;
        int markVal = 0;
        try {
            markMap = jsonParser.parseMap(question.getCorrectAnswer());
        } catch (IllegalArgumentException e) {
            MarkModel mark = new MarkModel();
            mark.setComment("Auto marking failed because correct answer isn't in correct format.");
            mark.setMark(0);
            LOGGER.log(Level.WARNING, "Could not parse correct answer for multiple choice question", e);
            return mark;
        }
            for (String regex : markMap.keySet()) {
                if (answerText.matches(regex) | answerText.toLowerCase().matches(regex)) {
                    if ((int) markMap.get(regex) > markVal) {
                        markVal = (int) markMap.get(regex);
                    }
                }
            }

            MarkModel mark = new MarkModel();
            mark.setComment("Auto Marked");
            mark.setMark(markVal);
            return mark;


    }

}
