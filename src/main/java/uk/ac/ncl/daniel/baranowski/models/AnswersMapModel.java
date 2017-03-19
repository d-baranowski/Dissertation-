package uk.ac.ncl.daniel.baranowski.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AnswersMapModel {
    private final Map<Integer, Map<Integer, AnswerModel>> map;

    public AnswersMapModel() {
        map = new HashMap<>();
    }

    public AnswerModel get(int sectionNo, int questionNo) {
        Map<Integer, AnswerModel> first = map.get(sectionNo);
        AnswerModel second;
        if (first != null) {
            second = first.get(questionNo);
            return second != null ? second : new AnswerModel();
        }

        return new AnswerModel();
    }

    public void put(int sectionNo, int questionNo, AnswerModel model) {
        if (map.get(sectionNo) == null) {
            map.put(sectionNo, new HashMap<>());
        }

        map.get(sectionNo).put(questionNo, model);
    }

    //This method is used inside answerable template fragments
    public String getNotNullAnswerText(int sectionNo, int questionNo) {
        final Map<Integer, AnswerModel> first = map.get(sectionNo);
        if (first != null) {
            final AnswerModel second = first.get(questionNo);
            if (second != null) {
                return second.getText() == null ? "" : second.getText();
            }
        }

        return "";
    }

    //This method is used inside answerable template fragments
    public String getNotNullAnswerTextShort(int sectionNo, int questionNo) {
        final Map<Integer, AnswerModel> first = map.get(sectionNo);
        if (first != null) {
            final AnswerModel second = first.get(questionNo);
            if (second != null) {
                if (second.getText() != null) {
                    if (second.getText().length() > 25) {
                        return second.getText().substring(0, 25) + "...";
                    }
                    return second.getText();
                }
                return "";
            }
        }

        return "";
    }

    //This method is used inside answerable template fragments
    public boolean isChecked(int sectionNo, int questionNo, char letter) {
        final String answerText = getNotNullAnswerText(sectionNo, questionNo);
        for (char c : answerText.toCharArray()) {
            if (letter == c) {
                return true;
            }
        }

        return false;
    }

    //This method is used inside answerable template fragments
    public boolean isQuestionAnswered(int sectionNo, int questionNo) {
        final Map<Integer, AnswerModel> first = map.get(sectionNo);
        if (first != null) {
            return first.get(questionNo) != null;
        }

        return false;
    }

    //This method is used inside answerable template fragments
    public String getNonNullMarkComment(int sectionNo, int questionNo) {
        final Map<Integer, AnswerModel> first = map.get(sectionNo);
        if (first != null) {
            final AnswerModel second = first.get(questionNo);
            if (second != null) {
                final MarkModel mark = second.getMark();
                if (mark != null) {
                    return mark.getComment() == null ? "" : mark.getComment();
                }
            }
        }

        return "";
    }

    public boolean isQuestionMarked(int sectionNo, int questionNo) {
        final Map<Integer, AnswerModel> first = map.get(sectionNo);
        if (first != null) {
            final AnswerModel second = first.get(questionNo);
            if (second != null) {
                final MarkModel mark = second.getMark();
                return mark != null;
            }
        }

        return false;
    }

    //This method is used inside answerable template fragments
    public int getNonNullMark(int sectionNo, int questionNo) {
        final Map<Integer, AnswerModel> first = map.get(sectionNo);
        if (first != null) {
            final AnswerModel second = first.get(questionNo);
            if (second != null) {
                final MarkModel mark = second.getMark();
                if (mark != null) {
                    return mark.getMark();
                }
            }
        }

        return -1;
    }

    @Override
    public final boolean equals(Object o) { //NOSONAR
        if (this == o) return true; //NOSONAR
        if (!(o instanceof AnswersMapModel)) {
            return false;
        }
        AnswersMapModel that = (AnswersMapModel) o;
        return Objects.equals(map, that.map);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(map);
    }

    @Override
    public String toString() {
        return String.format("AnswersMapModel [map=%s]", map);
    }
}
