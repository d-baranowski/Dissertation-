package uk.ac.ncl.daniel.baranowski.marking;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import uk.ac.ncl.daniel.baranowski.common.enums.QuestionType;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;

import java.util.LinkedHashMap;
import java.util.Map;

@Marker
public class ExpressionMarker implements AutoMarker {
    private final JsonParser jsonParser;

    public ExpressionMarker() {
        jsonParser = JsonParserFactory.getJsonParser();
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.EXPRESSION;
    }

    @Override
    public MarkModel getMark(QuestionModel question, AnswerModel answer) {
        String answerText = answer.getText() != null ? answer.getText() : "";
        Map<String, Object> markMap = jsonParser.parseMap(question.getCorrectAnswer());
        Map<String, Object> answerMap = jsonParser.parseMap(answerText);

        int markVal = 0;

        for (String blankNo : markMap.keySet()) {
            String regex = getRowFromMarkMap(markMap,blankNo).get("regex");
            Integer mark = Integer.parseInt(getRowFromMarkMap(markMap,blankNo).get("mark"));
            if (((String)answerMap.get(blankNo)).matches(regex)) {
                markVal += mark;
            }
        }

        MarkModel mark = new MarkModel();
        mark.setComment("Auto Marked");
        mark.setMark(markVal);
        return mark;
    }

    private LinkedHashMap<String,String> getRowFromMarkMap(Map<String, Object> markMap, String blankNo) {
        return (LinkedHashMap<String, String>) markMap.get(blankNo);
    }

}
