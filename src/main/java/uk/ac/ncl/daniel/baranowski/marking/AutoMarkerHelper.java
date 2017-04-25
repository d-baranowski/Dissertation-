package uk.ac.ncl.daniel.baranowski.marking;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import uk.ac.ncl.daniel.baranowski.common.enums.QuestionType;
import uk.ac.ncl.daniel.baranowski.data.UserRepo;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.UserReferenceModel;

import java.lang.annotation.Annotation;
import java.util.Map;

@Component
public class AutoMarkerHelper implements AutoMarker {
    private final ApplicationContext context;
    private final UserRepo userRepo;
    private final UserReferenceModel autoMarker;

    @Autowired
    public AutoMarkerHelper(ApplicationContext context, UserRepo userRepo) {
        this.context = context;
        this.userRepo = userRepo;
        this.autoMarker = userRepo.getUser("AUTO-MARKER");
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
                MarkModel autoMark = marker.getMark(question,answer);
                autoMark.setMarker(autoMarker);
                return autoMark;
            }
        }
        return null;
    }
}
