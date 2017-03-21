package uk.ac.ncl.daniel.baranowski.marking;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import uk.ac.ncl.daniel.baranowski.common.enums.QuestionType;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Marker
public class ExpressionMarker implements AutoMarker {
    private final JsonParser jsonParser;
    private final ObjectMapper objectMapper;

    public ExpressionMarker() {
        objectMapper = new ObjectMapper();
        jsonParser = JsonParserFactory.getJsonParser();
    }

    @Override
    public QuestionType getQuestionType() {
        return QuestionType.EXPRESSION;
    }

    @Override
    public MarkModel getMark(QuestionModel question, AnswerModel answer) {
        try {
            Row[] rows = objectMapper.readValue(question.getCorrectAnswer(),Row[].class);
            Map<String,String> answersForBlanks = (Map) jsonParser.parseMap(answer.getText());
            RowsByBlankNo rowsByBlank = new RowsByBlankNo();

            for (Row row : rows) {
                if (row != null) {
                    rowsByBlank.put(row);
                }
            }

            Map<String, Integer> marksForBlank = new HashMap<>();
            for (String key: answersForBlanks.keySet()) {
                for (Row answerRow: rowsByBlank.get(key)) {
                    if (answersForBlanks.get(key).matches(answerRow.getRegex())) {
                        if (marksForBlank.containsKey(key)) {
                            if (answerRow.getMark() > marksForBlank.get(key)) {
                                marksForBlank.put(key, answerRow.getMark());
                            }
                        } else {
                            marksForBlank.put(key, answerRow.getMark());
                        }
                    }
                }
            }

            int markVal = 0;
            for (Integer mark : marksForBlank.values()) {
                markVal += mark;
            }

            MarkModel mark = new MarkModel();
            mark.setComment("Auto Marked");
            mark.setMark(markVal);
            return mark;

        } catch (IOException e) {
            e.printStackTrace();
        }

        MarkModel mark = new MarkModel();
        mark.setComment("Auto Marking failed");
        mark.setMark(0);
        return mark;
    }

    private static class RowsByBlankNo {
        private final Map<String,ArrayList<Row>> rowsByBlankNo;

        private RowsByBlankNo() {
            rowsByBlankNo = new HashMap<>();
        }

        private Map<String,ArrayList<Row>> put(Row row) {
            if (rowsByBlankNo.containsKey(row.getBlankNo().replace("[[","").replace("]]",""))) {
                if (rowsByBlankNo.get(row.getBlankNo().replace("[[","").replace("]]","")) != null) {
                    rowsByBlankNo.get(row.getBlankNo().replace("[[","").replace("]]","")).add(row);
                    return rowsByBlankNo;
                } else {
                    ArrayList<Row> list = new ArrayList<>();
                    list.add(row);
                    rowsByBlankNo.put(row.getBlankNo().replace("[[","").replace("]]",""), list);
                    return rowsByBlankNo;
                }
            } else {
                ArrayList<Row> list = new ArrayList<>();
                list.add(row);
                rowsByBlankNo.put(row.getBlankNo().replace("[[","").replace("]]",""), list);
                return rowsByBlankNo;
            }
        }

        private ArrayList<Row> get(String blankNo) {
            return rowsByBlankNo.get(blankNo);
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "space",
            "punctuation",
            "case"
    })
    private static class Options {
        @JsonProperty("space")
        private Boolean space;
        @JsonProperty("punctuation")
        private Boolean punctuation;
        @JsonProperty("case")
        private Boolean _case;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("space")
        public Boolean getSpace() {
            return space;
        }

        @JsonProperty("space")
        public void setSpace(Boolean space) {
            this.space = space;
        }

        @JsonProperty("punctuation")
        public Boolean getPunctuation() {
            return punctuation;
        }

        @JsonProperty("punctuation")
        public void setPunctuation(Boolean punctuation) {
            this.punctuation = punctuation;
        }

        @JsonProperty("case")
        public Boolean getCase() {
            return _case;
        }

        @JsonProperty("case")
        public void setCase(Boolean _case) {
            this._case = _case;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Options{");
            sb.append("space=").append(space);
            sb.append(", punctuation=").append(punctuation);
            sb.append(", _case=").append(_case);
            sb.append(", additionalProperties=").append(additionalProperties);
            sb.append('}');
            return sb.toString();
        }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonPropertyOrder({
            "blankNo",
            "answer",
            "regex",
            "mark",
            "options"
    })
    private static class Row {
        @JsonProperty("blankNo")
        private String blankNo;
        @JsonProperty("answer")
        private String answer;
        @JsonProperty("regex")
        private String regex;
        @JsonProperty("mark")
        private Integer mark;
        @JsonProperty("options")
        private Options options;
        @JsonIgnore
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        @JsonProperty("blankNo")
        public String getBlankNo() {
            return blankNo;
        }

        @JsonProperty("blankNo")
        public void setBlankNo(String blankNo) {
            this.blankNo = blankNo;
        }

        @JsonProperty("answer")
        public String getAnswer() {
            return answer;
        }

        @JsonProperty("answer")
        public void setAnswer(String answer) {
            this.answer = answer;
        }

        @JsonProperty("regex")
        public String getRegex() {
            return regex;
        }

        @JsonProperty("regex")
        public void setRegex(String regex) {
            this.regex = regex;
        }

        @JsonProperty("mark")
        public Integer getMark() {
            return mark;
        }

        @JsonProperty("mark")
        public void setMark(Integer mark) {
            this.mark = mark;
        }

        @JsonProperty("options")
        public Options getOptions() {
            return options;
        }

        @JsonProperty("options")
        public void setOptions(Options options) {
            this.options = options;
        }

        @JsonAnyGetter
        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Row{");
            sb.append("blankNo='").append(blankNo).append('\'');
            sb.append(", answer='").append(answer).append('\'');
            sb.append(", regex='").append(regex).append('\'');
            sb.append(", mark='").append(mark).append('\'');
            sb.append(", options=").append(options);
            sb.append(", additionalProperties=").append(additionalProperties);
            sb.append('}');
            return sb.toString();
        }
    }
}
