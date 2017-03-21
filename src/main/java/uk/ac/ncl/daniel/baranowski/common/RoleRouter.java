package uk.ac.ncl.daniel.baranowski.common;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpSession;
import java.util.List;

import static uk.ac.ncl.daniel.baranowski.common.Constants.ROLE_CANDIDATE;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.*;

/**
 * This class is a tool that will be used by the CustomAccessDeniedHandler,
 * to decide where to redirect the user based on their role.
 */
public class RoleRouter {

    private RoleRouter() {
        //Hides implicit public constructor
    }

    public static String resolveRedirectBasedOnRole(HttpSession session) {
        final List<SimpleGrantedAuthority> roles = SessionUtility.getCurrentUserRoles(session);

        if (roles.size() == 1 && roles.get(0).getAuthority().equals(ROLE_CANDIDATE)) {
            return ATTEMPT_PREFIX + ATTEMPT_ONGOING;
        } else if (!roles.isEmpty()) {
            return DASHBOARD_PREFIX + DASHBOARD_GENERATE_TESTS;
        } else {
            session.invalidate();
            return LOGIN_LOGIN;
        }
    }
}
