package uk.ac.ncl.daniel.baranowski.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import uk.ac.ncl.daniel.baranowski.common.RoleRouter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is automatically picked up by Spring due to @Component.
 */
@Component
class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger LOGGER = Logger.getLogger(CustomAccessDeniedHandler.class.getName());

    private CustomAccessDeniedHandler() {
        //Hides pubic constructor to enforce use of dependency injection
    }

    /**
     * This method will try and redirect users to appropriate page based on their roles on GET requests.
     * Otherwise it will return 403.
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        if (request.getMethod().equals("GET")) {
            final String redirect = RoleRouter.resolveRedirectBasedOnRole(request.getSession());
            LOGGER.log(Level.FINE, "Http 403 exception handled with redirect to: " + redirect, accessDeniedException);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.sendRedirect(redirect);
        } else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
    }
}
