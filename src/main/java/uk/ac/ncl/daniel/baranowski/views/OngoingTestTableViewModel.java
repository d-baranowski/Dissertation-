package uk.ac.ncl.daniel.baranowski.views;

import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.servlet.ModelAndView;

public class OngoingTestTableViewModel {
    private final ModelAndView mav;

    public OngoingTestTableViewModel(List<AttemptReferenceModel> references) {
        this.mav = new ModelAndView("fragments/viewTests/ongoingAttemptsTable");
        mav.addObject("tableHead", Arrays.asList(
                        "Attempt Id",
                        "Candidate Name",
                        "Paper",
                        "Date",
                        "Location"));

        mav.addObject("viewPaperPrefix", ControllerEndpoints.PAPER_PREFIX);
        mav.addObject("viewPaperView", ControllerEndpoints.PAPER_VIEW.replace("/{paperId}", "").replace("/{paperVersionNo}", ""));
        mav.addObject("attemptReferences", references);
    }

    public ModelAndView getMav() {
        return mav;
    }
}
