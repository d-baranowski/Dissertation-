package selenium.ui;

import org.joda.time.LocalTime;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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
    private WebDriver driver;
    private String baseUrl = "https://localhost:8900";
    private StringBuffer verificationErrors = new StringBuffer();
    private WebDriverWait wait;
    Actions action;

    @Autowired
    JdbcTemplate jdbc;

    @Value("${backupUITestStages}")
    private Boolean backupUITestStages;

    @Value("${loadUITestStages}")
    private Boolean loadUITestStages;

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 500);
        action = new Actions(driver);
    }

    @Test
    public void A1viewingQuestionsWorksCorrectly() throws Exception {
        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("view-questions-link"));

        findInTable("Id", "1").click();
        click(By.linkText("View Selected"));
        assertEquals(driver.findElement(By.id("question-text-for-1-1")).getText(), "You have been asked to develop a function called IsEven that return true if a given integer parameter is even, or false if odd. Write this function below.");
        assertEquals("/test-paper/view-question/1/1", driver.getCurrentUrl().replace(baseUrl,""));

        driver.navigate().back();
        click(By.cssSelector("a[data-dt-idx='3']"));
        findInTable("Id", "40").click();
        click(By.linkText("View Selected"));
        assertEquals(driver.findElement(By.id("question-text-for-40-1")).getText(), "Correct answer is A A) Some text\nB) Some text\nC) Some text");
        assertEquals("/test-paper/view-question/40/1", driver.getCurrentUrl().replace(baseUrl,""));
    }

    @Test
    public void A2viewingSectionsWorksCorrectly() throws Exception {
        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("view-sections-link"));

        findInTable("Id", "2").click();
        click(By.linkText("View Selected"));

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
        click(By.id("view-papers-link"));

        findInTable("Id", "2").click();
        driver.findElement(By.linkText("View Selected")).click();

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
                "\n" +
                        "    <div class=\"questionHeader\">\n" +
                        "        <p>Question 5.1</p>\n" +
                        "    </div>\n" +
                        "\n" +
                        "    <b><p>(5 minutes)</p></b>\n" +
                        "\n" +
                        "    <p id=\"question-text-for-30-1\">The following code checks the validity of a date (which is passed in as 2 integer parameters). The code is looking to check the validity of all the days of the year, design the test data necessary to fully test this code. Note you do not need to consider leap years in your answer. </p><pre class=\"prettyprint prettyprinted\"><span class=\"kwd\">public</span><span class=\"pln\"> </span><span class=\"kwd\">class</span><span class=\"pln\"> </span><span class=\"typ\">DateValidator</span><br><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;</span><span class=\"kwd\">private</span><span class=\"pln\"> </span><span class=\"kwd\">static</span><span class=\"pln\"> </span><span class=\"kwd\">int</span><span class=\"pln\"> daysInMonth </span><span class=\"pun\">[</span><span class=\"lit\">12</span><span class=\"pun\">]</span><span class=\"pln\"> </span><span class=\"pun\">=</span><span class=\"pln\">&nbsp; </span><span class=\"pun\">{</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">28</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">30</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">30</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">30</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">,</span><span class=\"lit\">30</span><span class=\"pun\">,</span><span class=\"lit\">31</span><span class=\"pun\">};</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;</span><span class=\"kwd\">public</span><span class=\"pln\"> </span><span class=\"kwd\">static</span><span class=\"pln\"> </span><span class=\"kwd\">bool</span><span class=\"pln\"> validate</span><span class=\"pun\">(</span><span class=\"kwd\">int</span><span class=\"pln\"> day</span><span class=\"pun\">,</span><span class=\"pln\"> </span><span class=\"kwd\">int</span><span class=\"pln\"> month</span><span class=\"pun\">)</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;</span><span class=\"pun\">{</span><span class=\"pln\">&nbsp;&nbsp;&nbsp; &nbsp;</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">if</span><span class=\"pln\"> </span><span class=\"pun\">(</span><span class=\"pln\">month</span><span class=\"pun\">&gt;=</span><span class=\"lit\">1</span><span class=\"pln\"> </span><span class=\"pun\">&amp;&amp;</span><span class=\"pln\"> month </span><span class=\"pun\">&lt;=</span><span class=\"pln\"> </span><span class=\"lit\">12</span><span class=\"pln\"> </span><span class=\"pun\">&amp;&amp;</span><span class=\"pln\"> day </span><span class=\"pun\">&gt;=</span><span class=\"lit\">1</span><span class=\"pln\"> </span><span class=\"pun\">&amp;&amp;</span><span class=\"pln\"> day </span><span class=\"pun\">&lt;=</span><span class=\"pln\">daysInMonth</span><span class=\"pun\">[</span><span class=\"pln\">month</span><span class=\"pun\">-</span><span class=\"lit\">1</span><span class=\"pun\">])</span><span class=\"pln\"> </span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">return</span><span class=\"pln\"> </span><span class=\"kwd\">true</span><span class=\"pun\">:</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"pun\">}</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">else</span><span class=\"pln\"> </span><span class=\"pun\">{</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"kwd\">return</span><span class=\"pln\"> </span><span class=\"kwd\">false</span><span class=\"pun\">;</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; </span><span class=\"pun\">}</span><br><span class=\"pln\">&nbsp;&nbsp; &nbsp;</span><span class=\"pun\">}</span><br><span class=\"pun\">}</span></pre><p></p>\n" +
                        "\n" +
                        "    \n" +
                        "\n" +
                        "    \n" +
                        "\n" +
                        "    \n" +
                        "        <th:box>\n" +
                        "           <th:box>\n" +
                        "                <div class=\"collapsible-elements\">\n" +
                        "                    \n" +
                        "                    \n" +
                        "                    \n" +
                        "                </div>\n" +
                        "            </th:box>\n" +
                        "        </th:box>\n" +
                        "        \n" +
                        "    \n"));
    }

    @Test
    public void A4viewingExamsWorksCorrectly() {
        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.id("view-exams-link"));

        findInTable("id", "1").click();
        click(By.linkText("View Selected"));
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
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "\nA) This answer gives you 1 Mark\nB) This answer gives you 2 Marks\nC)This answer gives you 0 marksD)This answer gives you 4 marks.");
        click(By.id("js-rebuild-wizard"));
        multiChoiceMark(0,3);
        multiChoiceMark(1,1);
        multiChoiceMark(2,2);
        multiChoiceMark(4,4);
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"content\"]/div/button[2]"));
        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Multiple Choice Question 2");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "\nA) \nB)\nC) D) Each answer gives you one mark.");
        click(By.id("js-rebuild-wizard"));
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
        click(By.id("js-rebuild-wizard"));
        multiChoiceMark(1,1);
        multiChoiceMark(1,2);

        multiChoiceMark(2,3);
        multiChoiceMark(2,4);

        multiChoiceMark(3,1);
        multiChoiceMark(3,4);

        multiChoiceMark(4,2);
        multiChoiceMark(4,3);
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"content\"]/div/button[2]"));
        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 1");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "[[1]]");
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[2]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-1\"]"), "Java");
        clearType(By.xpath("//*[@id=\"mark-for-1\"]"), "50");
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-2\"]"), "C#");
        clearType(By.xpath("//*[@id=\"mark-for-2\"]"), "1");
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"content\"]/div/button[2]"));
        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 2");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "[[1]]");
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[2]/button[1]"));
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
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[2]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-1\"]"), "S.W.A.T");
        clearType(By.xpath("//*[@id=\"mark-for-1\"]"), "50");
        click(By.xpath("//*[@id=\"regex-builder-option1-alternatePunctuation\"]"));
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-2\"]"), "F.E.A.R");
        clearType(By.xpath("//*[@id=\"mark-for-2\"]"), "1");
        click(By.xpath("//*[@id=\"regex-builder-option2-alternatePunctuation\"]"));
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"content\"]/div/button[2]"));
        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 4");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "[[1]]");
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[2]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-1\"]"), "GoOGle");
        clearType(By.xpath("//*[@id=\"mark-for-1\"]"), "50");
        click(By.xpath("//*[@id=\"regex-builder-option1-caseInsensitive\"]"));
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-2\"]"), "TwiTtEr");
        clearType(By.xpath("//*[@id=\"mark-for-2\"]"), "1");
        click(By.xpath("//*[@id=\"regex-builder-option2-caseInsensitive\"]"));
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"content\"]/div/button[2]"));
        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 5");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "[[1]]");
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[2]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-1\"]"), "F.E.A.R iS A GoOD   Game");
        clearType(By.xpath("//*[@id=\"mark-for-1\"]"), "50");
        click(By.xpath("//*[@id=\"regex-builder-option1-whiteSpace\"]"));
        click(By.xpath("//*[@id=\"regex-builder-option1-alternatePunctuation\"]"));
        click(By.xpath("//*[@id=\"regex-builder-option1-caseInsensitive\"]"));
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[1]"));
        clearType(By.xpath("//*[@id=\"regex-builder-answer-2\"]"), "Random");
        clearType(By.xpath("//*[@id=\"mark-for-2\"]"), "1");
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"content\"]/div/button[2]"));
        waitForSuccessAlert();

        openEditorForQuestion("Name","Selenium Expression Question 6");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "[[1]]");
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[2]/button[1]"));
        click(By.xpath("//*[@id=\"{row-id}\"]/div[3]/div[1]/button[2]"));
        clearType(By.xpath("//*[@id=\"regex-for-1\"]"), "((?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\]))|cheatSheet");
        driver.findElement(By.xpath("//*[@id=\"mark-for-1\"]")).sendKeys("50");
        moveToBottomOfPage();
        click(By.xpath("//*[@id=\"content\"]/div/button[2]"));
        waitForSuccessAlert();

        if (backupUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("SCRIPT TO './src/test/resources/h2Back/testQuestionsEdited.sql'");
            statement.execute();
        }
    }

    @Test
    public void A7canCreateSection() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/testQuestionsEdited.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-edit"));
        click(By.linkText("Sections"));

        driver.findElement(By.id("referenceName")).sendKeys("Selenium Section");
        clearType(By.id("noOfQuestionsToAnswer"),"6");
        clearType(By.id("timeScale"),"60");

        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "Sample selenium section");

        click(By.xpath("//*[@id=\"content\"]/div/form/input[3]"));

        WebDriverWait wait = new WebDriverWait(driver, 500);
        Pattern p = Pattern.compile(PAPER_PREFIX + PAPER_SECTION_EDITOR + "\\?sectionId=\\d&sectionVersion=1");
        wait.until(ExpectedConditions.urlMatches(p.pattern()));

        assertEquals(driver.findElement(By.id("referenceName")).getAttribute("readonly"),"true");

        click(By.id("nav-browse"));
        click(By.linkText("Sections"));
        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys("Selenium Section");

        findInTable("Name","Selenium Section").click();
        click(By.linkText("View Selected"));

        assertTrue(driver.findElement(By.className("info-banner")).getText().contains("Sample selenium section"));

        click(By.id("nav-browse"));
        click(By.linkText("Sections"));
        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys("Selenium Section");

        findInTable("Name","Selenium Section").click();
        click(By.linkText("Edit Selected"));

        driver.findElement(By.xpath("//*[@id=\"availableQuestions_filter\"]/label/input")).sendKeys("Selenium");
        click(By.xpath("//*[@id=\"554599056\"]/td[9]/a"));
        click(By.xpath("//*[@id=\"134369432\"]/td[9]/a"));
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

        assertTrue(actual.contains("<a href=\"/test-paper/view-question/42/1\">View</a>"));
        assertTrue(actual.contains("<a href=\"/test-paper/view-question/43/1\">View</a>"));
        assertTrue(actual.contains("<a href=\"/test-paper/view-question/44/1\">View</a>"));
        assertTrue(actual.contains("<a href=\"/test-paper/view-question/45/1\">View</a>"));
        assertTrue(actual.contains("<a href=\"/test-paper/view-question/46/1\">View</a>"));
        assertTrue(actual.contains("<a href=\"/test-paper/view-question/47/1\">View</a>"));

        clearType(By.xpath("//*[@id=\"availableQuestions_filter\"]/label/input"),"Develop IsOdd");
        click(By.xpath("//*[@id=\"-1982189055\"]/td[9]/a"));

        clearType(By.xpath("//*[@id=\"sectionQuestions_filter\"]/label/input"),"Develop IsOdd");
        click(By.xpath("//*[@id=\"sectionQuestions\"]/tbody/tr/td[10]/a"));

        click(By.id("nav-browse"));
        click(By.linkText("Sections"));
        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys("Selenium Section");
        findInTable("Name","Selenium Section").click();

        click(By.linkText("View Selected"));
        click(By.xpath("//*[@id=\"8\"]/div[3]"));

        String actualQuestion3 = driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]")).getAttribute("innerHTML");

        assertTrue(actualQuestion3.contains("Question 1.3"));
        assertTrue(actualQuestion3.contains("This question was designed by selenium"));
        assertTrue(actualQuestion3.contains("<p><em><u>This question was designed by selenium</u></em></p>"));
        assertTrue(actualQuestion3.contains("<p><em><u>A) This answer gives you 1 Mark</u></em></p>"));
        assertTrue(actualQuestion3.contains("<p><em><u>B) This answer gives you 2 Marks</u></em></p>"));

        click(By.xpath("//*[@id=\"8\"]/div[4]"));
        String actualQuestion4 = driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]")).getAttribute("innerHTML");
        assertTrue(actualQuestion4.contains(
                "    <div class=\"questionHeader\">\n" +
                "        <p>Question 1.4</p>\n" +
                "    </div>\n" +
                "\n" +
                "    <b><p>(1 minutes)</p></b>\n" +
                "\n" +
                "    <p id=\"question-text-for-45-1\"></p><p><em><u>This question was designed by selenium</u></em></p><p><em><u>A)&nbsp;</u></em></p><p><em><u>B)</u></em></p><p><em><u>C) D) Each answer gives you one mark.</u></em></p><p></p>\n" +
                "\n" +
                "    \n" +
                "\n" +
                "    \n" +
                "\n" +
                "    \n" +
                "        <th:box>\n" +
                "           <th:box>\n" +
                "                <div class=\"collapsible-elements\">\n" +
                "                    \n" +
                "                    \n" +
                "    <div class=\"correct-answer-section collapsible-element\">\n" +
                "        <div class=\"title collapsed\" data-toggle=\"collapse\" data-target=\"#panel-answer-section4\" aria-expanded=\"false\">\n" +
                "            <a tabindex=\"0\">Marking Guide:</a>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"panel-answer-section collapsible collapse\" id=\"panel-answer-section4\" aria-expanded=\"false\">\n" +
                "            <div style=\"resize: none;\"><p>Sample marking guide.</p></div>\n" +
                "        </div>\n" +
                "    </div>\n"));


        click(By.xpath("//*[@id=\"8\"]/div[5]"));
        String actualQuestion5 = driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]")).getAttribute("innerHTML");

        assertTrue(actualQuestion5.contains(
                "    <div class=\"questionHeader\">\n" +
                "        <p>Question 1.5</p>\n" +
                "    </div>\n" +
                "\n" +
                "    <b><p>(1 minutes)</p></b>\n" +
                "\n" +
                "    <p id=\"question-text-for-46-1\"></p><p><em><u>This question was designed by selenium</u></em></p><p><em><u>A)&nbsp;</u></em></p><p><em><u>B) a and b gives you 1 Mark&nbsp;</u></em></p><p><em><u>C) D) c and d give you 2 marks, a and d give you 3 marks, b and c give you 4 marks&nbsp;</u></em></p><p></p>\n" +
                "\n" +
                "    \n" +
                "\n" +
                "    \n" +
                "\n" +
                "    \n" +
                "        <th:box>\n" +
                "           <th:box>\n" +
                "                <div class=\"collapsible-elements\">\n" +
                "                    \n" +
                "                    \n" +
                "    <div class=\"correct-answer-section collapsible-element\">\n" +
                "        <div class=\"title collapsed\" data-toggle=\"collapse\" data-target=\"#panel-answer-section5\" aria-expanded=\"false\">\n" +
                "            <a tabindex=\"0\">Marking Guide:</a>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"panel-answer-section collapsible collapse\" id=\"panel-answer-section5\" aria-expanded=\"false\">\n" +
                "            <div style=\"resize: none;\"><p>Sample marking guide.</p></div>\n" +
                "        </div>\n" +
                "    </div>\n"));

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
        click(By.linkText("Papers"));

        clearType(By.id("referenceName"), "Selenium Test Paper");
        clearType(By.id("timeAllowed"), "60");

        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "This test paper has been generated by selenium");

        driver.findElement(By.xpath("//*[@id=\"content\"]/div/form/input[3]")).click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/form/input[3]")));

        clearType(By.xpath("//*[@id=\"availableSections_filter\"]/label/input"), "Selenium");

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

    @Test
    public void A8canSetupAnExam() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/paperCreated.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("pickPaperSelect"));
        click(By.xpath("//*[@id=\"pickPaperSelect\"]/option[4]"));
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
        click(By.linkText("Exams"));

        findInTable("Test Paper", "Selenium Test Paper").click();
        click(By.linkText("View Selected"));

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
        click(By.linkText("Exams"));

        findInTable("Test Paper", "Selenium Test Paper").click();
        click(By.linkText("View Selected"));

        String userLogin = driver.findElement(By.xpath("//*[@id=\"loadedContent\"]/div/table/tbody/tr["+userNo+"]/td[3]")).getText();
        String userPass  = driver.findElement(By.xpath("//*[@id=\"loadedContent\"]/div/table/tbody/tr["+userNo+"]/td[4]")).getText();
        click(By.xpath("//*[@id=\"loadedContent\"]/div/a"));


        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/2/login"));

        driver.findElement(By.xpath("//*[@id=\"inputUsername\"]")).sendKeys(userLogin);
        driver.findElement(By.xpath("//*[@id=\"inputPassword\"]")).sendKeys(userPass);
        click(By.xpath("//*[@id=\"main-login-btn\"]"));

        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/"+userNo+"/start"));
        String html = getInnerHTML(By.xpath("/html/body/div[1]/div[2]/div/div/div"));
        assertTrue(html.contains("Test: Selenium Test Paper"));
        click(By.xpath("//*[@id=\"checkBoxID\"]"));
        click(By.xpath("//*[@id=\"buttonID\"]"));
        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/ongoing"));
    }

    @Test
    public void B1canTakeExam() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/examStarted.sql'");
            statement.execute();
        }

        loginToTakeExams(1);

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
        click(By.linkText("Exams"));

        findInTable("Test Paper", "Selenium Test Paper").click();
        click(By.linkText("View Selected"));
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
        click(By.linkText("Exams"));

        findInTable("Test Paper", "Selenium Test Paper").click();
        click(By.linkText("View Selected"));

        click(By.xpath("//*[@id=\"loadedContent\"]/div/a"));
        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/exam/mark/2"));

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

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-3\"]")), "4");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-3\"]")), "2");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-3\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-3\"]")), "0");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-3\"]")), "0");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-4\"]")), "4");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-4\"]")), "3");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-4\"]")), "2");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-4\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-4\"]")), "0");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-5\"]")), "4");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-5\"]")), "4");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-5\"]")), "2");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-5\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-5\"]")), "0");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        markQuestion(6, 47, 1, 10);
        markQuestion(6, 47, 2, 10);
        markQuestion(6, 47, 3, 10);
        markQuestion(6, 47, 4, 10);
        markQuestion(6, 47, 5, 10);

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-7\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-7\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-7\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-7\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-7\"]")), "0");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-8\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-8\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-8\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-8\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-8\"]")), "0");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-9\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-9\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-9\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-9\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-9\"]")), "0");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-10\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-10\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-10\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-10\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-10\"]")), "0");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-11\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-11\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-11\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-11\"]")), "1");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-11\"]")), "0");

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-1-section-1-questions-12\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-2-section-1-questions-12\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-3-section-1-questions-12\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-4-section-1-questions-12\"]")), "50");
        assertEquals(getValue(By.xpath("//*[@id=\"select-mark-for-test-attempt-5-section-1-questions-12\"]")), "0");

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
        click(By.linkText("Questions"));

        clearType(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input"), "Selenium Multiple Choice Question 3");
        click(By.xpath("//*[@id=\"viewListTable\"]/tbody/tr/td[3]"));
        click(By.linkText("Edit Selected"));

        assertTrue(driver.findElement(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr[2]/td[2]/input[1]")).isSelected());
        assertTrue(driver.findElement(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr[2]/td[2]/input[2]")).isSelected());

        assertTrue(driver.findElement(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr[3]/td[2]/input[3]")).isSelected());
        assertTrue(driver.findElement(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr[3]/td[2]/input[4]")).isSelected());

        assertTrue(driver.findElement(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr[4]/td[2]/input[1]")).isSelected());
        assertTrue(driver.findElement(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr[4]/td[2]/input[4]")).isSelected());

        assertTrue(driver.findElement(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr[5]/td[2]/input[2]")).isSelected());
        assertTrue(driver.findElement(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr[5]/td[2]/input[3]")).isSelected());
    }

    @Test
    public void B5ExpressionWizardRegenerates() throws SQLException {
        if (loadUITestStages) {
            PreparedStatement statement = jdbc.getDataSource().getConnection().prepareStatement("DROP ALL OBJECTS; RUNSCRIPT FROM './src/test/resources/h2Back/examMarked.sql'");
            statement.execute();
        }

        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.linkText("Questions"));

        clearType(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input"), "Selenium Expression Question");
        click(By.xpath("//*[@id=\"viewListTable\"]/tbody/tr/td[3]"));
        click(By.linkText("Edit Selected"));


    }

    private void submitAnswer(By by) {
        WebDriverWait wait = new WebDriverWait(driver, 500);
        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        click(by);
    }

    private void openEditorForQuestion(String col, String value) {
        click(By.id("nav-browse"));
        click(By.linkText("Questions"));

        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys(value);
        findInTable(col,value).click();
        click(By.linkText("Edit Selected"));
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
        click(By.id("nav-edit"));
        click(By.linkText("Questions"));
        driver.findElement(By.id("language")).sendKeys(language);
        driver.findElement(By.id("referenceName")).sendKeys(referenceName);
        clearType(By.id("difficulty"), difficulty);
        clearType(By.id("timeScale"),timeScale);
        click(By.xpath("//*[@id=\"form-group-type\"]/p/select"));
        click(By.cssSelector("option[value='"+questionType+"']"));
        click(By.xpath("//*[@id=\"italic-1\"]"));
        click(By.xpath("//*[@id=\"underline-1\"]"));
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), questionText);
        typeInFroala(By.xpath("//*[@id=\"form-group-markingGuide\"]/div[1]/div[3]/div/p"),markingGuide);

        click(By.xpath("//*[@id=\"content\"]/div/form/input[4]"));

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"content\"]/div/form/input[4]")));

        assertEquals(driver.findElement(By.id("language")).getAttribute("readonly"),"true");
        assertEquals(driver.findElement(By.id("referenceName")).getAttribute("readonly"),"true");
        assertEquals(driver.findElement(By.id("difficulty")).getAttribute("readonly"),"true");

        click(By.id("nav-browse"));
        click(By.id("view-questions-link"));
        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys(referenceName);

        findInTable("Name",referenceName).click();
        click(By.linkText("View Selected"));
        String questionContents = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div")).getAttribute("innerHTML");
        assertTrue(questionContents.contains("<p><em><u>"+questionText+"</u></em></p>"));
        assertTrue(questionContents.replace("&nbsp;", " ").contains(markingGuide));
    }

    private void multiChoiceMark(int mark, int letter) {
        click(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr["+(mark + 1)+"]/td[2]/input["+letter+"]"));
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
        wait.until(ExpectedConditions.elementToBeClickable(By.id("alert")));
        assertTrue(driver.findElement(By.id("alert")).getAttribute("class").contains("success"));
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

    private void typeInFroala(By findBy, String keys) {
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
