package uk.ac.ncl.daniel.baranowski.views;

import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.models.ExamModel;

import static uk.ac.ncl.daniel.baranowski.common.Constants.TEMPLATE_DASHBOARD;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.*;

public class ReviewExamViewModel {
    private final ModelAndView mav;

    public ReviewExamViewModel(ExamModel examModel) {
        this.mav = new ModelAndView(TEMPLATE_DASHBOARD);
        mav.addObject("dashboardContent", "reviewExam");
        mav.addObject("paperLink", PAPER_PREFIX + PAPER_VIEW
            .replaceFirst("\\{paperId}", examModel.getPaperRef().getId() + "")
            .replaceFirst("\\{paperVersionNo}", examModel.getPaperRef().getVersionNo() + ""));
        mav.addObject("exam",examModel);
        mav.addObject("BEGIN_ENDPOINT", EXAM_PREFIX + EXAM_BEGIN.replaceFirst("\\{examId}", examModel.getId() + ""));
        mav.addObject("END_ENDPOINT", EXAM_PREFIX + EXAM_FINNISH.replaceFirst("\\{examId}", examModel.getId() + ""));
        mav.addObject("EXAM_STATUS", examModel.getStatus().toString());
        mav.addObject("CREATED", ExamStatus.CREATED.toString());
        mav.addObject("STARTED", ExamStatus.STARTED.toString());
        mav.addObject("FINISHED",ExamStatus.FINISHED.toString());
        mav.addObject("MARKING_ONGOING", ExamStatus.MARKING_ONGOING.toString());
    }

    public ModelAndView getModelAndView() {
        return mav;
    }
}
