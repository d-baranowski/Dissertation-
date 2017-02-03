package uk.ac.ncl.daniel.baranowski.controllers;

import uk.ac.ncl.daniel.baranowski.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;

/**
 * This controller is responsible for the View Paper functionality of our application.
 */
@Controller
@RequestMapping(ControllerEndpoints.PAPER_PREFIX)
@PreAuthorize("hasAnyAuthority('Marker', 'Admin', 'Author')")
public class PaperController {

    private final PaperService service;

    /**
     * Please do not use this constructor. Spring automatically initializes all classes annotated with @Controller.
     * Controller constructor has to be public.
     * Parameters will be automatically injected due to use of @Autowired.
     * @param service NOSONAR
     */
    @Autowired
    public PaperController(PaperService service) {
        this.service = service;
    }

    /**
     * Uses the variables from the path to display the view paper page to the user.
     * Different test papers will be resolved based on the path variables
     * @param id Id of test paper.
     * @param versionNo  Version number of test paper.
     * @return A page with a test paper. No marking or Answering functionality is provided here.
     */
    @RequestMapping(ControllerEndpoints.PAPER_VIEW)
    public ModelAndView viewPaper(@PathVariable("paperId") int id, @PathVariable("paperVersionNo") int versionNo) {
        return service.getViewPaperModelAndView(id, versionNo);
    }

    /**
     * User can find a paper he wants to view through this page.
     * @return A page containing a list of all the test papers and their versions.
     */
    @RequestMapping("test-library")
    public ModelAndView testLibrary() {
        return service.getViewTestLibrary();
    }
}
