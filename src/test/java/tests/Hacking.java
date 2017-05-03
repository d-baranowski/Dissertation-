package tests;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import selenium.ui.BareSeleniumActions;

import static org.junit.Assert.assertTrue;

public class Hacking {


    public void caseInsensitiveJavaRegex() {
        String pattern = "(?i)(GoOGle)";
        String matchOn = "GoOGle";

        assertTrue(matchOn.matches(pattern));
    }


    public void customRegexInJavaRegex() {
        String pattern = "";
        String matchOn = "danielek4567@gmail.com";

        assertTrue(matchOn.matches(pattern));
    }

    @Autowired
    JdbcTemplate jdbc;

    public void provisionRunningWithSelenium() throws Exception {
        BareSeleniumActions seleniumActions = new BareSeleniumActions("https://localhost", jdbc);
        seleniumActions.loginAs("sampleAll","pass", "Jack Brown");
        seleniumActions.A5canCreateQuestions();
        seleniumActions.A6canEditQuestions();
        seleniumActions.A7canCreateSection();
        seleniumActions.A8canCreatePaper();
    }

    @Test
    public void howLongIsTheSalt() {
        String salt = "Kopytko123adsadsadsadsadsdsdqsqwdqwdqwdqwdqwdqfrevwrtwgttwrwwgr__$234234__";

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(salt);

        System.out.print(hashedPassword.length());
    }
}
