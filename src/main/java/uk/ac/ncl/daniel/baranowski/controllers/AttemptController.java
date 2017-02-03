package uk.ac.ncl.daniel.baranowski.controllers;

import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.common.enums.AttemptStatus;
import uk.ac.ncl.daniel.baranowski.exceptions.SessionAttributeMissingException;
import uk.ac.ncl.daniel.baranowski.models.admin.SetupExamFormModel;
import uk.ac.ncl.daniel.baranowski.service.AdminService;
import uk.ac.ncl.daniel.baranowski.common.Constants;
import uk.ac.ncl.daniel.baranowski.common.SessionUtility;
import uk.ac.ncl.daniel.baranowski.exceptions.AttemptMissingException;
import uk.ac.ncl.daniel.baranowski.exceptions.InvalidAttemptStatusException;
import uk.ac.ncl.daniel.baranowski.exceptions.InvalidUserStateException;
import uk.ac.ncl.daniel.baranowski.exceptions.NotLockedForMarkingException;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitAnswerFormModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitMarkFormModel;
import uk.ac.ncl.daniel.baranowski.service.AttemptService;
import uk.ac.ncl.daniel.baranowski.service.MarkingService;
import uk.ac.ncl.daniel.baranowski.service.PaperService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_BEGUN_TEST_ATTEMPT;
import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_TIME_ALLOWED;

/**
 * This controller provides endpoints linked to the Test Attempt and actions that user can perform on it,
 * Like Taking the Test, and Marking the test.
 */
@Controller
@RequestMapping(ControllerEndpoints.ATTEMPT_PREFIX)
public class AttemptController {
    private final AttemptService attemptService;
    private final MarkingService markingService;
    private final AdminService adminService;
    private final PaperService paperService;
    private static final Logger LOGGER = Logger.getLogger(AttemptController.class.getName());
    private static final double GRACE_PERIOD = -0.3;

    /**
     * Please do not use this constructor. Spring automatically initializes all classes annotated with @Controller.
     * Controller constructor has to be public.
     * Parameters will be automatically injected due to use of @Autowired.
     * @param attemptService NOSONAR
     * @param adminService NOSONAR
     * @param markingService NOSONAR
     */
    @Autowired
    public AttemptController(AttemptService attemptService, AdminService adminService, MarkingService markingService, PaperService paperService) {
        this.attemptService = attemptService;
        this.markingService = markingService;
        this.adminService = adminService;
        this.paperService = paperService;
    }

    /**
     * When Candidate he she doesn't have the Role assigned to them so this endpoint only checks if the user is
     * authenticated.
     * If they have begun the test attempt they will be redirected to the actual test page.
     * Otherwise they will be presented a start page will Terms and Conditions to accept and a start button.
     * @param candidateSession
     * @param testAttemptId
     * @return
     */
    @RequestMapping(ControllerEndpoints.ATTEMPT_START)
    @PreAuthorize("isAuthenticated()")
    public ModelAndView showStartPage(HttpSession candidateSession, @PathVariable int testAttemptId) {
        ModelAndView result;
        if (!SessionUtility.hasBegunTestAttempt(candidateSession)) {
            attemptService.validateStatus(testAttemptId, AttemptStatus.CREATED);
            AttemptReferenceModel model = attemptService.getAttemptReferenceModel(testAttemptId);
            final String instructionsText = paperService.getInstructionsText(model.getPaperRef().getId(), model.getPaperRef().getVersionNo());
            result = new ModelAndView("testAttempt/start").addObject("model", model)
                    .addObject("timeAllowed",attemptService.getTimeAllowed(model.getId()))
                    .addObject("noNavigation", true)
                    .addObject("termsAndConditions", attemptService.getTerms())
                    .addObject("paperInstructions", instructionsText);;
        } else {
            result = new ModelAndView(ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.ATTEMPT_PREFIX + ControllerEndpoints.ATTEMPT_ONGOING);
        }

        return result;
    }

    /**
     * Candidate goes to this endpoint to display the test and answer questions.
     * The database id of the test attempt is stored in the session of the candidate.
     * Before retrieving the entire AttemptModel endpoint will first check if the status is STARTED and
     * throw appropriate error if not. If its FINISHED instead candidate is redirected to finish page,
     * otherwise the error is rethrown.
     *
     * @param candidateSession
     * @return
     */
    @RequestMapping(ControllerEndpoints.ATTEMPT_ONGOING)
    @PreAuthorize("hasAnyAuthority('Candidate')")
    public ModelAndView ongoingAttempt(HttpSession candidateSession) {
        final int attemptId = (int) candidateSession.getAttribute("attempt");
        try {
            attemptService.validateStatus(attemptId, AttemptStatus.STARTED);
        } catch (InvalidAttemptStatusException e) {
            if (e.getActual().equals(AttemptStatus.FINISHED)) {
                final String errorMsg = String.format(
                        "Candidate %s tried to go back to ongoing attempt page after finishing the attempt. Redirected him back to finish page",
                        SessionUtility.getUserDisplayName(candidateSession));
                LOGGER.log(Level.INFO,errorMsg, e);
                return new ModelAndView(ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.ATTEMPT_PREFIX + ControllerEndpoints.ATTEMPT_FINISH_PAGE);
            }

            throw e;
        }

        return attemptService.getModelAndViewForAttempt(attemptId);
    }

    @RequestMapping(ControllerEndpoints.ATTEMPT_FINISH_PAGE)
    @PreAuthorize("hasAnyAuthority('Candidate')")
    public ModelAndView showFinishPage() {
        return new ModelAndView("testAttempt/finish").addObject("noNavigation", true);
    }

    /**
     * If logged in user is marking test attempt with this id, check if status is MARKING_ONGOING,
     * Otherwise start marking it, unless it is being marked by someone else.
     * @param testAttemptId
     * @param markerSession
     * @return
     */
    @RequestMapping(ControllerEndpoints.ATTEMPT_MARK)
    @PreAuthorize("hasAnyAuthority('Marker')")
    public ModelAndView mark(@PathVariable int testAttemptId, HttpSession markerSession) {
        if (markingService.userIsMarking(testAttemptId, SessionUtility.getUserId(markerSession))) {
            attemptService.validateStatus(testAttemptId, AttemptStatus.MARKING_ONGOING);
        } else try {
            if(markingService.isInMarking(testAttemptId)) {
                throw new NotLockedForMarkingException("This attempt is currently being marked by a different user.", testAttemptId, null);
            } else {
                attemptService.validateStatus(testAttemptId, AttemptStatus.FINISHED);
                markingService.startMarkingAttempt(testAttemptId,markerSession);
            }
        } catch (Exception e) {
            String msg = String.format("Attempt with id: %s does not exist", testAttemptId);
            throw new AttemptMissingException(msg,e);
        }
        return markingService.getMarkableViewForTestAttempt(testAttemptId);
    }

    /**
     * API Endpoints.
     */

    /**
     * This endpoint is hit when candidate presses finish and submit on the Ongoing Attempt screen.
     * This is done using ajax inside candidaPaperAnswerable.js file.
     * @param session
     * @return
     */
    @RequestMapping(value = ControllerEndpoints.ATTEMPT_COMPLETE, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Candidate')")
    public String testCompleted(HttpSession session) {
        attemptService.validateStatus(SessionUtility.getCandidatesTestAttemptId(session), AttemptStatus.STARTED);
        attemptService.finishAttempt(session);
        return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.ATTEMPT_PREFIX + ControllerEndpoints.ATTEMPT_FINISH_PAGE;
    }

    /**
     * IMPORTANT Model parameter will only hold id.
     */
    @RequestMapping(value = ControllerEndpoints.ATTEMPT_BEGIN, method = RequestMethod.POST)
    @PreAuthorize("isAuthenticated()")
    public String beginAttempt(AttemptReferenceModel model, HttpSession candidateSession) {
        candidateSession.setAttribute(Constants.SESSION_BEGUN_TEST_ATTEMPT, true);
        attemptService.validateStatus(model.getId(), AttemptStatus.CREATED);
        attemptService.beginAttempt(candidateSession, model.getId());
        return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.ATTEMPT_PREFIX + ControllerEndpoints.ATTEMPT_ONGOING;
    }

    /**
     * When user submits a question, get time remaining from the session
     * (TECHNICAL DEPT The time needs to be kept on the back end as well. )
     * timeRemaining is allowed to go negative as long as its not below the GRACE_PERIOD
     * to account for any latency when submitting questions once the time runs out.
     *
     * @param formBody Injected form object.
     * @param bindingResult Injected result of the form object validation.
     * @param candidateSession
     * @return
     */
    @RequestMapping(value = ControllerEndpoints.ATTEMPT_QUESTION_SUBMIT, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Candidate')")
    public @ResponseBody Object submitQuestion(@Valid SubmitAnswerFormModel formBody, BindingResult bindingResult, HttpSession candidateSession) {
        double timeRemaining = Double.parseDouble(getTimeRemaining(candidateSession));
        if (timeRemaining < GRACE_PERIOD) {
            LOGGER.log(Level.INFO, "User attempted to submit question but time has expired.");
            return "timeExpired";
        }

        if (bindingResult.hasErrors()) {
            return bindingResult.getFieldErrors();
        }

        attemptService.validateStatus(formBody.getAttemptId(), AttemptStatus.STARTED);
        attemptService.submitAnswer(formBody, SessionUtility.getUserDisplayName(candidateSession));

        LOGGER.log(Level.FINE, String.format("Candidate %s just submitted question with id %s and version %s. The answer provided was %s.",
                SessionUtility.getUserDisplayName(candidateSession),
                formBody.getQuestionId(),
                formBody.getQuestionVersionNo(),
                formBody.getText()));

        return "ok";
    }

    /**
     * When Marker posts a mark if he has locked the attempt to himself submit it to the database, otherwise throw.
     *
     * @param formBody Injected form object.
     * @param bindingResult Injected result of the form object validation.
     * @param markerSession
     * @return
     */
    @RequestMapping(value= ControllerEndpoints.ATTEMPT_MARK_SUBMIT, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Marker')")
    public @ResponseBody Object submitMark(@Valid SubmitMarkFormModel formBody,BindingResult bindingResult, HttpSession markerSession) {
        if (!bindingResult.hasErrors()) {
            if (markingService.userIsMarking(formBody.getTestAttemptId(), SessionUtility.getUserId(markerSession))) {
                attemptService.validateStatus(formBody.getTestAttemptId(), AttemptStatus.MARKING_ONGOING);
                markingService.submitMark(formBody, markerSession);
                return "ok";
            } else {
                throw new InvalidIsolationLevelException("This test attempt is currently blocked by another Marker");
            }
        } else {
            return bindingResult.getFieldErrors();
        }
    }

    /**
     * Endpoint to indicate that marking for this attempt is finished. Its hit when Marker presses Submit & Complete.
     * @param testAttemptId
     * @param markerSession
     * @return
     */
    @RequestMapping(value= ControllerEndpoints.ATTEMPT_FINISH_MARKING, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Marker')")
    @ResponseBody
    public void finishMarking(@PathVariable int testAttemptId, HttpSession markerSession) {
        attemptService.validateStatus(testAttemptId, AttemptStatus.MARKING_ONGOING);
        markingService.validateUserIsMarking(testAttemptId, SessionUtility.getUserId(markerSession));
        markingService.finishMarkingTestAttempt(testAttemptId);
    }

    /**
     * Endpoint invoked when Marker presses Unlock and Leave.
     *
     * @param testAttemptId
     * @param markerSession
     */
    @RequestMapping(value = ControllerEndpoints.ATTEMPT_UNLOCK_MARKING, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Marker')")
    @ResponseBody
    public void unlockMarking(@PathVariable int testAttemptId,HttpSession markerSession) {
        attemptService.validateStatus(testAttemptId, AttemptStatus.MARKING_ONGOING);
        markingService.validateUserIsMarking(testAttemptId, SessionUtility.getUserId(markerSession));
        markingService.unlockMarking(testAttemptId);
    }

    /**
     * If a Marker locks an attempt to himself, an admin is allowed to force unlock it, in case someone ever forgets to unlock
     * the test attempt and its causing issues.
     *
     * @param testAttemptId
     *
     */
    @RequestMapping(value = ControllerEndpoints.ATTEMPT_FORCE_UNLOCK_MARKING, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Admin')")
    @ResponseBody
    public void forceUnlock(@PathVariable int testAttemptId) {
        attemptService.validateStatus(testAttemptId, AttemptStatus.MARKING_ONGOING);
        markingService.unlockMarking(testAttemptId);
    }

    /**
     * Called by in candidatePaperAnswerable.js to get the time remaining on page load.
     *
     * @param candidateSession
     * @return
     */
    @RequestMapping(value = ControllerEndpoints.ATTEMPT_TIME_REMAINING, method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('Candidate')")
    @ResponseBody
    private String getTimeRemaining(HttpSession candidateSession) {
        final long attributeValue;
        try {
            attributeValue = SessionUtility.getTimeRemaining(candidateSession);
            double timePassed = System.currentTimeMillis() - attributeValue;
            double timeAllowed = (int) candidateSession.getAttribute(SESSION_TIME_ALLOWED);
            double timeRemaining = timeAllowed - ((timePassed / 1000) / 60);
            return String.valueOf(timeRemaining);
        } catch (SessionAttributeMissingException e) {
            final String errorMessage = String.format("User with this session %s cannot retrieve time allowed for a test attempt", candidateSession);
            LOGGER.log(Level.SEVERE,errorMessage,e);
            throw new InvalidUserStateException("This user cannot get time remaining");
        }
    }

    /**
     * IMPORTANT FormBody parameter the PaperReferenceModel will always hold just the id.
     * It always references the latest version. This means you can't submit a test attempt referencing
     * an old version of a test paper.
     *
     * This method is called when Admin presses Generate Test. If all fields pass validation,
     * strip roles from the session and redirect the user to the test attempt start page,
     * otherwise return to Dashboard generate test and display form validation.
     *
     * @param formBody
     * @return
     */
    @RequestMapping(value = ControllerEndpoints.ATTEMPT_SETUP_COMPLETE, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Admin')")
    public String confirmSetup(@Valid SetupExamFormModel formBody, BindingResult bindingResult, HttpSession adminSession, RedirectAttributes redir) {
        if (bindingResult.hasErrors()) {
            redir.addFlashAttribute("errors", bindingResult.getFieldErrors());
            redir.addFlashAttribute("target", bindingResult.getTarget());
            return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.DASHBOARD_PREFIX + ControllerEndpoints.DASHBOARD_GENERATE_TESTS;
        }

        AttemptReferenceModel model = adminService.createTestAttemptModelFromSetupInformation(formBody, attemptService.getTerms());
        adminSession.setAttribute(SESSION_BEGUN_TEST_ATTEMPT, false);
        adminService.logOutAdmin(adminSession, formBody.getCandidate());
        return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.ATTEMPT_PREFIX + ControllerEndpoints.ATTEMPT_START.replace("{testAttemptId}", Integer.toString(model.getId()));
    }
}
