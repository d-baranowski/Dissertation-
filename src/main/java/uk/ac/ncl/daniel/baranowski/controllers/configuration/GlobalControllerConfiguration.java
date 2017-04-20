package uk.ac.ncl.daniel.baranowski.controllers.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import uk.ac.ncl.daniel.baranowski.common.HelpConstants;
import uk.ac.ncl.daniel.baranowski.common.SessionUtility;
import uk.ac.ncl.daniel.baranowski.exceptions.*;
import uk.ac.ncl.daniel.baranowski.views.GenericErrorPageViewModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.ac.ncl.daniel.baranowski.common.Constants.*;

/**
 * This class is used to apply common settings across all of the controllers.
 */
@EnableWebMvc
@ControllerAdvice
public class GlobalControllerConfiguration {
    /**
     * Version code is imported from the properties file.
     */
    @Value("${versionCode}")
    private String versionString;

    /**
     * This method will apply common attributes to every model that will be resolved by the server.
     * Method is meant to be used by the framework using reflections.
     * @param model Model that will be returned as part of ModelAndView. Automatically injected.
     */
    @ModelAttribute
    public void addGlobalModelAttributes(Model model) {
        model.addAttribute("versionString", versionString);
        addModelAttributesBasedOnRoles(model);
        addModelAttributesBasedOnUser(model);
        model.addAttribute("helpJson", HelpConstants.getHelpFields());
    }

    /**
     * If an exception is threw in the controller it will be resolved differently based on the following methods.
     * If the exception type doesn't match any of these exception handlers the 500 status page will be displayed.
     * Different pictures and descriptions can be provided for different exceptions.
     * @param e Exception thrown
     * @return Error page with information about the exception being thrown.
     */
    @ExceptionHandler(value = AttemptMissingException.class)
    public ModelAndView handleAttemptMissingException(AttemptMissingException e) {
        return new GenericErrorPageViewModel(e.getMessage(), "Attempt Missing", "errorPuzzle.png").getModelAndView();
    }

    @ExceptionHandler(value = InvalidAttemptStatusException.class)
    public ModelAndView handleInvalidAttemptStatusException(InvalidAttemptStatusException e) { //NOSONAR as above
        return new GenericErrorPageViewModel(e.getMessage(), "Invalid Attempt Status", "errorPuzzle.png").getModelAndView();
    }

    @ExceptionHandler(value = InvalidUserStateException.class)
    public ModelAndView handleInvalidUserStateException(InvalidUserStateException e) { //NOSONAR as above
        return new GenericErrorPageViewModel(e.getMessage(), "Invalid User State", "errorPuzzle.png").getModelAndView();
    }

    @ExceptionHandler(value = TestPaperDoesNotExistException.class)
    public ModelAndView handleTestPaperDoesNotExist(TestPaperDoesNotExistException e) { //NOSONAR as above
        return new GenericErrorPageViewModel(e.getMessage(), "Test Paper Not Found", "errorPuzzle.png").getModelAndView();
    }

    @ExceptionHandler(value = NotLockedForMarkingException.class)
    public ModelAndView handleNotLockedForMarking(NotLockedForMarkingException e) { //NOSONAR as above
        return new GenericErrorPageViewModel(e.getMessage(), "Not Locked For Marking", "errorPuzzle.png").getModelAndView();
    }

    @ExceptionHandler(value = ExamMissingException.class)
    public ModelAndView handleExamMissing(ExamMissingException e) { //NOSONAR as above
        return new GenericErrorPageViewModel(e.getMessage(), "Unable To Retrieve Exam", "errorPuzzle.png").getModelAndView();
    }

    @ExceptionHandler(value = FailedToCreateExamException.class)
    public ModelAndView handleExamMissing(FailedToCreateExamException e) { //NOSONAR as above
        return new GenericErrorPageViewModel(e.getMessage(), "Unable To Create Exam", "errorPuzzle.png").getModelAndView();
    }

    @ExceptionHandler(value = FailedToCreateQuestionException.class)
    public ModelAndView handleExamMissing(FailedToCreateQuestionException e) { //NOSONAR as above
        return new GenericErrorPageViewModel(e.getMessage(), "Unable To Create Question", "errorPuzzle.png").getModelAndView();
    }

    /**
     * Converts SimpleGrantedAuthority to plain String.
     * @param session A list of SimpleGrantedAuthority will be retrieved from this session.
     * @return List of roles of the user specified by the session in form of the string.
     */
    private List<String> getSimpleRolesList(HttpSession session) {
        final List<String> result = new ArrayList<>();
        final List<SimpleGrantedAuthority> complexRoles = SessionUtility.getCurrentUserRoles(session);

        complexRoles.forEach(role -> {
            result.add(role.getAuthority());
        });

        return result;
    }

    private HttpSession getSessionByRequest() {
        final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession(true);//true will create if necessary
    }

    /**
     * Some UI elements need to be disabled or enabled based on user role. If disableNotX is true elements
     * reserved for role x will be disabled. This needs to be stated in your templates.
     * @param model Model to which attributes will be added.
     */
    private void addModelAttributesBasedOnRoles(Model model) {
        final List<String> roles = getSimpleRolesList(getSessionByRequest());
        model.addAttribute("disableNotMarker", !roles.contains(ROLE_MARKER));
        model.addAttribute("disableNotAdmin", !roles.contains(ROLE_ADMIN));
        model.addAttribute("disableNotAuthor", !roles.contains(ROLE_AUTHOR));
    }

    /**
     * UserId from the database and Full Name of the User.
     * @param model Model to which attributes will be added.
     */
    private void addModelAttributesBasedOnUser(Model model) {
        HttpSession session = getSessionByRequest();
        model.addAttribute("userId", SessionUtility.getUserId(session));
        model.addAttribute("userName", SessionUtility.getUserDisplayName(session));
    }
}
