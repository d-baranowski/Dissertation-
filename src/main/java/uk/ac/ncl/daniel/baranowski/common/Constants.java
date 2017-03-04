package uk.ac.ncl.daniel.baranowski.common;

/**
 * Holds the constants used by the application.
 **/
public class Constants {

    // Templates
    public static final String TEMPLATE_LOGIN = "login";
    public static final String TEMPLATE_DASHBOARD = "dashboard";
    public static final String TEMPLATE_GENERIC_ERROR_PAGE = "/errorPages/GenericErrorPage";

    // Bootstrap Flash Error Message Types
    public static final String FLASH_ERROR = "error";
    public static final String FLASH_SUCCESS = "success";

    // Error Messages
    public static final String ERROR_LOGIN_INVALID_CHARS = "Your username or password contained invalid characters";
    public static final String ERROR_EMPTY_FIELDS = "Username or password field empty.";

    public static final int MAX_USERNAME_LENGTH = 30;

    // Session Attribute Constants
    public static final String SESSION_USERNAME = "username";
    public static final String SESSION_ROLES = "roles";
    public static final String SESSION_START_TIME = "startTime";
    public static final String SESSION_LOGGED_IN = "isLoggedIn";
    static final String SESSION_SURNAME = "CandidateSurname";
    public static final String SESSION_ATTEMPT_ID = "attempt";
    public static final String SESSION_TIME_ALLOWED = "timeAllowed";
    public static final String SESSION_BEGUN_TEST_ATTEMPT = "candidateBegunTestAttempt";

    // Role Names
    public static final String ROLE_CANDIDATE = "Candidate";
    public static final String ROLE_MARKER = "Marker";
    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_AUTHOR = "Author";
    public static final String[] ROLES = { ROLE_ADMIN, ROLE_MARKER, ROLE_AUTHOR, ROLE_CANDIDATE };

    public static final String TIME_PATTERN = "HH:mm";

    private Constants() {
        // Hides explicit public constructor.
    }
}
