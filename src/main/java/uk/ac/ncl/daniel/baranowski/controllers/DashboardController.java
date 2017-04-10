package uk.ac.ncl.daniel.baranowski.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.ncl.daniel.baranowski.common.HelpConstants;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.service.DashboardService;
import uk.ac.ncl.daniel.baranowski.views.components.ThumbnailLink;
import uk.ac.ncl.daniel.baranowski.views.components.ThumbnailLinkRow;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.*;

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
        ModelAndView mav = new ModelAndView("thumbnailLinkPage");
        mav.addObject("dashboardContent","browse");
        mav.addObject("title", "Browse");

        mav.addObject("thumbnailLinkRows", Arrays.asList(
                new ThumbnailLinkRow()
                    .add(new ThumbnailLink(
                            "/images/createQuestionsThumbnail.png",
                            "Questions",
                            "In the questions section you'll be able to browse user created questions.",
                            "/view-all/questions")
                    )
                    .add(new ThumbnailLink(
                            "/images/createSectionsThumbnail.png",
                            "Sections",
                            "Exam Questions can be arranged into sections. You can browse existing sections here.",
                            "/view-all/sections")
                    )
                    .add(new ThumbnailLink(
                            "/images/createPapersThumbnail.png",
                            "Papers",
                            "Sections are arranged into exam papers. You can browse them here.",
                            "/view-all/papers")
                    )
                ,
                new ThumbnailLinkRow()
                        .add(new ThumbnailLink(
                                "/images/browseAttempts.png",
                                "Attempts",
                                "A single attempt is a single candidate sitting a particular paper on a given date in a given venue. You can browse and mark existing attempts here.",
                                "/dashboard/view-tests")
                        )
                        .add(new ThumbnailLink(
                                "/images/browseExams.png",
                                "Exams",
                                "Exams are made up from multiple attempts for each student enrolled on a given module. If you prefer marking in large chunks rather than one student at once use this section. Also it lets you access details of ongoing exams.",
                                "/view-all/exams")
                        )
        ));

        return mav;
    }

    @RequestMapping("/create")
    public ModelAndView create() {
        ModelAndView mav = new ModelAndView("thumbnailLinkPage");
        mav.addObject("dashboardContent","edit");
        mav.addObject("title", "Create");
        mav.addObject("thumbnailLinkRows", Collections.singletonList(new ThumbnailLinkRow()
                .add(new ThumbnailLink(
                        "/images/createQuestionsThumbnail.png",
                        "Questions",
                        "In the questions section you'll be able to create exam questions.",
                        "/test-paper/question-editor")
                )
                .add(new ThumbnailLink(
                        "/images/createSectionsThumbnail.png",
                        "Sections",
                        "Exam Questions can be arranged into sections. Candidates can be required to answer part of the questions in each section.",
                        "/test-paper/section-editor")
                )
                .add(new ThumbnailLink(
                        "/images/createPapersThumbnail.png",
                        "Papers",
                        "Sections are arranged into exam papers.",
                        "/test-paper/paper-editor")
                )
        ));
        return mav;
    }

    @RequestMapping("/help")
    public ModelAndView help() {
        ModelAndView mav = new ModelAndView("help");

        for (Field field : HelpConstants.class.getDeclaredFields()) {
            try {
                mav.addObject(field.getName(), field.get(null));
            } catch (IllegalAccessException e) {
                continue;
            }
        }

        mav.addObject("dashboardContent","help");
        return mav;
    }
}
