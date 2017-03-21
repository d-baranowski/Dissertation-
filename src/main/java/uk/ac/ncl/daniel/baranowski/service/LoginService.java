package uk.ac.ncl.daniel.baranowski.service;
/**
 * Service to handle user logins.
 */

import uk.ac.ncl.daniel.baranowski.common.enums.Roles;
import uk.ac.ncl.daniel.baranowski.data.UserRepo;
import uk.ac.ncl.daniel.baranowski.common.Constants;
import uk.ac.ncl.daniel.baranowski.common.SessionUtility;
import uk.ac.ncl.daniel.baranowski.models.UserModel;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import static uk.ac.ncl.daniel.baranowski.common.Constants.SESSION_LOGGED_IN;

@Service
public class LoginService {
    /**
     * Class to encapsulate login methods.
     **/
    private final UserRepo userRepo;
    private static final Logger LOGGER = Logger.getLogger(LoginService.class.getName());

    @Autowired
    public LoginService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public String[] loginUser(String username, String password, HttpSession session) {
        /**
         * Grabs the user repo, gets the user specified and validates the password.
         * @returns String[] with index 0 as success or false and index 1 as error messages
         *                   if any.
         * **/
        UserModel user = userRepo.getUserByLogin(username);
        String[] response = {"false", "Your Username / Password combination was incorrect."};

        String error = validateFields(username, password);
        if (!"".equals(error)) {
            response[1] = error;
            return response;
        }

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        if (user != null) {
            if (user.getPassword().equals(password)) {
                List<Roles> userRoles = user.getRoles();
                for (int i = 0; i < userRoles.size(); i++) {
                    roles.add(new SimpleGrantedAuthority(userRoles.get(i).toString()));
                }
                SessionUtility.setCurrentUserRoles(session, roles);
                SessionUtility.saveUserId(session, user.getId());
                SessionUtility.setUserDisplayName(session, user.getName(),user.getSurname());

                LOGGER.log(Level.INFO, "User " + username + " logged in.");
                response[0] = "success";
                response[1] = "";
            } else {
                LOGGER.log(Level.INFO, "User " + username + " attempt to login failed.");
            }
        }

        session.setAttribute(SESSION_LOGGED_IN, true);
        return response;
    }

    public String getMainAuthority(HttpSession session) {
        List<String> grantedAuthorities = SessionUtility.getCurrentUserRoles(session).stream()
                .map(userRole -> userRole.getAuthority() )
                .collect(Collectors.toList());

        for (String role : Constants.ROLES){
            if (grantedAuthorities.contains(role)) {
                return role;
            }
        }
        return "";
    }


    private String validateFields(String usernameField, String passwordField) {
        String invalidCharacters = "#£%$&{}/*<>$'\":|=`\\!+ ";

        if ("".equals(usernameField) || "".equals(passwordField)) {
            return Constants.ERROR_EMPTY_FIELDS;
        }
        for (int i = 0; i < invalidCharacters.length(); i++) {
            String currentChar = String.valueOf(invalidCharacters.charAt(i));
            if (usernameField.contains(currentChar) || passwordField.contains(currentChar)) {
                return Constants.ERROR_LOGIN_INVALID_CHARS;
            }
        }
        return "";
    }
}
