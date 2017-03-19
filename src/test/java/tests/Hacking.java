package tests;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class Hacking {

    @Test
    public void caseInsensitiveJavaRegex() {
        String pattern = "(?i)(GoOGle)";
        String matchOn = "GoOGle";

        assertTrue(matchOn.matches(pattern));
    }

    @Test
    public void customRegexInJavaRegex() {
        String pattern = "";
        String matchOn = "danielek4567@gmail.com";

        assertTrue(matchOn.matches(pattern));
    }
}
