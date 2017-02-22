package uk.ac.ncl.daniel.baranowski.common;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used to store all the endpoints used in our application.
 * It will be also accessible in java script because application will write output of
 * toJson method to js file as a variable named ENDPOINTS.
 * Users can access these values inside java script classes using ENDPOINTS.{PARAMETER_NAME}
 * for example ENDPOINTS.REDIRECT_REFIX.
 */
public class ControllerEndpoints {
    public static final String REDIRECT_PREFIX = "redirect:";

    public static final String ATTEMPT_PREFIX = "/test-attempt";
    public static final String EXAM_SETUP_COMPLETE = "/setup/confirm";
    public static final String ATTEMPT_LOGIN = "{examId}/login";
    public static final String ATTEMPT_CREATE_SESSION = "/{examId}/create-session";
    public static final String ATTEMPT_START = "/{testAttemptId}/start";
    public static final String ATTEMPT_ONGOING = "/ongoing";
    public static final String ATTEMPT_MARK = "/{testAttemptId}/mark";
    public static final String ATTEMPT_COMPLETE = "/completed";
    public static final String ATTEMPT_FINISH_PAGE = "/finish-page";
    public static final String ATTEMPT_BEGIN = "begin";
    public static final String ATTEMPT_QUESTION_SUBMIT = "/submit-question";
    public static final String ATTEMPT_MARK_SUBMIT = "/submit-mark";
    public static final String ATTEMPT_TIME_REMAINING = "/get-time-remaining";
    public static final String ATTEMPT_FINISH_MARKING = "/{testAttemptId}/mark/finish";
    public static final String ATTEMPT_UNLOCK_MARKING = "/{testAttemptId}/mark/unlock";
    public static final String ATTEMPT_FORCE_UNLOCK_MARKING = "/{testAttemptId}/mark/force-unlock";

    public static final String EXAM_PREFIX = "/exam";

    public static final String LOGIN_LOGIN = "/login";
    public static final String LOGIN_CREATE_SESSION = "/create-session";
    public static final String LOGIN_LOGOUT ="/logout";

    public static final String DASHBOARD_PREFIX = "/dashboard";
    public static final String DASHBOARD_GENERATE_TESTS = "/generate-test";
    public static final String DASHBOARD_VIEW_TESTS = "/view-tests";
    public static final String DASHBOARD_CURRENT_TESTS = "/current-tests";
    public static final String DASHBOARD_MARKED_TESTS = "/marked-tests";
    public static final String DASHBOARD_UNMARKED_TESTS = "/unmarked-tests";
    public static final String DASHBOARD_MARKING_ONGOING = "/marking-ongoing";
    public static final String DASHBOARD_SETTINGS = "/settings";

    public static final String PAPER_PREFIX = "/test-paper";
    public static final String PAPER_VIEW = "/{paperId}/{paperVersionNo}/view";
    public static final String PAPER_TEST_LIBRARY = "/test-library";

    private static final Logger LOGGER = Logger.getLogger(ControllerEndpoints.class.getName());
    public static final String EXAM_REVIEW = "/review-exam/{examId}";

    /**
     * Hides implicit public constructor.
     */
    private ControllerEndpoints() {
        //NOSONAR
    }

    /**
     * @return A json object containing all the above fields.
     */
    public static String toJson() {
        String complete = "";
        Field[] field = ControllerEndpoints.class.getDeclaredFields();

        for (Field f : field) {
            f.setAccessible(true);
            try {
                String jsonLine = String.format("\"%s\":\"%s\",", f.getName(), f.get(null));
                complete += jsonLine;
            } catch (IllegalAccessException e) {
                LOGGER.log(Level.WARNING, "Couldn't parse endpoints to json. This may cause problems in client side java script.",e);
            }
        }

        return "{" + complete.substring(0, complete.length() - 1) + "}";
    }
}