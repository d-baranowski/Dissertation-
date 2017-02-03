

package uk.ac.ncl.daniel.baranowski.controllers;

import uk.ac.ncl.daniel.baranowski.common.Constants;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;
import uk.ac.ncl.daniel.baranowski.service.LoginService;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Login Controller provides endpoints
 */
@Controller
public class LoginController {

    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private LoginService loginService;

    /**
     * Please do not use this constructor. Spring automatically initializes all classes annotated with @Controller.
     * Controller constructor has to be public.
     * @param loginService Login Service will be injected automatically due to use of Autowired annotation.
     */
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }


    /**
     * This endpoint allows anonymous users to log in to the application. If user is logged in Access Denied Error shall be thrown.
     * Access Denied Errors are handled by com.bjss.tinkerbell.configuration.CustomAccessDeniedHandler
     * @return Login Page
     */
    @RequestMapping(ControllerEndpoints.LOGIN_LOGIN)
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView(Constants.TEMPLATE_LOGIN);
        mav.addObject("ENDPOINT", ControllerEndpoints.LOGIN_CREATE_SESSION);
        return mav;
    }

    /**
     * This endpoint will be called when user presses login on the login page. If the login is
     * successful they will be redirected to appropriate page, based on their role. Otherwise the
     * user will be redirected back to the login page and error message shall be displayed explaining what
     * went wrong.
     *
     * NOTE ! - This should be replaced with form validation instead.
     * Please look at example in submitQuestion method of AttemptController class.
     *
     * @param redirectAttributes Used to provide error messages if redirected back to login.
     * @return Redirect to different page depending if login was successful.
     */
    @RequestMapping(ControllerEndpoints.LOGIN_CREATE_SESSION)
    @PreAuthorize("isAnonymous()")
    public String createSession(@RequestParam(value = "usernameField", required = true, defaultValue = "") String username,
                                @RequestParam(value = "passwordField", required = true, defaultValue = "") String password,
                                HttpSession session, RedirectAttributes redirectAttributes) {

        if (username.length() > Constants.MAX_USERNAME_LENGTH) {
            username = username.substring(0, Constants.MAX_USERNAME_LENGTH);
        }
        String[] response = loginService.loginUser(username, password, session);

        if ("success".equals(response[0])) {
            session.setMaxInactiveInterval(60 * 60 * 2);
            String mainAuthority = loginService.getMainAuthority(session);

           switch (mainAuthority) {
               case "Admin": {
                   return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.DASHBOARD_PREFIX + ControllerEndpoints.DASHBOARD_GENERATE_TESTS;
               }
               case "Marker": {
                   return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.DASHBOARD_PREFIX + ControllerEndpoints.DASHBOARD_VIEW_TESTS;
               }
               default: { //NOSONAR I want this to be explicit
                   return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.DASHBOARD_PREFIX + ControllerEndpoints.DASHBOARD_GENERATE_TESTS;
               }
            }
        }

        session.invalidate();
        redirectAttributes.addFlashAttribute(Constants.FLASH_ERROR, response[1]);
        return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.LOGIN_LOGIN;
    }

    /**
     * Endpoint that allows users to log out.
     * @param session Will be invalidated on log out.
     * @return Redirect to login page.
     */
    @RequestMapping(ControllerEndpoints.LOGIN_LOGOUT)
    @PreAuthorize("isAuthenticated()")
    public String logout(HttpSession session) {
        String username = session.getAttribute(Constants.SESSION_USERNAME).toString();
        session.invalidate();
        LOGGER.log(Level.INFO, "User " + username + " logged out.");
        return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.LOGIN_LOGIN;
    }
}

