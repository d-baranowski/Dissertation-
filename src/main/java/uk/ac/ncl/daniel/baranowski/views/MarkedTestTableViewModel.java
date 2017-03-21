package uk.ac.ncl.daniel.baranowski.views;

import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.models.SignedAttemptReferenceModel;

import java.util.Arrays;
import java.util.List;

public class MarkedTestTableViewModel {
    private final ModelAndView mav;

    public MarkedTestTableViewModel(List<SignedAttemptReferenceModel> references) {
        this.mav = new ModelAndView("fragments/viewTests/markedAttemptsTable");
        mav.addObject("tableHead",Arrays.asList(
                        "Attempt Id",
                        "Candidate Name",
                        "Paper",
                        "Date",
                        "Marked by",
                        "Final Mark"));

        mav.addObject("viewPaperPrefix", ControllerEndpoints.PAPER_PREFIX);
        mav.addObject("viewPaperView", ControllerEndpoints.PAPER_VIEW.replace("/{paperId}", "").replace("/{paperVersionNo}", ""));
        mav.addObject("attemptReferences", references);
    }

    public ModelAndView getMav() {
        return mav;
    }
}
