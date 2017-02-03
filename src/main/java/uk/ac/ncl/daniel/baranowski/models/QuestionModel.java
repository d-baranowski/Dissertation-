package uk.ac.ncl.daniel.baranowski.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class QuestionModel extends QuestionReferenceModel {
    private static final String MULTIPLE_CHOICE_QUESTION = "Multiple Choice";
    private static final String CHOICE_PATTERN = "([A-Z])\\)";
    private String text;
    private String correctAnswer;
    private String markingGuide;
    private List<AssetModel> assets;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getMarkingGuide() {
        return markingGuide;
    }

    public void setMarkingGuide(String markingGuide) {
        this.markingGuide = markingGuide;
    }

    public List<AssetModel> getAssets() {
        return assets;
    }

    public void setAssets(List<AssetModel> assets) {
        this.assets = assets;
    }

    public List<String> getAnswerLetters() {
        if (this.getType().equals(MULTIPLE_CHOICE_QUESTION)) {
            List<String> allMatches = new ArrayList<>();
            Matcher m = Pattern.compile(CHOICE_PATTERN).matcher(this.getText());
            while (m.find()) {
                allMatches.add(m.group());
            }
            for (int i = 0; i < allMatches.size(); i++) {
                String s = allMatches.get(i).replace(")", "");
                allMatches.set(i, s);
            }
            return allMatches;
        } else {
            return new ArrayList<>();
        }
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    @Override
    public final boolean canEqual(Object other) {
        return other instanceof QuestionModel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        if (!super.equals(o)) return false; //NOSONAR
        QuestionModel that = (QuestionModel) o;
        return Objects.equals(getText(), that.getText()) && //NOSONAR
                Objects.equals(getCorrectAnswer(), that.getCorrectAnswer()) &&
                Objects.equals(getMarkingGuide(), that.getMarkingGuide()) &&
                Objects.equals(getAssets(), that.getAssets()) &&
                that.canEqual(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getText(), getCorrectAnswer(), getMarkingGuide(), getAssets());
    }

    @Override
    public String toString() {
        return "QuestionModel{" +
                "text='" + text + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", markingGuide='" + markingGuide + '\'' +
                ", assets=" + assets +
                '}';
    }
}
