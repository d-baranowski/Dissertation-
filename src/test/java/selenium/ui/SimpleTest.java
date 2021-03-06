package selenium.ui;


import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ncl.daniel.baranowski.DissertationApplication;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
import static uk.ac.ncl.daniel.baranowski.common.Constants.TIME_PATTERN;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.PAPER_PREFIX;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.PAPER_SECTION_EDITOR;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DissertationApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebIntegrationTest()
public class SimpleTest {
    private EventFiringWebDriver driver;
    private String baseUrl = "https://localhost:8900";
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriverWait wait;
    private JavascriptExecutor executor;
    private Actions action;

    @Autowired
    JdbcTemplate jdbc;

    @Value("${backupUITestStages}")
    private Boolean backupUITestStages;

    @Value("${loadUITestStages}")
    private Boolean loadUITestStages;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        driver = new EventFiringWebDriver(new ChromeDriver());
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 500);
        action = new Actions(driver);
        if (driver instanceof JavascriptExecutor) {
            executor = ((JavascriptExecutor) driver);
        }

        driver.register(new WebDriverEventListener() {
            @Override
            public void beforeAlertAccept(WebDriver driver) {

            }

            @Override
            public void afterAlertAccept(WebDriver driver) {

            }

            @Override
            public void afterAlertDismiss(WebDriver driver) {

            }

            @Override
            public void beforeAlertDismiss(WebDriver driver) {

            }

            @Override
            public void beforeNavigateTo(String url, WebDriver driver) {

            }

            @Override
            public void afterNavigateTo(String url, WebDriver driver) {

            }

            @Override
            public void beforeNavigateBack(WebDriver driver) {

            }

            @Override
            public void afterNavigateBack(WebDriver driver) {

            }

            @Override
            public void beforeNavigateForward(WebDriver driver) {

            }

            @Override
            public void afterNavigateForward(WebDriver driver) {

            }

            @Override
            public void beforeNavigateRefresh(WebDriver driver) {

            }

            @Override
            public void afterNavigateRefresh(WebDriver driver) {

            }

            @Override
            public void beforeFindBy(By by, WebElement element, WebDriver driver) {

            }

            @Override
            public void afterFindBy(By by, WebElement element, WebDriver driver) {

            }

            @Override
            public void beforeClickOn(WebElement element, WebDriver driver) {
                hideHelpSliders();
            }

            @Override
            public void afterClickOn(WebElement element, WebDriver driver) {

            }

            @Override
            public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

            }

            @Override
            public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {

            }

            @Override
            public void beforeScript(String script, WebDriver driver) {

            }

            @Override
            public void afterScript(String script, WebDriver driver) {

            }

            @Override
            public void onException(Throwable throwable, WebDriver driver) {

            }
        });
    }

    private void executeJavaScript(String javascript) {
        executor.executeScript(javascript);
    }

    private void hideHelpSliders() {
        executeJavaScript("console.log('Disabling slide in help for testing')");
        executeJavaScript("for (i = 0; i < document.getElementsByClassName('js-footer-help-slide-out').length; i++) { document.getElementsByClassName('js-footer-help-slide-out')[0].remove() }");
    }



    @Test
    public void A1viewingQuestionsWorksCorrectly() throws Exception {
        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("BrowseQuestions"));

        clearType(By.cssSelector("input[type='search']"), "Develop isEven()");
        findInTable("Id", "1").click();

        assertEquals(driver.findElement(By.id("question-text-for-1-1")).getText(), "You have been asked to develop a function called IsEven that return true if a given integer parameter is even, or false if odd. Write this function below.");
        assertEquals("/test-paper/view-question/1/1", driver.getCurrentUrl().replace(baseUrl,""));

        driver.navigate().back();
        clearType(By.cssSelector("input[type='search']"), "Sample multiple choice 9");
        findInTable("Id", "40").click();

        assertEquals(driver.findElement(By.id("question-text-for-40-1")).getText(), "Correct answer is A A) Some text\nB) Some text\nC) Some text");
        assertEquals("/test-paper/view-question/40/1", driver.getCurrentUrl().replace(baseUrl,""));
    }

    @Test
    public void A2viewingSectionsWorksCorrectly() throws Exception {
        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("BrowseSections"));

        findInTable("Id", "2").click();


        WebElement nextQuestionBtn = driver.findElement(By.id("nextQuestion"));
        assertEquals("1: C# Language", driver.findElement(By.cssSelector("a[slickslide='1']")).getText());
        assertEquals("We recommend you spend 22 minutes on this section.\nYou should answer 8 questions from this section.",driver.findElement(By.className("info-banner")).getText());
        nextQuestionBtn.click();

        assertEquals("Which classes can access a variable declared as private?\nA) All classes.\nB) Within the body of the class that encloses the declaration.\nC)Inheriting sub classes.\nD) Classes in the same namespace.",driver.findElement(By.id("question-text-for-15-1")).getText());
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        assertTrue(driver.findElement(By.className("slick-active")).getAttribute("innerHTML").contains("<span class=\"kwd\">public</span><span class=\"pln\"> </span><span class=\"kwd\">class</span><span class=\"pln\"> </span><span class=\"typ\">Example</span><span class=\"pln\"> </span><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">public</span><span class=\"pln\"> </span><span class=\"kwd\">static</span><span class=\"pln\"> </span><span class=\"kwd\">void</span><span class=\"pln\"> main</span><span class=\"pun\">(</span><span class=\"kwd\">string</span><span class=\"pun\">[]</span><span class=\"pln\">args</span><span class=\"pun\">)</span><span class=\"pln\"> </span><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"typ\">Example</span><span class=\"pln\"> example </span><span class=\"pun\">=</span><span class=\"pln\"> </span><span class=\"kwd\">new</span><span class=\"pln\"> </span><span class=\"typ\">Example</span><span class=\"pun\">();</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; example</span><span class=\"pun\">.</span><span class=\"typ\">Method1</span><span class=\"pun\">();</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp; </span><span class=\"pun\">}</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">public</span><span class=\"pln\"> </span><span class=\"kwd\">void</span><span class=\"pln\"> </span><span class=\"typ\">Method1</span><span class=\"pun\">()</span><span class=\"pln\"> </span><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">int</span><span class=\"pln\"> i </span><span class=\"pun\">=</span><span class=\"pln\"> </span><span class=\"lit\">99</span><span class=\"pun\">;</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"typ\">ValueHolder</span><span class=\"pln\"> vh&nbsp;&nbsp; </span><span class=\"pun\">=</span><span class=\"pln\"> </span><span class=\"kwd\">new</span><span class=\"pln\"> </span><span class=\"typ\">ValueHolder</span><span class=\"pun\">();</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; vh</span><span class=\"pun\">.</span><span class=\"pln\">i </span><span class=\"pun\">=</span><span class=\"pln\"> </span><span class=\"lit\">30</span><span class=\"pun\">;</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"typ\">Method2</span><span class=\"pun\">(</span><span class=\"pln\">vh</span><span class=\"pun\">,</span><span class=\"pln\"> i</span><span class=\"pun\">);</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"typ\">Console</span><span class=\"pun\">.</span><span class=\"typ\">WriteLine</span><span class=\"pun\">(</span><span class=\"pln\">vh</span><span class=\"pun\">.</span><span class=\"pln\">i</span><span class=\"pun\">);</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp; </span><span class=\"pun\">}</span><br><br><span class=\"pln\">&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">public</span><span class=\"pln\"> </span><span class=\"kwd\">void</span><span class=\"pln\"> </span><span class=\"typ\">Method2</span><span class=\"pun\">(</span><span class=\"typ\">ValueHolder</span><span class=\"pln\"> v</span><span class=\"pun\">,</span><span class=\"pln\"> </span><span class=\"kwd\">int</span><span class=\"pln\"> i</span><span class=\"pun\">)</span><span class=\"pln\"> </span><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; i&nbsp;&nbsp; </span><span class=\"pun\">=</span><span class=\"pln\"> </span><span class=\"lit\">0</span><span class=\"pun\">;</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; v</span><span class=\"pun\">.</span><span class=\"pln\">i </span><span class=\"pun\">=</span><span class=\"pln\"> </span><span class=\"lit\">20</span><span class=\"pun\">;</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"typ\">ValueHolder</span><span class=\"pln\"> vh </span><span class=\"pun\">=</span><span class=\"pln\"> </span><span class=\"kwd\">new</span><span class=\"pln\"> </span><span class=\"typ\">ValueHolder</span><span class=\"pun\">();</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; v&nbsp; </span><span class=\"pun\">=</span><span class=\"pln\"> vh</span><span class=\"pun\">;</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"typ\">Console</span><span class=\"pun\">.</span><span class=\"typ\">WriteLine</span><span class=\"pun\">(</span><span class=\"pln\">v</span><span class=\"pun\">.</span><span class=\"pln\">i </span><span class=\"pun\">+</span><span class=\"pln\"> </span><span class=\"str\">\" \"</span><span class=\"pln\"> </span><span class=\"pun\">+</span><span class=\"pln\"> i</span><span class=\"pun\">);</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp; </span><span class=\"pun\">}</span><br><br><span class=\"pln\">&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">class</span><span class=\"pln\"> </span><span class=\"typ\">ValueHolder</span><span class=\"pln\"> </span><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">public</span><span class=\"pln\"> </span><span class=\"kwd\">int</span><span class=\"pln\"> i </span><span class=\"pun\">=</span><span class=\"pln\"> </span><span class=\"lit\">10</span><span class=\"pun\">;</span><br><span class=\"pln\">&nbsp;&nbsp;&nbsp; </span><span class=\"pun\">}</span><br><br><span class=\"pun\">}</span>"));
    }

    @Test
    public void A3viewingPapersWorksCorrectly() {
        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("BrowsePapers"));

        findInTable("Id", "2").click();


        assertTrue(driver.findElement(By.linkText("Interview Test-Graduate (C#)")).isDisplayed());
        assertTrue(driver.findElement(By.linkText("2: Problem Solving")).isDisplayed());
        assertTrue(driver.findElement(By.linkText("1: C# Language")).isDisplayed());
        assertTrue(driver.findElement(By.linkText("3: Architecture and Theory")).isDisplayed());
        assertTrue(driver.findElement(By.linkText("4: Written Communication")).isDisplayed());

        WebElement testCaseDesignSection = driver.findElement(By.linkText("5: Test Case Design"));
        testCaseDesignSection.isDisplayed();
        testCaseDesignSection.click();

        driver.findElement(By.id("nextQuestion")).click();

        String actual = driver.findElement(By.className("slick-active")).getAttribute("innerHTML");

        assertTrue(actual.contains(
            "<p id=\"question-text-for-30-1\">The following code checks the validity of a date (which is passed in as 2 integer parameters). The code is looking to check the validity of all the days of the year, design the test data necessary to fully test this code. Note you do not need to consider leap years in your answer. </p><pre class=\"prettyprint prettyprinted\"><span class=\"kwd\">public</span><span class=\"pln\"> </span><span class=\"kwd\">class</span><span class=\"pln\"> </span><span class=\"typ\">DateValidator</span><br><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;</span><span class=\"kwd\">private</span><span class=\"pln\"> </span><span class=\"kwd\">static</span><span class=\"pln\"> </span><span class=\"kwd\">int</span><span class=\"pln\"> daysInMonth </span><span class=\"pun\">[</span><span class=\"lit\">12</span><span class=\"pun\">]</span><span class=\"pln\"> </span><span class=\"pun\">=</span><span class=\"pln\">&nbsp; </span><span class=\"pun\">{</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">28</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">30</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">30</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">30</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">30</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">};</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;</span><span class=\"kwd\">public</span><span class=\"pln\"> </span><span class=\"kwd\">static</span><span class=\"pln\"> </span><span class=\"kwd\">bool</span><span class=\"pln\"> validate</span><span class=\"pun\">(</span><span class=\"kwd\">int</span><span class=\"pln\"> day</span><span class=\"pun\">,</span><span class=\"pln\"> </span><span class=\"kwd\">int</span><span class=\"pln\"> month</span><span class=\"pun\">)</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;</span><span class=\"pun\">{</span><span class=\"pln\">&nbsp;&nbsp;&nbsp; &nbsp;</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">if</span><span class=\"pln\"> </span><span class=\"pun\">(</span><span class=\"pln\">month</span><span class=\"pun\">&gt;=</span><span class=\"lit\">1</span><span class=\"pln\"> </span><span class=\"pun\">&amp;&amp;</span><span class=\"pln\"> month </span><span class=\"pun\">&lt;=</span><span class=\"pln\"> </span><span class=\"lit\">12</span><span class=\"pln\"> </span><span class=\"pun\">&amp;&amp;</span><span class=\"pln\"> day </span><span class=\"pun\">&gt;=</span><span class=\"lit\">1</span><span class=\"pln\"> </span><span class=\"pun\">&amp;&amp;</span><span class=\"pln\"> day </span><span class=\"pun\">&lt;=</span><span class=\"pln\">daysInMonth</span><span class=\"pun\">[</span><span class=\"pln\">month</span><span class=\"pun\">-</span><span class=\"lit\">1</span><span class=\"pun\">])</span><span class=\"pln\"> </span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">return</span><span class=\"pln\"> </span><span class=\"kwd\">true</span><span class=\"pun\">:</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"pun\">}</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">else</span><span class=\"pln\"> </span><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">return</span><span class=\"pln\"> </span><span class=\"kwd\">false</span><span class=\"pun\">;</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"pun\">}</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;</span><span class=\"pun\">}</span><br><span class=\"pun\">}</span></pre><p></p>\n"
         ));
    }

    @Test
    public void A4viewingExamsWorksCorrectly() {
        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("BrowseExams"));

        findInTable("id", "1").click();

        String actual = driver.findElement(By.id("loadedContent")).getAttribute("innerHTML");

        assertTrue(actual.contains("Exam Date: 22/02/2017"));
        assertTrue(actual.contains("Status: Finished"));
        assertTrue(actual.contains("Start Time: 12:00 PM"));
        assertTrue(actual.contains("End Time: 2:30 PM"));
        assertTrue(actual.contains("End Time: 2:30 PM"));
        assertTrue(actual.contains("ALTERNATE INTERVIEW JAVA"));
    }

    @Test
    public void A5canCreateQuestions() throws InterruptedException, SQLException {
        loginAs("sampleAll","pass", "Jack Brown");
        createQuestion("Java",
                "Selenium Java Question",
                "10","15","This question was designed by selenium","Sample marking guide.", "Code");

        createQuestion("C#",
                "Selenium C# Question",
                "15","25","This question was designed by selenium","Sample marking guide.", "Essay");

        createQuestion("",
                "Selenium Multiple Choice Question 1",
                "5","1","This question was designed by selenium","Sample marking guide.", "Multiple Choice");

        createQuestion("",
                "Selenium Multiple Choice Question 2",
                "5","1","This question was designed by selenium","Sample marking guide.", "Multiple Choice");

        createQuestion("",
                "Selenium Multiple Choice Question 3",
                "5","1","This question was designed by selenium","Sample marking guide.", "Multiple Choice");

        createQuestion("",
                "Selenium Drawing Question",
                "50","10","This question was designed by selenium","Sample marking guide.", "Drawing");

        createQuestion("",
                "Selenium Expression Question 1",
                "50","10","This questions will test simple answer matching","Correct answer is Java, but C# gives one mark", "Expression");

        createQuestion("",
                "Selenium Expression Question 2",
                "50","10","This questions will test white space collapsing","Correct answer is Hello World, but Hi Planet gives one mark", "Expression");

        createQuestion("",
                "Selenium Expression Question 3",
                "50","10","This questions will test alternative punctuation","Correct answer is S.W.A.T, but F.E.A.R gives one mark", "Expression");

        createQuestion("",
                "Selenium Expression Question 4",
                "50","10","This questions will test case insensitivity","Correct answer is GoOGle but TwiTtEr gives one mark", "Expression");

        createQuestion("",
                "Selenium Expression Question 5",
                "50","10","This questions will test all options","Correct answer is F.E.A.R iS A GoOD   Game but Random gives one mark", "Expression");

        createQuestion("",
                "Selenium Expression Question 6",
                "50","10","This questions will test custom regex","Correct answer is any valid email but cheatSheet gives one mark", "Expression");

        if (backupUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("SCRIPT TO './src/test/resources/h2Back/testQuestionsCreated.sql'");
            statement.execute();
        }
    }

    @Test
    public void A6canEditQuestions() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc
                    .getDataSource()
                    .getConnection()
                    .prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/testQuestionsCreated.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");

        openEditorForQuestion("Name","Selenium Multiple Choice Question 1");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "\nA) This answer gives you 1 Mark\nB) This answer gives you 2 Marks\nC)This answer gives you 0 marks\nD)This answer gives you 4 marks.");

        multiChoiceMark(1,1);
        multiChoiceMark(2,2);
        multiChoiceMark(4,4);
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"content\"]/div/button[2]"));
        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Multiple Choice Question 2");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "\nA) \nB)\nC) D) Each answer gives you one mark.");

        multiChoiceMark(1,1);
        multiChoiceMark(2,1);
        multiChoiceMark(2,2);
        multiChoiceMark(3,1);
        multiChoiceMark(3,2);
        multiChoiceMark(3,3);
        multiChoiceMark(4,1);
        multiChoiceMark(4,2);
        multiChoiceMark(4,3);
        multiChoiceMark(4,4);
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"content\"]/div/button[2]"));
        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Multiple Choice Question 3");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "\nA) \nB) a and b gives you 1 Mark \nC) D) c and d give you 2 marks, a and d give you 3 marks, b and c give you 4 marks ");

        multiChoiceMark(1,1);
        multiChoiceMark(1,2);

        multiChoiceMark(2,3);
        multiChoiceMark(2,4);

        multiChoiceMark(3,1);
        multiChoiceMark(3,4);

        multiChoiceMark(4,2);
        multiChoiceMark(4,3);
        moveToBottomOfPage();

        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 1");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), " [[1]]");

        clearType(By.xpath("//*[@id=\"regex-builder-answer-1\"]"), "Java");
        clearType(By.xpath("//*[@id=\"mark-for-1\"]"), "50");
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-2\"]"), "C#");
        clearType(By.xpath("//*[@id=\"mark-for-2\"]"), "1");
        moveToBottomOfPage();

        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 2");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "[[1]]");

        clearType(By.xpath("//*[@id=\"regex-builder-answer-1\"]"), "Hello World");
        clearType(By.xpath("//*[@id=\"mark-for-1\"]"), "50");
        click(By.xpath("//*[@id=\"regex-builder-option1-whiteSpace\"]"));
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-2\"]"), "Hi Planet");
        clearType(By.xpath("//*[@id=\"mark-for-2\"]"), "1");
        click(By.xpath("//*[@id=\"regex-builder-option2-whiteSpace\"]"));
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"content\"]/div/button[2]"));
        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 3");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "[[1]]");

        clearType(By.xpath("//*[@id=\"regex-builder-answer-1\"]"), "S.W.A.T");
        clearType(By.xpath("//*[@id=\"mark-for-1\"]"), "50");
        click(By.xpath("//*[@id=\"regex-builder-option1-alternatePunctuation\"]"));
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-2\"]"), "F.E.A.R");
        clearType(By.xpath("//*[@id=\"mark-for-2\"]"), "1");
        click(By.xpath("//*[@id=\"regex-builder-option2-alternatePunctuation\"]"));
        moveToBottomOfPage();

        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 4");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "[[1]]");

        clearType(By.xpath("//*[@id=\"regex-builder-answer-1\"]"), "GoOGle");
        clearType(By.xpath("//*[@id=\"mark-for-1\"]"), "50");
        click(By.xpath("//*[@id=\"regex-builder-option1-caseInsensitive\"]"));
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-2\"]"), "TwiTtEr");
        clearType(By.xpath("//*[@id=\"mark-for-2\"]"), "1");
        click(By.xpath("//*[@id=\"regex-builder-option2-caseInsensitive\"]"));
        moveToBottomOfPage();

        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 5");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "[[1]]");

        clearType(By.xpath("//*[@id=\"regex-builder-answer-1\"]"), "F.E.A.R iS A GoOD   Game");
        clearType(By.xpath("//*[@id=\"mark-for-1\"]"), "50");
        click(By.xpath("//*[@id=\"regex-builder-option1-whiteSpace\"]"));
        click(By.xpath("//*[@id=\"regex-builder-option1-alternatePunctuation\"]"));
        click(By.xpath("//*[@id=\"regex-builder-option1-caseInsensitive\"]"));
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-2\"]"), "Random");
        clearType(By.xpath("//*[@id=\"mark-for-2\"]"), "1");
        moveToBottomOfPage();

        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 6");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "[[1]]");
        moveToBottomOfPage();

        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[2]"));
        clearType(By.xpath("//*[@id=\"regex-for-1\"]"), "((?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]))|cheatSheet");
        driver.findElement(By.xpath("//*[@id=\"mark-for-1\"]")).sendKeys("50");
        moveToBottomOfPage();
        waitForSuccessAlert();

        if (backupUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("SCRIPT TO './src/test/resources/h2Back/testQuestionsEdited.sql'");
            statement.execute();
        }
    }

    private void waitToLoad() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
    }

    @Test
    public void A7canCreateSection() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/testQuestionsEdited.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-edit"));
        click(By.id("CreateSections"));
        waitToLoad();

        driver.findElement(By.id("referenceName")).sendKeys("Selenium Section");
        clearType(By.id("noOfQuestionsToAnswer"),"6");
        clearType(By.id("timeScale"),"60");

        typeInFroala(By.xpath("//*[@id=\"form-group-instructionsText\"]/div[1]/div[3]/div"), "Sample selenium section");

        click(By.cssSelector("input[type=\"submit\"]"));

        WebDriverWait wait = new WebDriverWait(driver, 500);
        Pattern p = Pattern.compile(PAPER_PREFIX + PAPER_SECTION_EDITOR + "\\?sectionId=\\d&sectionVersion=1");
        wait.until(ExpectedConditions.urlMatches(p.pattern()));
        waitToLoad();

        assertEquals(driver.findElement(By.id("referenceName")).getAttribute("readonly"),"true");

        click(By.id("nav-browse"));
        click(By.id("BrowseSections"));
        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys("Selenium Section");

        findInTable("Name","Selenium Section").click();

        waitToLoad();
        assertTrue(driver.findElement(By.className("info-banner")).getText().contains("Sample selenium section"));

        click(By.id("nav-browse"));
        click(By.id("BrowseSections"));
        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys("Selenium Section");

        findInTable("Name","Selenium Section").findElement(By.className("glyphicon-pencil")).click();

        waitToLoad();
        driver.findElement(By.xpath("//*[@id=\"availableQuestions_filter\"]/label/input")).sendKeys("Selenium Java");
        click(By.xpath("//*[@id=\"554599056\"]/td[9]/a"));
        driver.findElement(By.xpath("//*[@id=\"availableQuestions_filter\"]/label/input")).sendKeys(Keys.CONTROL,"a");
        driver.findElement(By.xpath("//*[@id=\"availableQuestions_filter\"]/label/input")).sendKeys("Selenium C");
        click(By.xpath("//*[@id=\"134369432\"]/td[9]/a"));
        driver.findElement(By.xpath("//*[@id=\"availableQuestions_filter\"]/label/input")).sendKeys(Keys.CONTROL,"a");
        driver.findElement(By.xpath("//*[@id=\"availableQuestions_filter\"]/label/input")).sendKeys("Selenium");
        click(By.xpath("//*[@id=\"-1167953417\"]/td[9]/a"));
        click(By.xpath("//*[@id=\"-280419945\"]/td[9]/a"));
        click(By.xpath("//*[@id=\"607113527\"]/td[9]/a"));
        click(By.xpath("//*[@id=\"529745097\"]/td[9]/a"));

        clearType(By.xpath("//*[@id=\"availableQuestions_filter\"]/label/input"), "Selenium Expression");
        click(By.xpath("//*[@id=\"-280120751\"]/td[9]/a"));
        click(By.xpath("//*[@id=\"607412721\"]/td[9]/a"));
        click(By.xpath("//*[@id=\"1494946193\"]/td[9]/a"));
        click(By.xpath("//*[@id=\"-1912487631\"]/td[9]/a"));
        click(By.xpath("//*[@id=\"-1024954159\"]/td[9]/a"));
        click(By.xpath("//*[@id=\"-137420687\"]/td[9]/a"));


        String actual = driver.findElement(By.xpath("//*[@id=\"sectionQuestions\"]/tbody")).getAttribute("innerHTML");

        assertTrue(actual.contains("href=\"/test-paper/view-question/42/1\""));
        assertTrue(actual.contains("href=\"/test-paper/view-question/43/1\""));
        assertTrue(actual.contains("href=\"/test-paper/view-question/44/1\""));
        assertTrue(actual.contains("href=\"/test-paper/view-question/45/1\""));
        assertTrue(actual.contains("href=\"/test-paper/view-question/46/1\""));
        assertTrue(actual.contains("href=\"/test-paper/view-question/47/1\""));

        clearType(By.xpath("//*[@id=\"availableQuestions_filter\"]/label/input"),"Develop IsOdd");
        click(By.xpath("//*[@id=\"-1982189055\"]/td[9]/a"));

        clearType(By.xpath("//*[@id=\"sectionQuestions_filter\"]/label/input"),"Develop IsOdd");
        click(By.xpath("//*[@id=\"sectionQuestions\"]/tbody/tr/td[10]/a"));

        click(By.id("nav-browse"));
        click(By.id("BrowseSections"));
        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys("Selenium Section");
        findInTable("Name","Selenium Section").click();

        click(By.xpath("//*[@id=\"8\"]/div[3]"));

        String actualQuestion3 = driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]")).getAttribute("innerHTML");

        assertTrue(actualQuestion3.contains("Question 1.3"));
        assertTrue(actualQuestion3.contains("<em><u>This question was designed by selenium</u></em>"));
        assertTrue(actualQuestion3.contains("<em><u>A) This answer gives you 1 Mark</u></em>"));
        assertTrue(actualQuestion3.contains("<em><u>B) This answer gives you 2 Marks</u></em>"));

        click(By.xpath("//*[@id=\"8\"]/div[4]"));
        String actualQuestion4 = driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]")).getAttribute("innerHTML");
        assertTrue(actualQuestion4.contains("<em><u>This question was designed by selenium</u></em>"));
        assertTrue(actualQuestion4.contains("<em><u>A)&nbsp;</u></em>"));
        assertTrue(actualQuestion4.contains("<em><u>B)</u></em>"));
        assertTrue(actualQuestion4.contains("<em><u>C) D) Each answer gives you one mark.</u></em>"));



        click(By.xpath("//*[@id=\"8\"]/div[5]"));
        String actualQuestion5 = driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]")).getAttribute("innerHTML");

        assertTrue(actualQuestion5.contains("<em><u>This question was designed by selenium</u></em>"));
        assertTrue(actualQuestion5.contains("<em><u>A)&nbsp;</u></em>"));
        assertTrue(actualQuestion5.contains("<em><u>B) a and b gives you 1 Mark&nbsp;</u></em>"));
        assertTrue(actualQuestion5.contains("<em><u>C) D) c and d give you 2 marks, a and d give you 3 marks, b and c give you 4 marks&nbsp;</u></em>"));


        if (backupUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("SCRIPT TO './src/test/resources/h2Back/testSectionCreated.sql'");
            statement.execute();
        }
    }

    @Test
    public void A8canCreatePaper() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/testSectionCreated.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-edit"));
        click(By.id("CreatePapers"));

        waitToLoad();
        clearType(By.id("referenceName"), "Selenium Test Paper");
        clearType(By.id("timeAllowed"), "60");

        typeInFroala(By.xpath("//*[@id=\"form-group-instructionsText\"]/div[1]/div[3]/div/p"), "This test paper has been generated by selenium");

        driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("input[type=\"submit\"]")));
        waitToLoad();

        clearType(By.cssSelector("#availableSections_filter > label > input"), "Selenium");
        click(By.xpath("//*[@id=\"-799534753\"]/td[7]/a"));

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//*[@id=\"paperSections\"]/tbody/tr")));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"paperSections\"]/tbody/tr")).contains("8"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"paperSections\"]/tbody/tr")).contains("1"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"paperSections\"]/tbody/tr")).contains("Selenium Section"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"paperSections\"]/tbody/tr")).contains("6"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"paperSections\"]/tbody/tr")).contains("60"));

        if (backupUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("SCRIPT TO './src/test/resources/h2Back/paperCreated.sql'");
            statement.execute();
        }
    }

    private void select2Type(By by ,String choice) {
        click(by);
        Actions actions = new Actions(driver);
        actions.sendKeys(choice);
        actions.sendKeys(Keys.ENTER);
        actions.build().perform();
    }

    @Test
    public void A8canSetupAnExam() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/paperCreated.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-setup-exam"));

        select2Type(By.id("select2-pickPaperSelect-container"), "Selenium Test Paper");

        click(By.id("datepicker"));
        click(By.className("ui-state-highlight"));

        LocalTime startTime = new LocalTime(System.currentTimeMillis());

        typeInFroala(By.id("day.startTime"), startTime.toString(TIME_PATTERN));

        clearType(By.id("day.location"), "Sample Location");

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("ui-state-default"))));
        click(By.id("create-exam-btn"));

        wait.until(ExpectedConditions.urlMatches("https://localhost:8900/exam/review-exam/2"));
        assertTrue(driver.getCurrentUrl().equals("https://localhost:8900/exam/review-exam/2"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"loadedContent\"]")).contains("<div>Paper: <a href=\"/test-paper/4/1/view\">Selenium Test Paper</a></div>"));

        if (backupUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("SCRIPT TO './src/test/resources/h2Back/examSetUp.sql'");
            statement.execute();
        }
    }

    @Test
    public void A9canStartAnExam() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/examSetUp.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("BrowseExams"));

        findInTable("Test Paper", "Selenium Test Paper").click();

        click(By.xpath("//*[@id=\"loadedContent\"]/div/form/input[1]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"dashboardWindow\"]")).contains("Status: Started"));

        if (backupUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("SCRIPT TO './src/test/resources/h2Back/examStarted.sql'");
            statement.execute();
        }
    }

    private void loginToTakeExams(int userNo) {
        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("BrowseExams"));

        findInTable("Test Paper", "Selenium Test Paper").click();


        String userLogin = driver.findElement(By.xpath("//*[@id=\"DataTables_Table_0\"]/tbody/tr["+userNo+"]/td[3]")).getText();
        String userPass  = driver.findElement(By.xpath("//*[@id=\"DataTables_Table_0\"]/tbody/tr["+userNo+"]/td[4]")).getText();
        click(By.xpath("//*[@id=\"loadedContent\"]/div/a"));


        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/2/login"));

        driver.findElement(By.xpath("//*[@id=\"inputUsername\"]")).sendKeys(userLogin);
        driver.findElement(By.xpath("//*[@id=\"inputPassword\"]")).sendKeys(userPass);
        click(By.xpath("//*[@id=\"main-login-btn\"]"));

        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/\\d/start"));
        String html = getInnerHTML(By.xpath("/html/body/div[1]/div[2]/div/div/div"));
        assertTrue(html.contains("Test: Selenium Test Paper"));
        click(By.xpath("//*[@id=\"checkBoxID\"]"));
        click(By.xpath("//*[@id=\"buttonID\"]"));
        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/ongoing"));
        waitToLoad();
    }

    @Test
    public void B1canTakeExam() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/examStarted.sql'");
            statement.execute();
        }

        loginToTakeExams(1);
        waitToLoad();
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/div")).contains("Question 1.1"));

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/div/div/div"), "Sample code Example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/div/p")).contains("Question 1.2"));

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/textarea"), "Sample essay example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/div")).contains("Question 1.3"));


        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/div[4]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/div")).contains("Question 1.4"));

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[4]/input")));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[1]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[2]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[3]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[4]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/input[4]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/div")).contains("Question 1.5"));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[2]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[3]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/input[4]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/div")).contains("Question 1.6"));

        action.clickAndHold(driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/div")));
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.release();
        action.perform();

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/input[6]")));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/input[6]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/div")).contains("Question 1.7"));

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/div/div/input"),"Java");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/div")).contains("Question 1.8"));

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/div/div/input"), "Hello World");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/div")).contains("Question 1.9"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/div/div/input"), "S.W.A.T");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/div")).contains("Question 1.10"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/div/div/input"), "GoOGle");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/div")).contains("Question 1.11"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/div/div/input"), "F.E.A.R iS A GoOD   Game");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/div")).contains("Question 1.12"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/div/div/input"), "danielek4567@gmail.com");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/input[5]"));

        click(By.xpath("//*[@id=\"submitAllBtn\"]"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]")));
        click(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]"));

        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/finish-page"));
        click(By.id("buttonID"));

        loginToTakeExams(2);
        waitToLoad();
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/div")).contains("Question 1.1"));

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/div/div/div"), "Sample code Example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/div/p")).contains("Question 1.2"));

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/textarea"), "Sample essay example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/div")).contains("Question 1.3"));


        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/div[2]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/div")).contains("Question 1.4"));

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[4]/input")));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[1]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[2]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[3]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/input[4]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/div")).contains("Question 1.5"));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[2]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[3]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/input[4]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/div")).contains("Question 1.6"));

        action.clickAndHold(driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/div")));
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.release();
        action.perform();

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/input[6]")));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/input[6]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/div")).contains("Question 1.7"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/div/div/input"),"Java");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/div")).contains("Question 1.8"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/div/div/input"), "Hello                     World");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/div")).contains("Question 1.9"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/div/div/input"), "S,W,A,T");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/div")).contains("Question 1.10"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/div/div/input"), "google");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/div")).contains("Question 1.11"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/div/div/input"), "F E A R is a good GAME");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/div")).contains("Question 1.12"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/div/div/input"), "danielek4567@gmail.com");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/input[5]"));

        click(By.xpath("//*[@id=\"submitAllBtn\"]"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]")));
        click(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]"));

        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/finish-page"));
        click(By.id("buttonID"));

        loginToTakeExams(3);

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/div")).contains("Question 1.1"));

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/div/div/div"), "Sample code Example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/div/p")).contains("Question 1.2"));

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/textarea"), "Sample essay example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/div")).contains("Question 1.3"));


        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/div[1]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/div")).contains("Question 1.4"));

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[4]/input")));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[1]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[2]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/input[4]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/div")).contains("Question 1.5"));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[3]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[4]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/input[4]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/div")).contains("Question 1.6"));

        action.clickAndHold(driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/div")));
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.release();
        action.perform();

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/input[6]")));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/input[6]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/div")).contains("Question 1.7"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/div/div/input"),"C#");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/div")).contains("Question 1.8"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/div/div/input"), "Hi Planet");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/div")).contains("Question 1.9"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/div/div/input"), "F.E.A.R");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/div")).contains("Question 1.10"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/div/div/input"), "TwiTtEr");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/div")).contains("Question 1.11"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/div/div/input"), "Random");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/div")).contains("Question 1.12"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/div/div/input"), "cheatSheet");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/input[5]"));

        click(By.xpath("//*[@id=\"submitAllBtn\"]"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]")));
        click(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]"));

        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/finish-page"));
        click(By.id("buttonID"));

        loginToTakeExams(4);

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/div")).contains("Question 1.1"));

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/div/div/div"), "Sample code Example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/div/p")).contains("Question 1.2"));

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/textarea"), "Sample essay example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/div")).contains("Question 1.3"));


        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/div[3]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/input[4]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/div")).contains("Question 1.4"));

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[4]/input")));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[1]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/input[4]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/div")).contains("Question 1.5"));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[1]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[2]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/input[4]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/div")).contains("Question 1.6"));

        action.clickAndHold(driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/div")));
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.moveByOffset(10,105);
        action.moveByOffset(-40,-15);
        action.moveByOffset(140,345);
        action.release();
        action.perform();

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/input[6]")));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[8]/form/input[6]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/div")).contains("Question 1.7"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/div/div/input"),"C#");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/div")).contains("Question 1.8"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/div/div/input"), "Hi         Planet");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/div")).contains("Question 1.9"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/div/div/input"), "F|E|A|R");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/div")).contains("Question 1.10"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/div/div/input"), "twitter");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/div")).contains("Question 1.11"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/div/div/input"), "Random");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/div")).contains("Question 1.12"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/div/div/input"), "cheatSheet");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/input[5]"));

        click(By.xpath("//*[@id=\"submitAllBtn\"]"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]")));
        click(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]"));

        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/finish-page"));
        click(By.id("buttonID"));

        loginToTakeExams(5);

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/div")).contains("Question 1.7"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/div/div/input"),"Plain Wrong answer");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/div")).contains("Question 1.8"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/div/div/input"), "  ");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/div")).contains("Question 1.9"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/div/div/input"), "dsa");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/div")).contains("Question 1.10"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/div/div/input"), "twitterr");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/div")).contains("Question 1.11"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/div/div/input"), "RAndom");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/input[5]"));

        assertTrue(getInnerHTML(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/div")).contains("Question 1.12"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/div/div/input"), "cheatSheett");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/input[5]"));

        click(By.xpath("//*[@id=\"submitAllBtn\"]"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]")));
        click(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]"));

        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/finish-page"));
        click(By.id("buttonID"));

        if (backupUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("SCRIPT TO './src/test/resources/h2Back/examTaken.sql'");
            statement.execute();
        }
    }

    @Test
    public void B2CanFinnishExam() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/examTaken.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("BrowseExams"));

        findInTable("Test Paper", "Selenium Test Paper").click();

        click(By.xpath("//*[@id=\"loadedContent\"]/div/form/input[1]"));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"loadedContent\"]/div/a"))));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"loadedContent\"]/div/a")).contains("Mark Exam"));

        if (backupUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("SCRIPT TO './src/test/resources/h2Back/examFinished.sql'");
            statement.execute();
        }
    }

    private void markQuestion(int questionNo, int questionId, int attemptId, int mark) {
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"view-test-attempt-"+attemptId+"-section-1-questions-" + questionNo + "\"]"));
        ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("body")));
        moveToBottomOfPage();
        clearType(By.xpath("//*[@id=\"panel-comment-section"+questionNo+"\"]/textarea"), "Selenium Can Make Comments");
        click(By.xpath("//*[@id=\"form-for-attempt-"+attemptId+"-question-"+questionId+"-version-1\"]/div[2]/select"));
        click(By.xpath("//*[@id=\"form-for-attempt-"+attemptId+"-question-"+questionId+"-version-1\"]/div[2]/select/option["+(mark + 2)+"]"));
        click(By.xpath("//*[@id=\"submit-mark-button-for-"+attemptId+"-question-"+questionId+"-version-1\"]"));
        wait.until(ExpectedConditions.attributeToBe(By.xpath("//*[@id=\"form-for-attempt-"+attemptId+"-question-"+questionId+"-version-1\"]"),"ismarked", "true"));
        driver.close();
        driver.switchTo().window(tabs.get(0));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-"+attemptId+"-section-1-questions-" + questionNo + "\"]")), mark +"");
    }

    @Test
    public void B3CanMarkExam() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/examFinished.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("BrowseExams"));

        findInTable("Test Paper", "Selenium Test Paper").click();


        click(By.xpath("//*[@id=\"loadedContent\"]/div/a"));
        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/exam/mark/2"));
        waitToLoad();

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        markQuestion(1, 42, 1, 10);
        markQuestion(1, 42, 2, 10);
        markQuestion(1, 42, 3, 10);
        markQuestion(1, 42, 4, 10);
        markQuestion(1, 42, 5, 0);

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        markQuestion(2, 43, 1, 10);
        markQuestion(2, 43, 2, 10);
        markQuestion(2, 43, 3, 10);
        markQuestion(2, 43, 4, 10);
        markQuestion(2, 43, 5, 0);

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-3\"]")), "2");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-3\"]")), "0");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-3\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-3\"]")), "0");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-3\"]")), "4");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-4\"]")), "3");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-4\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-4\"]")), "2");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-4\"]")), "0");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-4\"]")), "4");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-5\"]")), "4");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-5\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-5\"]")), "2");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-5\"]")), "0");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-5\"]")), "4");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        markQuestion(6, 47, 1, 10);
        markQuestion(6, 47, 2, 10);
        markQuestion(6, 47, 3, 10);
        markQuestion(6, 47, 4, 10);
        markQuestion(6, 47, 5, 10);

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-7\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-7\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-7\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-7\"]")), "0");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-7\"]")), "50");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-8\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-8\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-8\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-8\"]")), "0");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-8\"]")), "50");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-9\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-9\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-9\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-9\"]")), "0");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-9\"]")), "50");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-10\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-10\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-10\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-10\"]")), "0");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-10\"]")), "50");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-11\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-11\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-11\"]")), "1");
        //assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-11\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-11\"]")), "50");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-12\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-12\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-12\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-12\"]")), "0");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-12\"]")), "50");

        moveToTheTopOfThePage();
        click(By.xpath("//*[@id=\"submitAllBtn\"]"));
        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/dashboard/generate-test"));

        if (backupUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("SCRIPT TO './src/test/resources/h2Back/examMarked.sql'");
            statement.execute();
        }
    }

    @Test
    public void B4MultiChoiceWizardRegenerates() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/examMarked.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("BrowseQuestions"));

        clearType(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input"), "Selenium Multiple Choice Question 3");
        findInTable("Name","Selenium Multiple Choice Question 3").findElement(By.className("glyphicon-pencil")).click();

        assertTrue(driver.findElement(By.cssSelector("input[data-score='1'][data-letter-required='A)']")).isSelected());
        assertTrue(driver.findElement(By.cssSelector("input[data-score='1'][data-letter-required='B)']")).isSelected());

        assertTrue(driver.findElement(By.cssSelector("input[data-score='2'][data-letter-required='C)']")).isSelected());
        assertTrue(driver.findElement(By.cssSelector("input[data-score='2'][data-letter-required='D)']")).isSelected());

        assertTrue(driver.findElement(By.cssSelector("input[data-score='3'][data-letter-required='A)']")).isSelected());
        assertTrue(driver.findElement(By.cssSelector("input[data-score='3'][data-letter-required='D)']")).isSelected());

        assertTrue(driver.findElement(By.cssSelector("input[data-score='4'][data-letter-required='B)']")).isSelected());
        assertTrue(driver.findElement(By.cssSelector("input[data-score='4'][data-letter-required='C)']")).isSelected());
    }

    private void submitAnswer(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 500);
        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        click(by);
    }

    private void openEditorForQuestion(String col, String value) {
        click(By.id("nav-browse"));
        click(By.id("BrowseQuestions"));

        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys(value);
        findInTable(col,value).findElement(By.className("glyphicon-pencil")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
    }

    private void moveToElement(By by) {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(by));
        actions.sendKeys("");
        actions.build().perform();
    }

    private void moveToTheTopOfThePage() {
        Actions actions = new Actions(driver);
        driver.findElement(By.xpath("/html/body")).sendKeys("");
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.sendKeys(Keys.PAGE_UP);
        actions.perform();
    }

    private void moveToBottomOfPage() {
        Actions actions = new Actions(driver);
        driver.findElement(By.xpath("/html/body")).sendKeys("");
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.sendKeys(Keys.PAGE_DOWN);
        actions.perform();
    }

    private void createQuestion(String language, String referenceName, String difficulty, String timeScale, String questionText, String markingGuide, String questionType) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        click(By.id("nav-edit"));
        click(By.id("CreateQuestions"));
        driver.findElement(By.id("language")).sendKeys(language);
        driver.findElement(By.id("referenceName")).sendKeys(referenceName);
        clearType(By.id("difficulty"), difficulty);
        clearType(By.id("timeScale"),timeScale);
        click(By.id("question-type-select"));
        click(By.cssSelector("option[value='"+questionType+"']"));
        clearFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"));
        click(By.xpath("//*[@id=\"italic-1\"]"));
        click(By.xpath("//*[@id=\"underline-1\"]"));
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), questionText);
        clearFroala(By.xpath("//*[@id=\"form-group-markingGuide\"]/div[1]/div[3]/div/p"));
        typeInFroala(By.xpath("//*[@id=\"form-group-markingGuide\"]/div[1]/div[3]/div/p"),markingGuide);


        click(By.xpath("//*[@id=\"content\"]/div/form/input[4]"));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/form/input[4]")));

        assertEquals(driver.findElement(By.id("language")).getAttribute("readonly"),"true");
        assertEquals(driver.findElement(By.id("referenceName")).getAttribute("readonly"),"true");
        assertEquals(driver.findElement(By.id("difficulty")).getAttribute("readonly"),"true");

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        click(By.id("nav-browse"));
        click(By.id("BrowseQuestions"));
        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys(referenceName);

        findInTable("Name",referenceName).click();

        String questionContents = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div")).getAttribute("innerHTML");
        assertTrue(questionContents.contains("<em><u>"+questionText+"</u></em>"));


        click(By.linkText("View as Marker"));
        click(By.linkText("Marking Guide:"));

        String markingGuideContents = driver.findElement(By.id("panel-answer-section1")).getAttribute("innerHTML");
        assertTrue(markingGuideContents.replace("&nbsp;", " ").contains(markingGuide));
    }

    private void multiChoiceMark(int mark, int letter) {
        click(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr["+(mark)+"]/td[2]/input["+letter+"]"));
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private void waitForSuccessAlert() {
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("noty_type_success")));
    }

    private void loginAs(String userName, String password, String expectedName) {
        driver.get(baseUrl + "/login");
        WebElement loginBox = driver.findElement(By.id("inputUsername"));
        WebElement passwordBox = driver.findElement(By.id("inputPassword"));
        WebElement loginBtn = driver.findElement(By.id("main-login-btn"));
        loginBox.sendKeys(userName);
        passwordBox.sendKeys(password);
        loginBtn.click();
        assertEquals(driver.findElement(By.id("logOut")).getText(),"Log Out " + expectedName);
    }

    private void click(By by) {
        moveToElement(by);
        wait.until(ExpectedConditions.elementToBeClickable(by));
        driver.findElement(by).click();
    }

    private WebElement findInTable(String columnName,String value) {
        WebElement baseTable = driver.findElement(By.id("viewListTable"));
        List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
        List<WebElement> columnHeadingsRow = tableRows.get(0).findElements(By.tagName("th"));

        int columnIndex = -1;
        for (int i = 0; i < columnHeadingsRow.size(); i++) {
            WebElement tableHeading = columnHeadingsRow.get(i);
            if (tableHeading.getText().equals(columnName)) {
                columnIndex = i;
                break;
            }
        }

        for(WebElement trElement : tableRows.subList(1, tableRows.size()))
        {
            List<WebElement> rowData = trElement.findElements(By.tagName("td"));
            if (rowData.get(columnIndex).getText().equals(value)) {
                return trElement;
            }
        }

        return null;
    }

    private void clearType(By findBy, String keys) {
        WebElement element = driver.findElement(findBy);
        element.sendKeys(Keys.CONTROL,"a");
        element.sendKeys(Keys.DELETE);
        element.sendKeys(keys);
    }

    private void clearFroala(By findBy) {
        wait.until(ExpectedConditions.elementToBeClickable(findBy));
        driver.findElement(findBy).click();
        wait.until(ExpectedConditions.elementToBeClickable(findBy));
        driver.findElement(findBy).click();
        wait.until(ExpectedConditions.elementToBeClickable(findBy));
        driver.findElement(findBy).click();

        Actions action = new Actions(driver);
        String selectAll = Keys.chord(Keys.CONTROL, "a");
        action.sendKeys(selectAll);
        action.sendKeys(Keys.DELETE);
        action.perform();
    }

    private void typeInFroala(By findBy, String keys) {
        wait.until(ExpectedConditions.elementToBeClickable(findBy));
        driver.findElement(findBy).click();
        wait.until(ExpectedConditions.elementToBeClickable(findBy));
        driver.findElement(findBy).click();
        wait.until(ExpectedConditions.elementToBeClickable(findBy));
        driver.findElement(findBy).click();
        Actions action = new Actions(driver);
        action.sendKeys(keys);
        action.perform();
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private String getInnerHTML(By by) {
        return driver.findElement(by).getAttribute("innerHTML");
    }

    private String getValue(By by) {
        return driver.findElement(by).getAttribute("value");
    }
}
