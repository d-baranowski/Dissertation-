package uk.ac.ncl.daniel.baranowski.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints;

/**
 * IndexController
 */
@Controller
public class IndexController {

    /**
     * This controller will redirect the user to the home page when they hit the base url or throw
     * authorization error.
     * @return A redirect to dashboard page
     */
    @RequestMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String index() {
        return ControllerEndpoints.REDIRECT_PREFIX + ControllerEndpoints.DASHBOARD_PREFIX + ControllerEndpoints.DASHBOARD_GENERATE_TESTS;
    }
}
