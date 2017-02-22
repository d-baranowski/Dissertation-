package uk.ac.ncl.daniel.baranowski.views;

import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.models.ExamModel;

import static uk.ac.ncl.daniel.baranowski.common.Constants.TEMPLATE_DASHBOARD;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.PAPER_PREFIX;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.PAPER_VIEW;

public class ReviewExamViewModel {
    private final ModelAndView mav;

    public ReviewExamViewModel(ExamModel examModel) {
        this.mav = new ModelAndView(TEMPLATE_DASHBOARD);
        mav.addObject("dashboardContent", "reviewExam");
        mav.addObject("paperLink", PAPER_PREFIX + PAPER_VIEW
            .replaceFirst("\\{paperId}", examModel.getPaperRef().getId() + "")
            .replaceFirst("\\{paperVersionNo}", examModel.getPaperRef().getVersionNo() + ""));
        mav.addObject("exam",examModel);
    }

    public ModelAndView getModelAndView() {
        return mav;
    }
}
