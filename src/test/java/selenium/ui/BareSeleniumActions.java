package selenium.ui;

import org.joda.time.LocalTime;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import static uk.ac.ncl.daniel.baranowski.common.Constants.TIME_PATTERN;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.PAPER_PREFIX;
import static uk.ac.ncl.daniel.baranowski.common.ControllerEndpoints.PAPER_SECTION_EDITOR;

public class BareSeleniumActions {
    private WebDriver driver;
    private String baseUrl;
    private WebDriverWait wait;
    Actions action;
    JdbcTemplate jdbc;

    public BareSeleniumActions(String baseUrl, JdbcTemplate jdbc) {
        this.baseUrl = baseUrl;
        this.jdbc = jdbc;
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\chromedriver.exe");
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        this.wait = new WebDriverWait(driver, 500);
        this.action = new Actions(driver);
    }

    public void A1viewingQuestionsWorksCorrectly() throws Exception {
        
        click(By.id("nav-browse"));
        click(By.id("BrowseQuestions"));

        findInTable("Id", "1").click();
        
        driver.navigate().back();
        click(By.cssSelector("a[data-dt-idx='3']"));
        findInTable("Id", "40").click();
    }

    public void A2viewingSectionsWorksCorrectly() throws Exception {
        
        click(By.id("nav-browse"));
        click(By.id("BrowseSections"));

        findInTable("Id", "2").click();


        WebElement nextQuestionBtn = driver.findElement(By.id("nextQuestion"));
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
        nextQuestionBtn.click();
    }


    public void A3viewingPapersWorksCorrectly() {
        click(By.id("nav-browse"));
        click(By.id("BrowsePapers"));

        findInTable("Id", "2").click();

        WebElement testCaseDesignSection = driver.findElement(By.linkText("5: Test Case Design"));
        testCaseDesignSection.isDisplayed();
        testCaseDesignSection.click();

        driver.findElement(By.id("nextQuestion")).click();

        String actual = driver.findElement(By.className("slick-active")).getAttribute("innerHTML");

    }

    public void A4viewingExamsWorksCorrectly() {
        click(By.id("nav-browse"));
        click(By.id("BrowseExams"));

        findInTable("id", "1").click();

        String actual = driver.findElement(By.id("loadedContent")).getAttribute("innerHTML");
    }


    public void A5canCreateQuestions() throws InterruptedException, SQLException {
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
    }

    public void A6canEditQuestions() throws SQLException {
        

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
    }

    private void waitToLoad() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
    }

    public void A7canCreateSection() throws SQLException {
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
        
        click(By.id("nav-browse"));
        click(By.id("BrowseSections"));
        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys("Selenium Section");

        findInTable("Name","Selenium Section").click();

        waitToLoad();
        

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
        
        click(By.xpath("//*[@id=\"8\"]/div[4]"));
        String actualQuestion4 = driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]")).getAttribute("innerHTML");

        click(By.xpath("//*[@id=\"8\"]/div[5]"));
        String actualQuestion5 = driver.findElement(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]")).getAttribute("innerHTML");
    }

    public void A8canCreatePaper() throws SQLException {
        
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
    }

    private void select2Type(By by ,String choice) {
        click(by);
        Actions actions = new Actions(driver);
        actions.sendKeys(choice);
        actions.sendKeys(Keys.ENTER);
        actions.build().perform();
    }

    public void A8canSetupAnExam() throws SQLException {
        
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
        
        
    }

    public void A9canStartAnExam() throws SQLException {
        
        click(By.id("nav-browse"));
        click(By.id("BrowseExams"));

        findInTable("Test Paper", "Selenium Test Paper").click();

        click(By.xpath("//*[@id=\"loadedContent\"]/div/form/input[1]"));
        
    }

    private void loginToTakeExams(int userNo) {
        
        click(By.id("nav-browse"));
        click(By.id("BrowseExams"));

        findInTable("Test Paper", "Selenium Test Paper").click();

        String userLogin = driver.findElement(By.xpath("//*[@id=\"loadedContent\"]/div/table/tbody/tr["+userNo+"]/td[3]")).getText();
        String userPass  = driver.findElement(By.xpath("//*[@id=\"loadedContent\"]/div/table/tbody/tr["+userNo+"]/td[4]")).getText();
        click(By.xpath("//*[@id=\"loadedContent\"]/div/a"));


        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/2/login"));

        driver.findElement(By.xpath("//*[@id=\"inputUsername\"]")).sendKeys(userLogin);
        driver.findElement(By.xpath("//*[@id=\"inputPassword\"]")).sendKeys(userPass);
        click(By.xpath("//*[@id=\"main-login-btn\"]"));

        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/"+userNo+"/start"));
        String html = getInnerHTML(By.xpath("/html/body/div[1]/div[2]/div/div/div"));
        
        click(By.xpath("//*[@id=\"checkBoxID\"]"));
        click(By.xpath("//*[@id=\"buttonID\"]"));
        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/ongoing"));
    }


    public void B1canTakeExam() throws SQLException {
        loginToTakeExams(1);

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));
        

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/div/div/div"), "Sample code Example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/input[4]"));
        

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/textarea"), "Sample essay example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/input[4]"));
        


        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/div[4]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/input[4]"));
        

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[4]/input")));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[1]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[2]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[3]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[4]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/input[4]"));

        

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[2]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[3]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/input[4]"));

        

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
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/div/div/input"),"Java");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/input[5]"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/div/div/input"), "Hello World");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/input[5]"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/div/div/input"), "S.W.A.T");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/input[5]"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/div/div/input"), "GoOGle");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/input[5]"));
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/div/div/input"), "F.E.A.R iS A GoOD   Game");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/input[5]"));
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
        

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/div/div/div"), "Sample code Example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/input[4]"));

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/textarea"), "Sample essay example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/input[4]"));


        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/div[2]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/input[4]"));

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[4]/input")));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[1]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[2]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[3]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/input[4]"));

        

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[2]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[3]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/input[4]"));
        
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

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/div/div/input"),"Java");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/input[5]"));

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/div/div/input"), "Hello                     World");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/input[5]"));

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/div/div/input"), "S,W,A,T");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/input[5]"));

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/div/div/input"), "google");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/input[5]"));

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/div/div/input"), "F E A R is a good GAME");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/input[5]"));

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

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/div/div/div"), "Sample code Example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/input[4]"));

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/textarea"), "Sample essay example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/input[4]"));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/div[1]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/input[4]"));

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[4]/input")));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[1]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[2]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/input[4]"));
        
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[3]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[4]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/input[4]"));

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

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/div/div/input"),"C#");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/input[5]"));

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/div/div/input"), "Hi Planet");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/input[5]"));

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/div/div/input"), "F.E.A.R");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/input[5]"));

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/div/div/input"), "TwiTtEr");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/input[5]"));

        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/div/div/input"), "Random");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/input[5]"));

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
        

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/div/div/div"), "Sample code Example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[3]/form/input[4]"));
        

        typeInFroala(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/textarea"), "Sample essay example");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[4]/form/input[4]"));


        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/div[3]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[5]/form/input[4]"));
        

        moveToBottomOfPage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[4]/input")));

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/div[1]/input"));
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[6]/form/input[4]"));

        

        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[1]/input"));
        click(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/div[2]/input"));

        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[7]/form/input[4]"));

        

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

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/div/div/input"),"C#");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/input[5]"));

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/div/div/input"), "Hi         Planet");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/input[5]"));

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/div/div/input"), "F|E|A|R");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/input[5]"));

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/div/div/input"), "twitter");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/input[5]"));

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/div/div/input"), "Random");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/input[5]"));

        
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

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/div/div/input"),"Plain Wrong answer");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[9]/form/input[5]"));

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/div/div/input"), "  ");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[10]/form/input[5]"));

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/div/div/input"), "dsa");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[11]/form/input[5]"));

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/div/div/input"), "twitterr");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[12]/form/input[5]"));

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/div/div/input"), "RAndom");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[13]/form/input[5]"));

        
        clearType(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/div/div/input"), "cheatSheett");
        submitAnswer(By.xpath("//*[@id=\"carousell\"]/div/div/div[14]/form/input[5]"));

        click(By.xpath("//*[@id=\"submitAllBtn\"]"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]")));
        click(By.xpath("//*[@id=\"areyousure\"]/div/div/div[3]/button[2]"));

        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/test-attempt/finish-page"));
        click(By.id("buttonID"));
    }

    public void B2CanFinnishExam() throws SQLException {
        
        click(By.id("nav-browse"));
        click(By.id("BrowseExams"));

        findInTable("Test Paper", "Selenium Test Paper").click();

        click(By.xpath("//*[@id=\"loadedContent\"]/div/form/input[1]"));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"loadedContent\"]/div/a"))));
        
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

        
    }

    public void B3CanMarkExam() throws SQLException {
        
        click(By.id("nav-browse"));
        click(By.id("BrowseExams"));

        findInTable("Test Paper", "Selenium Test Paper").click();


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

        
        
        
        
        

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        
        
        
        
        

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        
        
        
        
        

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        markQuestion(6, 47, 1, 10);
        markQuestion(6, 47, 2, 10);
        markQuestion(6, 47, 3, 10);
        markQuestion(6, 47, 4, 10);
        markQuestion(6, 47, 5, 10);

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        
        
        
        
        

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        
        
        
        
        

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        
        
        
        
        

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        
        
        
        
        

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        
        
        
        
        

        click(By.xpath("//*[@id=\"nextQuestion\"]/p"));

        
        
        
        
        

        click(By.xpath("//*[@id=\"submitAllBtn\"]"));
        wait.until(ExpectedConditions.urlMatches("https://localhost?(:\\d\\d\\d\\d)/dashboard/generate-test"));
    }

    public void B4MultiChoiceWizardRegenerates() throws SQLException {
        
        click(By.id("nav-browse"));
        click(By.id("BrowseQuestions"));

        clearType(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input"), "Selenium Multiple Choice Question 3");
        findInTable("Name","Selenium Multiple Choice Question 3").findElement(By.className("glyphicon-pencil")).click();

        
        

        
        

        
        

        
        
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

        
        
        

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loading")));
        click(By.id("nav-browse"));
        click(By.id("BrowseQuestions"));
        driver.findElement(By.xpath("//*[@id=\"viewListTable_filter\"]/label/input")).sendKeys(referenceName);

        findInTable("Name",referenceName).click();

        String questionContents = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div")).getAttribute("innerHTML");
        


        click(By.linkText("View as Marker"));
        click(By.linkText("Marking Guide:"));

        String markingGuideContents = driver.findElement(By.id("panel-answer-section1")).getAttribute("innerHTML");
        
    }

    private void multiChoiceMark(int mark, int letter) {
        click(By.xpath("//*[@id=\"form-group-correctAnswer\"]/div[1]/table/tbody/tr["+(mark)+"]/td[2]/input["+letter+"]"));
    }

    private void waitForSuccessAlert() {
        WebDriverWait wait = new WebDriverWait(driver, 5000);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("noty_type_success")));
    }

    public void loginAs(String userName, String password, String expectedName) {
        driver.get(baseUrl + "/login");
        WebElement loginBox = driver.findElement(By.id("inputUsername"));
        WebElement passwordBox = driver.findElement(By.id("inputPassword"));
        WebElement loginBtn = driver.findElement(By.id("main-login-btn"));
        loginBox.sendKeys(userName);
        passwordBox.sendKeys(password);
        loginBtn.click();
        
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
