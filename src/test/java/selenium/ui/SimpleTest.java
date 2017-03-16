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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.ac.ncl.daniel.baranowski.DissertationApplication;

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

        assertTrue(actual.contains(
                "    \n" +
                "    \n" +
                "    \n" +
                "        <div>\n" +
                "    <h1>Review Exam</h1>\n" +
                "    <b><div>CSC001</div></b>\n" +
                "    <div>Exam Date: 22/02/2017</div>\n" +
                "    <div>Status: FINISHED</div>\n" +
                "    <div>Start Time: 12:00 PM</div>\n" +
                "    <div>End Time: 2:30 PM</div>\n" +
                "    <div>Paper: <a href=\"/test-paper/1/1/view\">ALTERNATE INTERVIEW JAVA</a></div>\n" +
                "\n" +
                "    <table class=\"table table-striped\">\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th>Name</th>\n" +
                "                <th>Status</th>\n" +
                "                <th>Exam Login</th>\n" +
                "                <th>Exam Password</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody>\n" +
                "            \n" +
                "        </tbody>\n" +
                "    </table>\n" +
                "\n" +
                "    \n" +
                "\n" +
                "    \n" +
                "\n" +
                "    \n" +
                "    <br>\n" +
                "    \n" +
                "    <br>\n" +
                "    <a href=\"/exam/mark/1\">Mark Exam</a>\n" +
                "</div>\n" +
                "    \n" +
                "    \n" +
                "\n"));
    }

    @Test
    public void A5canCreateQuestions() throws InterruptedException {
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
    }

    @Test
    public void A6canEditQuestions() {
        loginAs("sampleAll","pass", "Jack Brown");

        openEditorForQuestion("Name","Selenium Multiple Choice Question 1");
        typeInFroala(By.xpath("//*[@id=\"form-group-text\"]/div[1]/div[3]/div/p"), "\nA) This answer gives you 1 Mark\nB) This answer gives you 2 Marks\nC)This answer gives you 0 marksD)This answer gives you 4 marks.");
        click(By.id("js-rebuild-wizard"));
        multiChoiceMark(0,3);
        multiChoiceMark(1,1);
        multiChoiceMark(2,2);
        multiChoiceMark(4,4);
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

        waitForSuccessAlert();
    }

    @Test
    public void A7canCreateSection() {
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

        String actual = driver.findElement(By.xpath("//*[@id=\"sectionQuestions\"]/tbody")).getAttribute("innerHTML");

        assertTrue(actual.contains("<a href=\"/test-paper/view-question/42/1\">View</a>"));
        assertTrue(actual.contains("<a href=\"/test-paper/view-question/43/1\">View</a>"));
        assertTrue(actual.contains("<a href=\"/test-paper/view-question/44/1\">View</a>"));
        assertTrue(actual.contains("<a href=\"/test-paper/view-question/45/1\">View</a>"));
        assertTrue(actual.contains("<a href=\"/test-paper/view-question/46/1\">View</a>"));

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
        assertTrue(actualQuestion3.contains(
                "    <div class=\"questionHeader\">\n" +
                "        <p>Question 1.3</p>\n" +
                "    </div>\n" +
                "\n" +
                "    <b><p>(1 minutes)</p></b>\n" +
                "\n" +
                "    <p id=\"question-text-for-44-1\"></p><p><em><u>This question was designed by selenium</u></em></p><p><em><u>A) This answer gives you 1 Mark</u></em></p><p><em><u>B) This answer gives you 2 Marks</u></em></p><p><em><u>C)This answer gives you 0 marksD)This answer gives you 4 marks.</u></em></p><p></p>\n" +
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
                "        <div class=\"title collapsed\" data-toggle=\"collapse\" data-target=\"#panel-answer-section3\" aria-expanded=\"false\">\n" +
                "            <a tabindex=\"0\">Marking Guide:</a>\n" +
                "        </div>\n" +
                "\n" +
                "        <div class=\"panel-answer-section collapsible collapse\" id=\"panel-answer-section3\" aria-expanded=\"false\">\n" +
                "            <div style=\"resize: none;\"><p>Sample marking guide.</p></div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "                    \n" +
                "                </div>\n"));

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
    }

    @Test
    public void A8canCreatePaper() {
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
    }

    @Test
    public void A8canSetupAnExam() {
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
    }

    @Test
    public void A9canStartAnExam() {
        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.linkText("Exams"));

        findInTable("Test Paper", "Selenium Test Paper").click();
        click(By.linkText("View Selected"));

        click(By.xpath("//*[@id=\"loadedContent\"]/div/form/input[1]"));
        assertTrue(getInnerHTML(By.xpath("//*[@id=\"dashboardWindow\"]")).contains("Status: Started"));
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
    public void B1canTakeExam() {
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
        click(By.xpath("//*[@id=\"submitAllBtn\"]"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]")));
        click(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]"));

        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/finish-page"));
        click(By.id("buttonID"));
    }

    @Test
    public void B2CanFinnishExam() {
        loginAs("sampleAll","pass", "Jack Brown");
        click(By.id("nav-browse"));
        click(By.linkText("Exams"));

        findInTable("Test Paper", "Selenium Test Paper").click();
        click(By.linkText("View Selected"));
        click(By.xpath("//*[@id=\"loadedContent\"]/div/form/input[1]"));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("//*[@id=\"loadedContent\"]/div/form/input[1]"))));
        click(By.xpath("//*[@id=\"loadedContent\"]/div/a"));
        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/exam/mark/2"));
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
        assertTrue(questionContents.contains(markingGuide));
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

}
