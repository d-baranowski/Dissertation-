package uk.ac.ncl.daniel.baranowski.common;

import uk.ac.ncl.daniel.baranowski.exceptions.SessionAttributeMissingException;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_ATTEMPT_ID;
import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_BEGUN_TEST_ATTEMPT;
import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_ROLES;
import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_START_TIME;
import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_SURNAME;
import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_USERNAME;

/**
 * A collection of helpers to handle user sessions.
 **/
public class SessionUtility {
    private SessionUtility() {
        //NOSONAR Intentionally empty constructor, to prevent from unnecessary instantiation of static class;
    }

    /**
     * Store the user id in the session for ease of access in controllers.
     * @param userSession The user session.
     * @param userId Id we wan't to store.
     */
    public static void saveUserId(HttpSession userSession, String userId) {
        userSession.setAttribute("userId", userId);
    }


    /**
     * @param userSession The user session.
     * @return Grab the id back from the session.
     */
    public static String getUserId(HttpSession userSession) {
        return (String) userSession.getAttribute("userId");
    }

    /**
     * @param session The user session.
     * @return Name and surname divided with space.
     */
    public static String getUserDisplayName(HttpSession session) {
        return session.getAttribute(SESSION_USERNAME) + " " + session.getAttribute(SESSION_SURNAME);
    }

    public static void setUserDisplayName(HttpSession session, String firstName, String surname) {
        session.setAttribute(SESSION_USERNAME, firstName);
        session.setAttribute(SESSION_SURNAME, surname);
    }


    public static void assignTestAttemptIdToCandidate(HttpSession candidateSession, int id) {
        candidateSession.setAttribute("attempt", id);
    }


    public static int getCandidatesTestAttemptId(HttpSession candidateSession) {
        return (int) candidateSession.getAttribute(SESSION_ATTEMPT_ID);
    }

    public static boolean hasBegunTestAttempt(HttpSession candidateSession) {
        return candidateSession.getAttribute(SESSION_BEGUN_TEST_ATTEMPT) != null && (boolean) candidateSession.getAttribute(SESSION_BEGUN_TEST_ATTEMPT);
    }

    public static long getTimeRemaining(HttpSession candidateSession) throws SessionAttributeMissingException {
        final Object attributeResult =  candidateSession.getAttribute(SESSION_START_TIME);
        if (attributeResult != null) {
            return (long) attributeResult;
        } else {
            throw new SessionAttributeMissingException();
        }
    }

    /**
     * Replaces the currently logged in users roles with the roles specified and sets these on the
     * session.
     * **/
    public static void setCurrentUserRoles(HttpSession session, List<SimpleGrantedAuthority> roles) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                session.getAttribute(Constants.SESSION_USERNAME), "", roles);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        session.setAttribute(SESSION_ROLES, roles);
    }

    public static List<SimpleGrantedAuthority> getCurrentUserRoles(HttpSession session) {
        final Object result = session.getAttribute(SESSION_ROLES);
        return result != null ?  (List<SimpleGrantedAuthority>) result : new ArrayList<>();
    }
}
