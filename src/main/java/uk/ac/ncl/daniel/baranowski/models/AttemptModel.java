package uk.ac.ncl.daniel.baranowski.models;

import java.util.Objects;

/**
 * Attempt model is con
 */
public final class AttemptModel extends AttemptReferenceModel {
    private PaperModel paper;
    private AnswersMapModel answerMap;

    public PaperModel getPaper() {
        return paper;
    }

    public void setPaper(PaperModel paper) {
        this.paper = paper;
    }

    public AnswersMapModel getAnswerMap() {
        return answerMap != null ? answerMap : new AnswersMapModel();
    }

    public void setAnswerMap(AnswersMapModel answerMap) {
        this.answerMap = answerMap;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;  //NOSONAR
        if (o == null || !(o.getClass() == this.getClass())) return false;  //NOSONAR
        if (!super.equals(o)) return false;  //NOSONAR
        AttemptModel that = (AttemptModel) o;
        return Objects.equals(paper, that.paper) &&
                Objects.equals(answerMap, that.answerMap) &&
                that.canEqual(this);
    }

    /* Read this: http://www.artima.com/lejava/articles/equality.html Pitfall #4 */
    @Override
    public final boolean canEqual(Object other) {
        return other instanceof AttemptModel;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(super.hashCode(), paper, answerMap);
    }

    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder("AttemptModel{");
        sb.append("paper=").append(paper);
        sb.append(", answerMap=").append(answerMap);
        sb.append('}');
        return sb.toString();
    }
}
