package tests;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    @Test
    public void provisionRunningWithSelenium() throws Exception {
        BareSeleniumActions seleniumActions = new BareSeleniumActions("https://localhost", jdbc);
        seleniumActions.loginAs("sampleAll","pass", "Jack Brown");
        seleniumActions.A5canCreateQuestions();
        seleniumActions.A6canEditQuestions();
        seleniumActions.A7canCreateSection();
        seleniumActions.A8canCreatePaper();
        seleniumActions.A8canSetupAnExam();
        seleniumActions.A9canStartAnExam();
        seleniumActions.B1canTakeExam();
        seleniumActions.B2CanFinnishExam();
        seleniumActions.B3CanMarkExam();
        seleniumActions.B4MultiChoiceWizardRegenerates();
    }

}
