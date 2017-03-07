package uk.ac.ncl.daniel.baranowski.marking;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import uk.ac.ncl.daniel.baranowski.common.enums.QuestionType;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;

import java.lang.annotation.Annotation;
import java.util.Map;

@Component
public class AutoMarkerHelper implements AutoMarker {
    private final ApplicationContext context;

    @Autowired
    public AutoMarkerHelper(ApplicationContext context) {
        this.context = context;
    }

    public Map<String, AutoMarker> getAllAutoMarkers() {
        return (Map) context.getBeansWithAnnotation((Class<? extends Annotation>) Marker.class);
    }

    @Override
    public QuestionType getQuestionType() {
        return null;
    }

    @Override
    public MarkModel getMark(QuestionModel question, AnswerModel answer) {
        for (AutoMarker marker : getAllAutoMarkers().values()) {
            if (question.getType().equals(marker.getQuestionType().toString())) {
                return marker.getMark(question,answer);
            }
        }
        return null;
    }
}
