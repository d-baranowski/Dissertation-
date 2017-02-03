package tests;

import uk.ac.ncl.daniel.baranowski.DissertationApplication;
import uk.ac.ncl.daniel.baranowski.common.Constants;
import uk.ac.ncl.daniel.baranowski.service.LoginService;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@WebAppConfiguration
public class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @Test
    public void happyPathLoginResponse() {
        MockHttpSession session = new MockHttpSession();
        String[] response = loginService.loginUser("sampleMarker", "pass", session);
        String[] expectedResponse = {"success", ""};

        assertTrue(Arrays.equals(expectedResponse, response));

        assertTrue(session.getAttribute(Constants.SESSION_ROLES).equals(Collections.singletonList(new SimpleGrantedAuthority("Marker"))));
    }

    @Test
    public void invalidUsernameLoginResponse() {
        MockHttpSession session = new MockHttpSession();
        String[] response = loginService.loginUser("thequickbrownfox", "pass", session);
        String[] expectedResponse = {"false", "Your Username / Password combination was incorrect."};

        assertTrue(Arrays.equals(expectedResponse, response));

        assertTrue(session.getAttribute(Constants.SESSION_USERNAME) == null);
        assertTrue(session.getAttribute(Constants.SESSION_ROLES) == null);
    }

    @Test
    public void invalidPasswordLoginResponse() {
        MockHttpSession session = new MockHttpSession();
        String[] response = loginService.loginUser("sampleMarker", "incorrect", session);
        String[] expectedResponse = {"false", "Your Username / Password combination was incorrect."};

        assertTrue(Arrays.equals(expectedResponse, response));

        assertTrue(session.getAttribute(Constants.SESSION_USERNAME) == null);
        assertTrue(session.getAttribute(Constants.SESSION_ROLES) == null);
    }

    @Test
    public void invalidUsernameCharactersResponse() {
        MockHttpSession session = new MockHttpSession();
        String[] response = loginService.loginUser("sampleMarker<\\/*<>", "incorrect", session);
        String[] expectedResponse = {"false", Constants.ERROR_LOGIN_INVALID_CHARS};

        assertTrue(Arrays.equals(expectedResponse, response));

        assertTrue(session.getAttribute(Constants.SESSION_USERNAME) == null);
        assertTrue(session.getAttribute(Constants.SESSION_ROLES) == null);
    }

    @Test
    public void invalidPasswordCharactersResponse() {
        MockHttpSession session = new MockHttpSession();
        String[] response = loginService.loginUser("sampleMarker", "incorrect\\*<<%", session);
        String[] expectedResponse = {"false", Constants.ERROR_LOGIN_INVALID_CHARS};

        assertTrue(Arrays.equals(expectedResponse, response));

        assertTrue(session.getAttribute(Constants.SESSION_USERNAME) == null);
        assertTrue(session.getAttribute(Constants.SESSION_ROLES) == null);
    }

    @Test
    public void emptyUsernameResponse() {
        MockHttpSession session = new MockHttpSession();
        String[] response = loginService.loginUser("", "incorrect", session);
        String[] expectedResponse = {"false", Constants.ERROR_EMPTY_FIELDS};

        assertTrue(Arrays.equals(expectedResponse, response));

        assertTrue(session.getAttribute(Constants.SESSION_USERNAME) == null);
        assertTrue(session.getAttribute(Constants.SESSION_ROLES) == null);
    }

    @Test
    public void emptyPasswordResponse() {
        MockHttpSession session = new MockHttpSession();
        String[] response = loginService.loginUser("clonafa", "", session);
        String[] expectedResponse = {"false", Constants.ERROR_EMPTY_FIELDS};

        assertTrue(Arrays.equals(expectedResponse, response));

        assertTrue(session.getAttribute(Constants.SESSION_USERNAME) == null);
        assertTrue(session.getAttribute(Constants.SESSION_ROLES) == null);
    }

}
