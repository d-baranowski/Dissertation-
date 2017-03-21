package uk.ac.ncl.daniel.baranowski.controllers;

import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.service.DashboardService;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.DASHBOARD_CURRENT_TESTS;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.DASHBOARD_GENERATE_TESTS;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.DASHBOARD_MARKED_TESTS;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.DASHBOARD_MARKING_ONGOING;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.DASHBOARD_PREFIX;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.DASHBOARD_SETTINGS;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.DASHBOARD_UNMARKED_TESTS;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.DASHBOARD_VIEW_TESTS;

/**
 * This controller provides endpoints for Generate a Test and View Tests functionality of the application.
 */
@Controller
@PreAuthorize("hasAnyAuthority('Marker', 'Admin', 'Author','ModuleLeader')")
@RequestMapping(DASHBOARD_PREFIX)
public class DashboardController {

    private final DashboardService dashboardService;
    private static final Logger LOGGER = Logger.getLogger(DashboardController.class.getName());

    /**
     * Please do not use this constructor. Spring automatically initializes all classes annotated with @Controller.
     * Controller constructor has to be public.
     * Parameters will be automatically injected due to use of @Autowired.
     * @param dashboardService NOSONAR
     */
    @Autowired
    public DashboardController(DashboardService dashboardService) { //NOSONAR This will never be instantiated in code. Controllers are instantiated automagically by Spring
        this.dashboardService = dashboardService;
    }

    /**
        This endpoint leads to the page which allows the admin to set up the Attempt for the candidate.
        It provides a form and outputs any validation errors along with it. The validation errors are caused by @Valid
        annotations inside SetupExamFormModel class implementation.

        @ModelAttribute annotation is used to allow repopulating form fields with the same values in case user enters invalid
        values and is redirected back to this endpoint, same values will be populated in the form fields,
        and any error message will be appended inside errors attribute.

        NOSONAR Because it was pointing out that I should use an interface instead of concrete implementation,
        however that would cause an error, when spring will try to instantiate the parameter annotated with @ModelAttribute
     **/
    @RequestMapping(DASHBOARD_GENERATE_TESTS)
    public ModelAndView generateTests(@ModelAttribute("errors") ArrayList<FieldError> errors, @ModelAttribute("target") SetupExamFormModel target, HttpSession loggedInUser) { //NOSONAR
        return dashboardService.getGenerateTestViewModel(errors, target, loggedInUser);
    }

    /**
     * This endpoint leads to the page where users can see test attempts in different states.
     * The page will be populated using ajax calls.
     */

	@RequestMapping(DASHBOARD_VIEW_TESTS)
	public ModelAndView viewTestsFragment() {
        return dashboardService.getViewTestsFragment();
	}

    @RequestMapping(DASHBOARD_SETTINGS)
    public ModelAndView viewSettingsFragment(){
        return dashboardService.getSettingsFragment();
    }

    /**
     * These are just fragments which are injected onto a screen using ajax inside the view tests endpoint.
     * @return
     */
	@RequestMapping(DASHBOARD_CURRENT_TESTS)
	public ModelAndView currentTestsFragment() {
        return dashboardService.getCurrentTestsFragment();
	}

	@RequestMapping(DASHBOARD_MARKED_TESTS)
	public ModelAndView markedTestsFragment() {
        return dashboardService.getMarkedTestsFragment();
    }

	@RequestMapping(DASHBOARD_UNMARKED_TESTS)
	public ModelAndView unmarkedTestsFragment() {
        return dashboardService.getUnmarkedTestsFragment();
	}

    @RequestMapping(DASHBOARD_MARKING_ONGOING)
    public ModelAndView ongoingMarkingFragment() {
        return dashboardService.getOngoingMarkingFragment();
    }

    @RequestMapping("/browse")
    public ModelAndView browse() {
        ModelAndView mav = new ModelAndView("browse");
        mav.addObject("dashboardContent","browse");
        return mav;
    }

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("create");
        mav.addObject("dashboardContent","edit");
        return mav;
    }

    @RequestMapping("/help")
    public ModelAndView help() {
        ModelAndView mav = new ModelAndView("help");
        mav.addObject("dashboardContent","help");
        return mav;
    }
}
