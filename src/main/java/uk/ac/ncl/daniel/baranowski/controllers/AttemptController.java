package uk.ac.ncl.daniel.baranowski.controllers;

import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uk.ac.ncl.daniel.baranowski.common.Constants;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.common.SessionUtility;
import uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus;
import uk.ac.ncl.daniel.baranowski.exceptions.*;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitAnswerFormModel;
import uk.ac.ncl.daniel.baranowski.models.testattempt.SubmitMarkFormModel;
import uk.ac.ncl.daniel.baranowski.models.websocket.MarkMessage;
import uk.ac.ncl.daniel.baranowski.service.AttemptService;
import uk.ac.ncl.daniel.baranowski.service.ExamService;
import uk.ac.ncl.daniel.baranowski.service.MarkingService;
import uk.ac.ncl.daniel.baranowski.service.PaperService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_TIME_ALLOWED;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.*;
import static uk.ac.ncl.daniel.baranowski.common.enums.ExamStatus.STARTED;

/**
 * This controller provides endpoints linked to the Test Attempt and actions that user can perform on it,
 * Like Taking the Test, and Marking the test.
 */
@Controller
@RequestMapping(ATTEMPT_PREFIX)
public class AttemptController {
    private final AttemptService attemptService;
    private final MarkingService markingService;
    private final PaperService paperService;
    private final ExamService examService;
    private static final Logger LOGGER = Logger.getLogger(AttemptController.class.getName());
    private static final double GRACE_PERIOD = -0.3;
    private final SimpMessagingTemplate simpMessager;

    /**
     * Please do not use this constructor. Spring automatically initializes all classes annotated with @Controller.
     * Controller constructor has to be public.
     * Parameters will be automatically injected due to use of @Autowired.
     * @param attemptService NOSONAR
     * @param markingService NOSONAR
     */
    @Autowired
    public AttemptController(AttemptService attemptService, MarkingService markingService, PaperService paperService, ExamService examService, SimpMessagingTemplate simpMessager) {
        this.attemptService = attemptService;
        this.markingService = markingService;
        this.paperService = paperService;
        this.examService = examService;
        this.simpMessager = simpMessager;
    }

    /**
     * Since candidate doesn't have any roles assigned to them so this endpoint only checks if the user is
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
            attemptService.validateStatus(testAttemptId, ExamStatus.CREATED);
            AttemptReferenceModel model = attemptService.getAttemptReferenceModel(testAttemptId);
            final String instructionsText = paperService.getInstructionsText(model.getPaperRef().getId(), model.getPaperRef().getVersionNo());
            result = new ModelAndView("testAttempt/start")
                    .addObject("model", model)
                    .addObject("timeAllowed",attemptService.getTimeRemaining(model.getId()))
                    .addObject("noNavigation", true)
                    .addObject("termsAndConditions", attemptService.getLatestTermsAndConditions())
                    .addObject("paperInstructions", instructionsText);
        } else {
            result = new ModelAndView(REDIRECT_PREFIX + ATTEMPT_PREFIX + ATTEMPT_ONGOING);
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
    @RequestMapping(ATTEMPT_ONGOING)
    @PreAuthorize("hasAnyAuthority('Candidate')")
    public ModelAndView ongoingAttempt(HttpSession candidateSession) {
        final int attemptId = (int) candidateSession.getAttribute("attempt");
        try {
            attemptService.validateStatus(attemptId, STARTED);
        } catch (InvalidAttemptStatusException e) {
            if (e.getActual().equals(ExamStatus.FINISHED)) {
                final String errorMsg = String.format(
                        "Candidate %s tried to go back to ongoing attempt page after finishing the attempt. Redirected him back to finish page",
                        SessionUtility.getUserDisplayName(candidateSession));
                LOGGER.log(Level.INFO,errorMsg, e);
                return new ModelAndView(REDIRECT_PREFIX + ATTEMPT_PREFIX + ControllerEndpoints.ATTEMPT_FINISH_PAGE);
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
    public ModelAndView mark(@PathVariable int testAttemptId,
                             @RequestParam(required = false, defaultValue = "-1") int sectionNo,
                             @RequestParam(required = false, defaultValue = "-1") int questionNo,
                             HttpSession markerSession) {
        markingService.startMarking(testAttemptId, markerSession);
        return markingService.getMarkableViewForTestAttempt(testAttemptId);
    }

    @RequestMapping(ATTEMPT_LOGIN)
    public ModelAndView loginToAttempt(@PathVariable int examId) {
        ModelAndView mav = new ModelAndView(Constants.TEMPLATE_LOGIN);
        mav.addObject("ENDPOINT", ATTEMPT_PREFIX + ATTEMPT_CREATE_SESSION.replaceFirst("\\{examId}", examId + ""));
        return mav;
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
    @RequestMapping(value = ATTEMPT_COMPLETE, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Candidate')")
    public String testCompleted(HttpSession session) {
        attemptService.validateStatus(SessionUtility.getCandidatesTestAttemptId(session), STARTED);
        attemptService.finishAttempt(session);
        return REDIRECT_PREFIX + ATTEMPT_PREFIX + ATTEMPT_FINISH_PAGE;
    }

    @RequestMapping(value = ControllerEndpoints.ATTEMPT_CREATE_SESSION, method = RequestMethod.POST)
    @PreAuthorize("isAnonymous()")
    public String createSession(@RequestParam(value = "usernameField", required = true, defaultValue = "") String username,
                                @RequestParam(value = "passwordField", required = true, defaultValue = "") String password,
                                @PathVariable int examId,
                                HttpSession session, RedirectAttributes redirectAttributes) {

        int attemptId = attemptService.canLoginToAttempt(examId,username,password);

        if (attemptId == 0) {
            session.invalidate();
            redirectAttributes.addFlashAttribute(Constants.FLASH_ERROR, "Wrong Credentials, Try Again");
            return REDIRECT_PREFIX + ATTEMPT_PREFIX + ControllerEndpoints.ATTEMPT_LOGIN.replaceFirst("\\{examId}", examId + "");
        } else {
            examService.validateStatus(attemptService.getExamId(attemptId), STARTED);
            List<SimpleGrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority(Constants.ROLE_CANDIDATE));
            SessionUtility.setCurrentUserRoles(session, roles);
            SessionUtility.assignTestAttemptIdToCandidate(session, attemptId);
            return REDIRECT_PREFIX + ATTEMPT_PREFIX + ATTEMPT_START.replaceFirst("\\{testAttemptId}",attemptId+"");
        }
    }


    /**
     * IMPORTANT Model parameter will only hold id.
     */
    @RequestMapping(value = ATTEMPT_BEGIN, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Candidate')")
    public String beginAttempt(AttemptReferenceModel model, HttpSession candidateSession) {
        attemptService.setupSessionToBeginAttempt(candidateSession,model.getId());
        return REDIRECT_PREFIX + ATTEMPT_PREFIX + ATTEMPT_ONGOING;
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
    @RequestMapping(value = ATTEMPT_QUESTION_SUBMIT, method = RequestMethod.POST)
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

        attemptService.validateStatus(formBody.getAttemptId(), STARTED);
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
    @RequestMapping(value= ATTEMPT_MARK_SUBMIT, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Marker')")
    public @ResponseBody Object submitMark(@Valid SubmitMarkFormModel formBody,BindingResult bindingResult, HttpSession markerSession) {
        if (!bindingResult.hasErrors()) {
            if (markingService.userIsMarking(formBody.getTestAttemptId(), SessionUtility.getUserId(markerSession))) {
                attemptService.validateStatus(formBody.getTestAttemptId(), ExamStatus.MARKING_ONGOING);
                int markId = markingService.submitMark(formBody, markerSession);
                sendMarkMessage(markId, formBody);
                return "ok";
            } else {
                throw new InvalidIsolationLevelException("This test attempt is currently blocked by another Marker");
            }
        } else {
            return bindingResult.getFieldErrors();
        }
    }

    private void sendMarkMessage(int markId, SubmitMarkFormModel formBody) {
        simpMessager.convertAndSend("/marking/mark-updated",  new MarkMessage()
                .setMark(markingService.get(markId))
                .setQuestionId(formBody.getQuestionId())
                .setQuestionVersionNo(formBody.getQuestionVersionNo())
                .setTestAttemptId(formBody.getTestAttemptId()));
    }

    /**
     * Endpoint to indicate that marking for this attempt is finished. Its hit when Marker presses Submit & Complete.
     * @param testAttemptId
     * @param markerSession
     * @return
     */
    @RequestMapping(value= ATTEMPT_FINISH_MARKING, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Marker')")
    @ResponseBody
    public void finishMarking(@PathVariable int testAttemptId, HttpSession markerSession) {
        attemptService.validateStatus(testAttemptId, ExamStatus.MARKING_ONGOING);
        markingService.validateUserIsMarking(testAttemptId, SessionUtility.getUserId(markerSession));
        markingService.finishMarkingTestAttempt(testAttemptId);
    }

    /**
     * Endpoint invoked when Marker presses Unlock and Leave.
     *
     * @param testAttemptId
     * @param markerSession
     */
    @RequestMapping(value = ATTEMPT_UNLOCK_MARKING, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Marker')")
    @ResponseBody
    public void unlockMarking(@PathVariable int testAttemptId,HttpSession markerSession) {
        attemptService.validateStatus(testAttemptId, ExamStatus.MARKING_ONGOING);
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
    @RequestMapping(value = ATTEMPT_FORCE_UNLOCK_MARKING, method = RequestMethod.POST)
    @PreAuthorize("hasAnyAuthority('Admin')")
    @ResponseBody
    public void forceUnlock(@PathVariable int testAttemptId) {
        attemptService.validateStatus(testAttemptId, ExamStatus.MARKING_ONGOING);
        markingService.unlockMarking(testAttemptId);
    }

    /**
     * Called by in candidatePaperAnswerable.js to get the time remaining on page load.
     *
     * @param candidateSession
     * @return
     */
    @RequestMapping(value = ATTEMPT_TIME_REMAINING, method = RequestMethod.GET)
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
}
