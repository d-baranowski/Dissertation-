package uk.ac.ncl.daniel.baranowski.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class PaperModel extends PaperReferenceModel {
    @NotNull(message = "Please add a instructions text before submitting")
    @Size(min = 5, max = 50000, message = "Paper instructions text too long or too short.")
    private String instructionsText;
    private Map<Integer, SectionModel> sections;

    public PaperModel() {
        super();
        this.sections = new HashMap<>();
        this.instructionsText = "";
    }

    public String getInstructionsText() {
        return instructionsText;
    }

    public void setInstructionsText(String instructionsText) {
        this.instructionsText = instructionsText;
    }

    public Map<Integer, SectionModel> getSections() {
        return sections;
    }

    /**
     * This method return a total number of questions to answer
     * NOT total number of questions in the test.
     * For Example: If the test has 2 sections and each contains
     * 4 questions, but each only requires the candidate to answer 1 of these available questions
     * this method will return 4 (4 * 1).
     **/
    public int getTotalQuestionsToAnswer() {
        int totalQuestions = 0;
        for (SectionModel section : getSections().values()) {
            totalQuestions += section.getNoOfQuestionsToAnswer();
        }
        return totalQuestions;
    }

    public void setSections(Map<Integer, SectionModel> sections) {
        this.sections = sections;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true; //NOSONAR
        if (o == null || getClass() != o.getClass()) return false; //NOSONAR
        if (!super.equals(o)) return false; //NOSONAR
        PaperModel that = (PaperModel) o;
        return Objects.equals(getInstructionsText(), that.getInstructionsText()) &&
                Objects.equals(getSections(), that.getSections()) &&
                that.canEqual(this);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(super.hashCode(), getInstructionsText(), getSections());
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    @Override
    public final boolean canEqual(Object other) {
        return other instanceof PaperModel;
    }

    @Override
    public String toString() {
        return "PaperModel{" +
                "instructionsText='" + instructionsText + '\'' +
                ", sections=" + sections +
                '}';
    }
}
