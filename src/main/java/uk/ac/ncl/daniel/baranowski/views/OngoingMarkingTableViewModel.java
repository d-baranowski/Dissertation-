package uk.ac.ncl.daniel.baranowski.views;


import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.models.SignedAttemptReferenceModel;

import java.util.Arrays;
import java.util.List;


public class OngoingMarkingTableViewModel {
    private final ModelAndView mav;

    public OngoingMarkingTableViewModel(List<SignedAttemptReferenceModel> references) {
        this.mav = new ModelAndView("fragments/viewTests/markingOngoingTable");
        mav.addObject("tableHead", Arrays.asList(
                "Attempt Id",
                "Candidate Name",
                "Paper",
                "Date",
                "Checked out by",
                "Link to mark",
                "Link to force unlock"));

        mav.addObject("viewPaperPrefix", ControllerEndpoints.PAPER_PREFIX);
        mav.addObject("viewPaperView", ControllerEndpoints.PAPER_VIEW.replace("/{paperId}", "").replace("/{paperVersionNo}", ""));
        mav.addObject("attemptReferences", references);
    }

    public ModelAndView getMav() {
        return mav;
    }
}

