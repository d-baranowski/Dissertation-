package tests;


import uk.ac.ncl.daniel.baranowski.data.access.pojos.Answer;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.AnswerAsset;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Candidate;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Mark;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Paper;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.PaperVersion;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Question;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersion;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.QuestionVersionAsset;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Role;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.Section;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.SectionVersion;
import uk.ac.ncl.daniel.baranowski.data.access.pojos.User;
import uk.ac.ncl.daniel.baranowski.models.AnswerModel;
import uk.ac.ncl.daniel.baranowski.models.AnswersMapModel;
import uk.ac.ncl.daniel.baranowski.models.AssetModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptModel;
import uk.ac.ncl.daniel.baranowski.models.AttemptReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.CandidateModel;
import uk.ac.ncl.daniel.baranowski.models.MarkModel;
import uk.ac.ncl.daniel.baranowski.models.PaperModel;
import uk.ac.ncl.daniel.baranowski.models.PaperReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionModel;
import uk.ac.ncl.daniel.baranowski.models.QuestionReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.SectionModel;
import uk.ac.ncl.daniel.baranowski.models.SectionReferenceModel;
import uk.ac.ncl.daniel.baranowski.models.TestDayModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TestResources {
    /**
     * Candidate resources
     **/
    static final Candidate CANDIDATE_ID_1;

    /**
     * Question resources
     **/
    static final Question SAMPLE_QUESTION;
    static final Question QUESTION_ID_1;

    /**
     * Question version resources
     **/
    static final QuestionVersion QUESTION_VERSION_SAMPLE;
    static final QuestionVersion QUESTION_VERSION_ID_1_VERNO_1;
    static final QuestionVersion QUESTION_VERSION_ID_30_VERNO_1;
    static final QuestionVersion QUESTION_VERSION_ID_31_VERNO_1;

    /**
     * Section resources
     **/
    static final Section SECTION_SAMPLE;
    static final Section SECTION_ID_1;

    /**
     * Section Version Resources
     **/
    static final SectionVersion SECTION_VERSION_SAMPLE;
    static final SectionVersion SECTION_VERSION_ID_1_VERNO_1;

    /**
     * Paper resources
     **/
    static final Paper PAPER_ID_1;
    private static final Paper PAPER_ID_2;

    /**
     * PaperVersion resources
     **/
    static final PaperVersion PAPER_VERSION_ID_1_VERNO_1;
    static final PaperVersion PAPER_VERSION_ID_2_VERNO_1;

    /**
     * User Roles resources
     **/
    static final Role ROLE_ADMIN = new Role("Admin");
    static final Role ROLE_MARKER = new Role("Marker");
    static final Role ROLE_AUTHOR = new Role("Author");

    /**
     * User resources
     **/
    static final User USER_MARKER = new User.Builder()
            .setId("3ca33b4f-009a-4403-829b-e2d20b3d47c2")
            .setLogin("sampleMarker")
            .setPassword("pass")
            .setName("Bob")
            .setSurname("Smith").build();

    static final User USER_ADMIN = new User.Builder()
            .setId("94cbbbc4-f94d-40d2-b0cf-e642eb36e73a")
            .setLogin("sampleAdmin")
            .setPassword("pass")
            .setName("Sam")
            .setSurname("Armstrong").build();

    static final User USER_AUTHOR = new User.Builder()
            .setId("fba6a561-8999-4b19-9c57-232895d024c6")
            .setLogin("sampleAuthor")
            .setPassword("pass")
            .setName("Grzegorz")
            .setSurname("Brzenczyszczykiewicz").build();

    static final User USER_ALL = new User.Builder()
            .setId("9f4db0ac-b18a-4777-8b04-b72a0eeccf5d")
            .setLogin("sampleAll")
            .setPassword("pass")
            .setName("Jack")
            .setSurname("Brown").build();

    /**
     * QuestionVersionAsset Resources
     **/
    static final QuestionVersionAsset QUESTION_VERSION_ASSET_ID_1;

    /**
     * AnswerAsset resources
     **/
    static final AnswerAsset ANSWER_ASSET_EXAMPLE;

    /**
     * Asset Models
     **/
    static final AssetModel QUESTION_ASSET_MODEL_ID_1;

    /**
     * QuestionReferenceModels
     */
    static final QuestionReferenceModel QUESTION_REFERENCE_MODEL_ID_1_VER_1;

    /**
     * QuestionModel resources
     **/
    static final QuestionModel QUESTION_MODEL_ID_1_VER_1;
    private static final QuestionModel QUESTION_MODEL_ID_15;
    private static final QuestionModel QUESTION_MODEL_ID_16;
    private static final QuestionModel QUESTION_MODEL_ID_17;
    private static final QuestionModel QUESTION_MODEL_ID_18;
    private static final QuestionModel QUESTION_MODEL_ID_19;
    private static final QuestionModel QUESTION_MODEL_ID_20;
    private static final QuestionModel QUESTION_MODEL_ID_21;
    private static final QuestionModel QUESTION_MODEL_ID_22;
    private static final QuestionModel QUESTION_MODEL_ID_23;
    private static final QuestionModel QUESTION_MODEL_ID_24;
    private static final QuestionModel QUESTION_MODEL_ID_25;
    private static final QuestionModel QUESTION_MODEL_ID_26;
    private static final QuestionModel QUESTION_MODEL_ID_27;
    private static final QuestionModel QUESTION_MODEL_ID_28;
    private static final QuestionModel QUESTION_MODEL_ID_29;
    private static final QuestionModel QUESTION_MODEL_ID_30;
    private static final QuestionModel QUESTION_MODEL_ID_31;

    /**
     * SectionReferenceModel resources
     **/
    static final SectionReferenceModel SECTION_REFERENCE_MODEL_ID_1;
    static final SectionReferenceModel SECTION_REFERENCE_MODEL_ID_2;
    static final SectionReferenceModel SECTION_REFERENCE_MODEL_ID_3;
    static final SectionReferenceModel SECTION_REFERENCE_MODEL_ID_4;
    static final SectionReferenceModel SECTION_REFERENCE_MODEL_ID_5;
    static final SectionReferenceModel SECTION_REFERENCE_MODEL_ID_6;

    /**
     * Section Questions resources
     **/
    static final Map<Integer, QuestionModel> SECTION_QUESTIONS_FOR_ID_1_VER_1;
    private static final Map<Integer, QuestionModel> SECTION_QUESTIONS_FOR_ID_2_VER_1;
    private static final Map<Integer, QuestionModel> SECTION_QUESTIONS_FOR_ID_3_VER_1;
    private static final Map<Integer, QuestionModel> SECTION_QUESTIONS_FOR_ID_4_VER_1;
    private static final Map<Integer, QuestionModel> SECTION_QUESTIONS_FOR_ID_5_VER_1;
    private static final Map<Integer, QuestionModel> SECTION_QUESTIONS_FOR_ID_6_VER_1;

    /**
     * SectionModel resources
     **/
    private static final SectionModel SECTION_MODEL_SAMPLE;
    private static final SectionModel SECTION_MODEL_ID_1;
    private static final SectionModel SECTION_MODEL_ID_2;
    private static final SectionModel SECTION_MODEL_ID_3;
    private static final SectionModel SECTION_MODEL_ID_4;
    private static final SectionModel SECTION_MODEL_ID_5;
    private static final SectionModel SECTION_MODEL_ID_6;

    /**
     * Paper Sections
     **/
    static final Map<Integer, SectionModel> PAPER_SECTIONS_FOR_PAPER_MODEL_ID_1;
    private static final Map<Integer, SectionModel> PAPER_SECTIONS_FOR_PAPER_MODEL_ID_2;

    /**
     * Answer resources
     */
    static final Answer ANSWER_ID_1;

    /**
     * AnswerModel resources
     **/
    static final AnswerModel ANSWER_MODEL_ID_1;

    /**
     * AnswersMapModel resources
     **/
    private static final AnswersMapModel ANSWERS_FOR_ATTEMPT_MODEL_ID_1;
    private static final AnswersMapModel ANSWERS_FOR_ATTEMPT_MODEL_ID_2;

    /**
     * CandidateModel resources
     **/
    static final CandidateModel CANDIDATE_MODEL_ID_1;

    /**
     * PaperReferenceModel resources
     **/
    static final PaperReferenceModel PAPER_REFERENCE_MODEL_ID_1;
    static final PaperReferenceModel PAPER_REFERENCE_MODEL_ID_2;

    /**
     * PaperModel resources
     **/
    static final PaperModel PAPER_MODEL_ID_1_VER_1;
    static final PaperModel PAPER_MODEL_ID_2_VER_1;

    /**
     * TestDayModel resources
     **/
    static final TestDayModel SAMPLE_DAY;

    /**
     * AttemptModel resources
     **/
    static final AttemptModel TEST_ATTEMPT_MODEL_ID_1;
    static final AttemptModel TEST_ATTEMPT_MODEL_ID_2;

    /**
     * AttemptReferenceModel resources
     **/
    static final AttemptReferenceModel TEST_ATTEMPT_REFERENCE_MODEL_ID_1;
    static final AttemptReferenceModel TEST_ATTEMPT_REFERENCE_MODEL_ID_2;

    /**
     * Mark resources
     **/
    static final Mark SAMPLE_MARK;

    /**
     * MarkModel resources
     */

    static final MarkModel SAMPLE_MARK_MODEL;

    static {
        /** Candidate resources **/
        CANDIDATE_ID_1 = new Candidate.Builder()
                .setId(1)
                .setName("Agent")
                .setSurname("Smith")
                .build();

        /** Question resources **/
        SAMPLE_QUESTION = new Question.Builder()
                .setId(99)
                .setLanguage("Ada")
                .setReferenceName("Sample")
                .setType("Code")
                .setDifficulty(99)
                .build();

        QUESTION_ID_1 = new Question.Builder()
                .setId(1)
                .setLanguage("Java")
                .setReferenceName("Develop isEven()")
                .setType("Code")
                .setDifficulty(5)
                .build();


        /** Question version resources **/
        QUESTION_VERSION_SAMPLE = new QuestionVersion.Builder()
                .setQuestionId(99)
                .setVersionNo(99)
                .setCorrectAnswer("Kopytka")
                .setMarkingGuide("Sample sample sample")
                .setText("Lorem ipsium dolore...")
                .setTimeScale(99)
                .build();


        QUESTION_VERSION_ID_1_VERNO_1 = new QuestionVersion.Builder()
                .setText("You have been asked to develop a function called IsEven that return true if a given integer parameter is even, or false if odd. Write this function below.")
                .setTimeScale(5)
                .setQuestionId(1)
                .setVersionNo(1)
                .build();

        QUESTION_VERSION_ID_30_VERNO_1 = new QuestionVersion.Builder()
                .setText("The following code checks the validity of a date (which is passed in as 2 integer parameters). The code is looking to check the validity of all the days of the year, design the test data necessary to fully test this code. Note you do not need to consider leap years in your answer. <pre class=\"prettyprint\">public class DateValidator<br />{<br />&nbsp;&nbsp; &nbsp;private static int daysInMonth [12] =&nbsp; {31,28,31,30,31,30,31,31,30,31,30,31};<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;public static bool validate(int day, int month)<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;{&nbsp;&nbsp;&nbsp; &nbsp;<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; if (month&gt;=1 &amp;&amp; month &lt;= 12 &amp;&amp; day &gt;=1 &amp;&amp; day &lt;=daysInMonth[month-1]) <br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return true:<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; else {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; return false;<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp; &nbsp;}<br />}</pre>")
                .setTimeScale(5)
                .setQuestionId(30)
                .setVersionNo(1)
                .setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.")
                .build();

        QUESTION_VERSION_ID_31_VERNO_1 = new QuestionVersion.Builder()
                .setText("What tests can be executed against a web site that has static pages?  For example, spell checking text or verifying image downloads.")
                .setTimeScale(5)
                .setQuestionId(31)
                .setVersionNo(1)
                .setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.")
                .build();

        /** Section Resources **/
        SECTION_SAMPLE = new Section.Builder()
                .setId(99)
                .setReferenceName("Kopytka")
                .build();

        SECTION_ID_1 = new Section.Builder()
                .setId(1)
                .setReferenceName("ALTERNATE INTERVIEW JAVA QUESTIONS")
                .build();

        /** Section Version Resources **/
        SECTION_VERSION_SAMPLE = new SectionVersion.Builder()
                .setSectionId(99)
                .setVersionNo(99)
                .setDescription("Lorem Ipsium Dolore")
                .setNoOfQuestionsToAnswer(99)
                .setTimeScale(99)
                .build();

        SECTION_VERSION_ID_1_VERNO_1 = new SectionVersion.Builder()
                .setNoOfQuestionsToAnswer(14)
                .setTimeScale(60)
                .setSectionId(1)
                .setVersionNo(1)
                .build();

        /** Paper resources **/
        PAPER_ID_1 = new Paper.Builder()
                .setId(1)
                .setName("ALTERNATE INTERVIEW JAVA")
                .setTimeAllowed(60)
                .build();

        PAPER_ID_2 = new Paper.Builder()
                .setId(2)
                .setName("Interview Test-Graduate (C#)")
                .setTimeAllowed(60)
                .build();

        /** PaperVersion resources **/
        PAPER_VERSION_ID_1_VERNO_1 = new PaperVersion.Builder()
                .setAuthorId("9f4db0ac-b18a-4777-8b04-b72a0eeccf5d")
                .setPaperId(1)
                .setVersionNo(1)
                .setInstructionsText(
                        "<b>Normal time Allowed: Up to 1 Hour </br>"
                                + "(all times indicative only)</b></br>"
                                + "Your interviewer will idicate: </br>"
                                + "<ul>"
                                + "<li> which questions of this set you should complete."
                                + " The selected set will be relvant to your knowledge and experience</li>"
                                + "<li>the time that you have to complete these in.</li>"
                                + "</ul>"
                                + "</br>"
                                + "The quelity / correctness of your answers is more important than the ammount of "
                                + "quetions answered. A timescale is provided with each question as a rough guide for how"
                                + " long the question should take to complete.")
                .build();

        PAPER_VERSION_ID_2_VERNO_1 = new PaperVersion.Builder()
                .setAuthorId("9f4db0ac-b18a-4777-8b04-b72a0eeccf5d")
                .setPaperId(2)
                .setVersionNo(1)
                .setInstructionsText(
                        "<b>Normal time Allowed: Up to 60 Minutes </br>"
                                + "(all times indicative only)</b></br>"
                                + "Answer as many questions as you can. If you are unsure,"
                                + " it is better to skip the question completely rather than provide an incorrect answer."
                                + " An indicative time is provided with each question as a rough guide for how long the question should"
                                + " take to complete.")
                .build();

        /** AnswerAsset Resources **/
        ANSWER_ASSET_EXAMPLE = new AnswerAsset.Builder()
                .setId(99)
                .setQuestionId(99)
                .setTestDayEntryId(99)
                .setFile(new byte[99])
                .setFileType("png")
                .setQuestionVersionNo(99)
                .setReferenceName("This is an example which is not in data.sql")
                .build();

        /** QuestionVersionAsset Resources **/
        QUESTION_VERSION_ASSET_ID_1 = new QuestionVersionAsset.Builder()
                .setId(1)
                .setQuestionId(28)
                .setQuestionVersionNo(1)
                .setReferenceName("Login Box")
                .setFileType("png")
                .setFile(new byte[35842]) //Equals method only compares size
                .build();


        /** Asset Models **/
        QUESTION_ASSET_MODEL_ID_1 = new AssetModel();
        QUESTION_ASSET_MODEL_ID_1.setReferenceName(QUESTION_VERSION_ASSET_ID_1.getReferenceName());
        QUESTION_ASSET_MODEL_ID_1.setId(QUESTION_VERSION_ASSET_ID_1.getId());
        QUESTION_ASSET_MODEL_ID_1.setFileType(QUESTION_VERSION_ASSET_ID_1.getFileType());
        QUESTION_ASSET_MODEL_ID_1.setFile(QUESTION_VERSION_ASSET_ID_1.getFile());

        /** QuestionReferenceModel resources **/
        QUESTION_REFERENCE_MODEL_ID_1_VER_1 = new QuestionReferenceModel();
        QUESTION_REFERENCE_MODEL_ID_1_VER_1.setId(QUESTION_ID_1.getId());
        QUESTION_REFERENCE_MODEL_ID_1_VER_1.setVersionNo(QUESTION_VERSION_ID_1_VERNO_1.getVersionNo());
        QUESTION_REFERENCE_MODEL_ID_1_VER_1.setDifficulty(QUESTION_ID_1.getDifficulty());
        QUESTION_REFERENCE_MODEL_ID_1_VER_1.setTimeScale(QUESTION_VERSION_ID_1_VERNO_1.getTimeScale());
        QUESTION_REFERENCE_MODEL_ID_1_VER_1.setType(QUESTION_ID_1.getType());
        QUESTION_REFERENCE_MODEL_ID_1_VER_1.setLanguage(QUESTION_ID_1.getLanguage());
        QUESTION_REFERENCE_MODEL_ID_1_VER_1.setReferenceName(QUESTION_ID_1.getReferenceName());

        /** QuestionModel resources **/
        QUESTION_MODEL_ID_1_VER_1 = new QuestionModel();
        QUESTION_MODEL_ID_1_VER_1.setId(QUESTION_ID_1.getId());
        QUESTION_MODEL_ID_1_VER_1.setVersionNo(QUESTION_VERSION_ID_1_VERNO_1.getVersionNo());
        QUESTION_MODEL_ID_1_VER_1.setLanguage(QUESTION_ID_1.getLanguage());
        QUESTION_MODEL_ID_1_VER_1.setTimeScale(QUESTION_VERSION_ID_1_VERNO_1.getTimeScale());
        QUESTION_MODEL_ID_1_VER_1.setReferenceName(QUESTION_ID_1.getReferenceName());
        QUESTION_MODEL_ID_1_VER_1.setType(QUESTION_ID_1.getType());
        QUESTION_MODEL_ID_1_VER_1.setText(QUESTION_VERSION_ID_1_VERNO_1.getText());
        QUESTION_MODEL_ID_1_VER_1.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_1_VER_1.setDifficulty(QUESTION_ID_1.getDifficulty());

        QUESTION_MODEL_ID_15 = new QuestionModel();
        QUESTION_MODEL_ID_15.setId(15);
        QUESTION_MODEL_ID_15.setVersionNo(1);
        QUESTION_MODEL_ID_15.setLanguage("C#");
        QUESTION_MODEL_ID_15.setTimeScale(1);
        QUESTION_MODEL_ID_15.setReferenceName("Access a private variable");
        QUESTION_MODEL_ID_15.setType("Multiple Choice");
        QUESTION_MODEL_ID_15.setText("Which classes can access a variable declared as private? </br> A) All classes. </br> B) Within the body of the class that encloses the declaration. </br> C)Inheriting sub classes. </br> D) Classes in the same namespace.");
        QUESTION_MODEL_ID_15.setCorrectAnswer("B");
        QUESTION_MODEL_ID_15.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_15.setDifficulty(1);

        QUESTION_MODEL_ID_16 = new QuestionModel();
        QUESTION_MODEL_ID_16.setId(16);
        QUESTION_MODEL_ID_16.setVersionNo(1);
        QUESTION_MODEL_ID_16.setLanguage("C#");
        QUESTION_MODEL_ID_16.setTimeScale(1);
        QUESTION_MODEL_ID_16.setReferenceName("Access a variable withoud modifier");
        QUESTION_MODEL_ID_16.setType("Multiple Choice");
        QUESTION_MODEL_ID_16.setText("Which classes can access a variable with no access modifier? </br> A) All classes.</br> B) Within the body of the class that encloses the declaration.</br> C) Inheriting sub classes</br> D) Classes in the same namespace.");
        QUESTION_MODEL_ID_16.setCorrectAnswer("B");
        QUESTION_MODEL_ID_16.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_16.setDifficulty(1);

        QUESTION_MODEL_ID_17 = new QuestionModel();
        QUESTION_MODEL_ID_17.setId(17);
        QUESTION_MODEL_ID_17.setVersionNo(1);
        QUESTION_MODEL_ID_17.setLanguage("C#");
        QUESTION_MODEL_ID_17.setTimeScale(1);
        QUESTION_MODEL_ID_17.setReferenceName("Truth about deadlocking");
        QUESTION_MODEL_ID_17.setType("Multiple Choice");
        QUESTION_MODEL_ID_17.setText("Which of the following statement is true about deadlocking? </br>A) It is not possible for more than two threads to deadlock at once.</br>B) Managed code language such as Java or C# guarantee that threads cannot enter a deadlocked state.</br>C) It is possible for a single threaded application to deadlock if synchronized blocks are used incorrectly.</br>D) None of the above.");
        QUESTION_MODEL_ID_17.setCorrectAnswer("C");
        QUESTION_MODEL_ID_17.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_17.setDifficulty(1);

        QUESTION_MODEL_ID_18 = new QuestionModel();
        QUESTION_MODEL_ID_18.setId(18);
        QUESTION_MODEL_ID_18.setVersionNo(1);
        QUESTION_MODEL_ID_18.setLanguage("C#");
        QUESTION_MODEL_ID_18.setTimeScale(1);
        QUESTION_MODEL_ID_18.setReferenceName("Storing key/value pairs");
        QUESTION_MODEL_ID_18.setType("Multiple Choice");
        QUESTION_MODEL_ID_18.setText("Which of the following C# objects is most suitable for storing general key/value pairs? </br>A) Dictionary</br>B) List</br>C) HashSet</br>D) IEnumerable");
        QUESTION_MODEL_ID_18.setCorrectAnswer("A");
        QUESTION_MODEL_ID_18.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_18.setDifficulty(1);

        QUESTION_MODEL_ID_19 = new QuestionModel();
        QUESTION_MODEL_ID_19.setId(19);
        QUESTION_MODEL_ID_19.setVersionNo(1);
        QUESTION_MODEL_ID_19.setLanguage("C#");
        QUESTION_MODEL_ID_19.setTimeScale(5);
        QUESTION_MODEL_ID_19.setReferenceName("Null reference exception");
        QUESTION_MODEL_ID_19.setType("Essay");
        QUESTION_MODEL_ID_19.setText("Given the following fragment of C# code what will be happen?</br><pre class=\"prettyprint\">bool flag = true;<br />Console.WriteLine( \"0\");<br />try {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( \"1\");<br />&nbsp;&nbsp;&nbsp; if (flag) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; object o = null;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; o.ToString();<br />&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( \"2\");<br />}<br />catch (NullReferenceException ex) {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( \"3\");<br />&nbsp;&nbsp;&nbsp; throw new ArgumentException( \"\", ex);<br />}<br />catch (Exception ex) {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( \"4\");<br />}<br />finally {<br />&nbsp;&nbsp;&nbsp; Console.WriteLine( \"5\");<br />}<br />Console.WriteLine( \"6\");</pre>");
        QUESTION_MODEL_ID_19.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_19.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_19.setDifficulty(5);

        QUESTION_MODEL_ID_20 = new QuestionModel();
        QUESTION_MODEL_ID_20.setId(20);
        QUESTION_MODEL_ID_20.setVersionNo(1);
        QUESTION_MODEL_ID_20.setLanguage("C#");
        QUESTION_MODEL_ID_20.setTimeScale(5);
        QUESTION_MODEL_ID_20.setReferenceName("Output of tester class");
        QUESTION_MODEL_ID_20.setType("Essay");
        QUESTION_MODEL_ID_20.setText("What will be the output of the following code?</br><pre class=\"prettyprint\">public class Tester {<br />&nbsp;&nbsp;&nbsp; private int t = 1;<br />&nbsp;&nbsp;&nbsp; private static int p = 1;<br /><br />&nbsp;&nbsp;&nbsp; static void main(string[]args) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; for (int counter = 0; counter &lt; 5; counter++) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Tester tester = new Tester();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; tester.test();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; public void test() {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine(\"T \" + t + \" \" + p);<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; t++;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; p++;<br />&nbsp;&nbsp;&nbsp; }<br />}</pre>");
        QUESTION_MODEL_ID_20.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_20.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_20.setDifficulty(5);

        QUESTION_MODEL_ID_21 = new QuestionModel();
        QUESTION_MODEL_ID_21.setId(21);
        QUESTION_MODEL_ID_21.setVersionNo(1);
        QUESTION_MODEL_ID_21.setLanguage("C#");
        QUESTION_MODEL_ID_21.setTimeScale(3);
        QUESTION_MODEL_ID_21.setReferenceName("Compilation error");
        QUESTION_MODEL_ID_21.setType("Essay");
        QUESTION_MODEL_ID_21.setText("What will happen when you attempt to compile and run the following code?<pre class=\"prettyprint\">public class StringHolder {<br /><br />&nbsp;&nbsp;&nbsp; public StringHolder(string value) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Value = value;<br />&nbsp;&nbsp;&nbsp; }<br /><br />&nbsp;&nbsp;&nbsp; public string Value {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; get;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; set;<br />&nbsp;&nbsp;&nbsp; }<br />}<br /><br />public class EqualityExample {<br /><br />&nbsp;&nbsp;&nbsp; static void Main(string[]args) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var s = new StringHolder(\"Marcus\");<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; var s2 = new StringHolder(\"Marcus\");<br /><br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; if (s == s2) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine(\"we have a match\");<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; } else {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine(\"not equal\");<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; }<br />}</pre>");
        QUESTION_MODEL_ID_21.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_21.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_21.setDifficulty(3);

        QUESTION_MODEL_ID_22 = new QuestionModel();
        QUESTION_MODEL_ID_22.setId(22);
        QUESTION_MODEL_ID_22.setVersionNo(1);
        QUESTION_MODEL_ID_22.setLanguage("C#");
        QUESTION_MODEL_ID_22.setTimeScale(5);
        QUESTION_MODEL_ID_22.setReferenceName("Compilation error");
        QUESTION_MODEL_ID_22.setType("Essay");
        QUESTION_MODEL_ID_22.setText("Given the following code what will be the output?<pre class=\"prettyprint\">public class Example {<br />&nbsp;&nbsp;&nbsp; public static void main(string[]args) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Example example = new Example();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; example.Method1();<br />&nbsp;&nbsp;&nbsp; }<br />&nbsp;&nbsp;&nbsp; public void Method1() {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; int i = 99;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ValueHolder vh&nbsp;&nbsp; = new ValueHolder();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; vh.i = 30;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Method2(vh, i);<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine(vh.i);<br />&nbsp;&nbsp;&nbsp; }<br /><br />&nbsp;&nbsp;&nbsp; public void Method2(ValueHolder v, int i) {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; i&nbsp;&nbsp; = 0;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; v.i = 20;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ValueHolder vh = new ValueHolder();<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; v&nbsp; = vh;<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Console.WriteLine(v.i + \" \" + i);<br />&nbsp;&nbsp;&nbsp; }<br /><br />&nbsp;&nbsp;&nbsp; class ValueHolder {<br />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; public int i = 10;<br />&nbsp;&nbsp;&nbsp; }<br /><br />}</pre>");
        QUESTION_MODEL_ID_22.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_22.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_22.setDifficulty(5);

        QUESTION_MODEL_ID_23 = new QuestionModel();
        QUESTION_MODEL_ID_23.setId(23);
        QUESTION_MODEL_ID_23.setVersionNo(1);
        QUESTION_MODEL_ID_23.setLanguage("C#");
        QUESTION_MODEL_ID_23.setTimeScale(10);
        QUESTION_MODEL_ID_23.setReferenceName("Common array elements");
        QUESTION_MODEL_ID_23.setType("Code");
        QUESTION_MODEL_ID_23.setText("You have two arrays:</br>int[] = new int[]{ 1, 2, 3, 5, 4 };</br>int[] = new int[]{ 3, 2, 9, 3, 7 };</br>Write a method that returns a collection of common elements.</br>Please note that the arrays can contain repeating elements, and are not in any particular order.</br>Complete the method in the space below. Any necessary variables should be shown.</br>Extra credit will be awarded for solutions that are efficient (let's say given very large input arrays).</br>Extra credit will be awarded if the method can handle any arbitrary number of arrays, i.e. a,b,c</br>");
        QUESTION_MODEL_ID_23.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_23.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_23.setDifficulty(10);

        QUESTION_MODEL_ID_24 = new QuestionModel();
        QUESTION_MODEL_ID_24.setId(24);
        QUESTION_MODEL_ID_24.setVersionNo(1);
        QUESTION_MODEL_ID_24.setLanguage("C#");
        QUESTION_MODEL_ID_24.setTimeScale(5);
        QUESTION_MODEL_ID_24.setReferenceName("Develop isOdd");
        QUESTION_MODEL_ID_24.setType("Code");
        QUESTION_MODEL_ID_24.setText("You have been asked to develop a function called IsOdd that returns true if a given integer parameter is odd, or false if even. Write this function below.");
        QUESTION_MODEL_ID_24.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_24.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_24.setDifficulty(5);

        QUESTION_MODEL_ID_25 = new QuestionModel();
        QUESTION_MODEL_ID_25.setId(25);
        QUESTION_MODEL_ID_25.setVersionNo(1);
        QUESTION_MODEL_ID_25.setLanguage("C#");
        QUESTION_MODEL_ID_25.setTimeScale(5);
        QUESTION_MODEL_ID_25.setReferenceName("Objected Orientated Design");
        QUESTION_MODEL_ID_25.setType("Drawing");
        QUESTION_MODEL_ID_25.setText("The classes shown in Error! Reference source not found. have been identified within the domain space for an internet electrical shopping site that deliver their goods in distinctive brightly coloured lorries and vans.Use object orientated theory to make the domain objects more efficient for software implementation.");
        QUESTION_MODEL_ID_25.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_25.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_25.setDifficulty(5);

        QUESTION_MODEL_ID_26 = new QuestionModel();
        QUESTION_MODEL_ID_26.setId(26);
        QUESTION_MODEL_ID_26.setVersionNo(1);
        QUESTION_MODEL_ID_26.setTimeScale(10);
        QUESTION_MODEL_ID_26.setReferenceName("Singleton pattern");
        QUESTION_MODEL_ID_26.setType("Essay");
        QUESTION_MODEL_ID_26.setText("a) This is the singleton pattern, what is it commonly used for?</br>b) If there are errors, fix them in the code snippet.</br>c) Please comment on any strengths or weaknesses of the above implementation. For instance, are there cases were it doesn't guarantee only one instance is created?</br><pre class=\"prettyprint\">class Singleton {<br />&nbsp;&nbsp; &nbsp;Singleton();<br /><br />&nbsp;&nbsp; &nbsp;private Singleton mInstance = null;<br /><br />&nbsp;&nbsp; &nbsp;public static Singleton instance() {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;if (mInstance == null) {<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;mInstance == new Singleton();<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;}<br />&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;return mInstance;<br />&nbsp;&nbsp; &nbsp;}<br />}</pre>");
        QUESTION_MODEL_ID_26.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_26.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_26.setDifficulty(10);

        QUESTION_MODEL_ID_27 = new QuestionModel();
        QUESTION_MODEL_ID_27.setId(27);
        QUESTION_MODEL_ID_27.setVersionNo(1);
        QUESTION_MODEL_ID_27.setTimeScale(10);
        QUESTION_MODEL_ID_27.setReferenceName("Sql products and invoices");
        QUESTION_MODEL_ID_27.setType("Essay");
        QUESTION_MODEL_ID_27.setText("The questions section are based on the schema definitions defined below.</br><pre class=\"prettyprint lang-sql\">CREATE TABLE Products(ProductId integer PRIMARY KEY, ProductName varchar(100));<br />CREATE TABLE Invoices(InvoiceNumber integer PRIMARY KEY, ProductId integer, InvoiceDate datetime, InvoiceCost decimal(6, 2)InvoiceComment varchar(200));</pre>a) What is the purpose of the following SQL statement:</br><pre class=\"prettyprint lang-sql\">SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId;</pre>b) The application programs using the tables allow a user to find an invoice by date and time range using a select statement of the form:</br><pre class=\"prettyprint lang-sql\">SELECT InvoiceNumber, InvoiceCost FROM Invoices WHERE InvoiceDate &gt;= &lsquo;2000/05/23 15:00:00&rsquo; AND InvoiceDate &lt; &lsquo;2000/05/23 16:00:00&rsquo;;</pre>c) However as the Invoices table grew larger the execution times of these queries increased.Describe a change to the Database schema that would decrease the query execution time.</br>d) A test has been written to validate the query from question 2.1. The pseudo-code is:<pre class=\"prettyprint lang-sql\">Connect to the database <br />FirstResult = Execute Query(&ldquo;SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId&rdquo;) <br />Add a new invoice &nbsp;<br />SecondResult = Execute Query(&ldquo;SELECT ProductId, SUM(InvoiceCost) FROM Invoices GROUP BY ProductId&rdquo;) <br />Check that FirstResult and SecondResult differ by the added amount</pre> </br>But on a shared database the test keeps failing because someone else was running the same test so the second query picked up two invoices, what can we do to avoid this problem?</br>e)Write a query to return the product Name, number of the product sold and the highest price paid for it?");
        QUESTION_MODEL_ID_27.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_27.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_27.setDifficulty(10);

        QUESTION_MODEL_ID_28 = new QuestionModel();
        QUESTION_MODEL_ID_28.setId(28);
        QUESTION_MODEL_ID_28.setVersionNo(1);
        QUESTION_MODEL_ID_28.setTimeScale(8);
        QUESTION_MODEL_ID_28.setReferenceName("Functional specification of a login dialog");
        QUESTION_MODEL_ID_28.setType("Essay");
        QUESTION_MODEL_ID_28.setText("Write a short functional specification of a typical login dialog (as seen in <b>Error! Reference source not found.</b>)");
       /* QUESTION_MODEL_ID_28.setAssets(singletonList(QUESTION_ASSET_MODEL_ID_1));*/
        QUESTION_MODEL_ID_28.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_28.setDifficulty(8);

        QUESTION_MODEL_ID_29 = new QuestionModel();
        QUESTION_MODEL_ID_29.setId(29);
        QUESTION_MODEL_ID_29.setVersionNo(1);
        QUESTION_MODEL_ID_29.setTimeScale(8);
        QUESTION_MODEL_ID_29.setReferenceName("Recursion on contents of array");
        QUESTION_MODEL_ID_29.setType("Code");
        QUESTION_MODEL_ID_29.setText("A) Write a short paragraph describing the concept of recursion.</br>B) Write a simple recursive method to print out the contents of an array</br>");
        QUESTION_MODEL_ID_29.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_29.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_29.setDifficulty(8);

        QUESTION_MODEL_ID_30 = new QuestionModel();
        QUESTION_MODEL_ID_30.setId(30);
        QUESTION_MODEL_ID_30.setVersionNo(1);
        QUESTION_MODEL_ID_30.setTimeScale(5);
        QUESTION_MODEL_ID_30.setReferenceName("Test date validity");
        QUESTION_MODEL_ID_30.setType("Code");
        QUESTION_MODEL_ID_30.setText(QUESTION_VERSION_ID_30_VERNO_1.getText());
        QUESTION_MODEL_ID_30.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_30.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_30.setDifficulty(5);

        QUESTION_MODEL_ID_31 = new QuestionModel();
        QUESTION_MODEL_ID_31.setId(31);
        QUESTION_MODEL_ID_31.setVersionNo(1);
        QUESTION_MODEL_ID_31.setTimeScale(5);
        QUESTION_MODEL_ID_31.setReferenceName("Test static website");
        QUESTION_MODEL_ID_31.setType("Essay");
        QUESTION_MODEL_ID_31.setText(QUESTION_VERSION_ID_31_VERNO_1.getText());
        QUESTION_MODEL_ID_31.setAssets(new ArrayList<>());
        QUESTION_MODEL_ID_31.setCorrectAnswer("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque nulla est, mattis vel felis et, malesuada feugiat est. Mauris malesuada ex erat, ut consectetur felis iaculis interdum. Vivamus tempor semper turpis at maximus. Phasellus eu egestas enim, non ornare eros. Vivamus auctor rhoncus eros vel sollicitudin. Fusce vel magna id lectus tristique euismod. Curabitur facilisis cursus posuere. Aenean congue ligula est, vitae pellentesque massa rutrum ut. Donec nec facilisis magna, in viverra eros. Praesent enim sapien, consectetur congue risus in, condimentum elementum erat. Nam et ullamcorper ante. Duis lobortis volutpat scelerisque.");
        QUESTION_MODEL_ID_31.setDifficulty(5);

        /** SectionReferenceModel resources **/
        SECTION_REFERENCE_MODEL_ID_1 = new SectionReferenceModel();
        SECTION_REFERENCE_MODEL_ID_1.setId(1);
        SECTION_REFERENCE_MODEL_ID_1.setVersionNumber(1);
        SECTION_REFERENCE_MODEL_ID_1.setReferenceName(SECTION_ID_1.getReferenceName());

        SECTION_REFERENCE_MODEL_ID_2 = new SectionReferenceModel();
        SECTION_REFERENCE_MODEL_ID_2.setId(2);
        SECTION_REFERENCE_MODEL_ID_2.setVersionNumber(1);
        SECTION_REFERENCE_MODEL_ID_2.setReferenceName("C# Language");

        SECTION_REFERENCE_MODEL_ID_3 = new SectionReferenceModel();
        SECTION_REFERENCE_MODEL_ID_3.setId(3);
        SECTION_REFERENCE_MODEL_ID_3.setVersionNumber(1);
        SECTION_REFERENCE_MODEL_ID_3.setReferenceName("Problem Solving");

        SECTION_REFERENCE_MODEL_ID_4 = new SectionReferenceModel();
        SECTION_REFERENCE_MODEL_ID_4.setId(4);
        SECTION_REFERENCE_MODEL_ID_4.setVersionNumber(1);
        SECTION_REFERENCE_MODEL_ID_4.setReferenceName("Architecture and Theory");

        SECTION_REFERENCE_MODEL_ID_5 = new SectionReferenceModel();
        SECTION_REFERENCE_MODEL_ID_5.setId(5);
        SECTION_REFERENCE_MODEL_ID_5.setVersionNumber(1);
        SECTION_REFERENCE_MODEL_ID_5.setReferenceName("Written Communication");

        SECTION_REFERENCE_MODEL_ID_6 = new SectionReferenceModel();
        SECTION_REFERENCE_MODEL_ID_6.setId(6);
        SECTION_REFERENCE_MODEL_ID_6.setVersionNumber(1);
        SECTION_REFERENCE_MODEL_ID_6.setReferenceName("Test Case Design");

        /** Section Questions resources **/
        SECTION_QUESTIONS_FOR_ID_1_VER_1 = new HashMap<>();
        SECTION_QUESTIONS_FOR_ID_1_VER_1.put(1, QUESTION_MODEL_ID_1_VER_1);

        SECTION_QUESTIONS_FOR_ID_2_VER_1 = new HashMap<>();
        SECTION_QUESTIONS_FOR_ID_2_VER_1.put(1, QUESTION_MODEL_ID_15);
        SECTION_QUESTIONS_FOR_ID_2_VER_1.put(2, QUESTION_MODEL_ID_16);
        SECTION_QUESTIONS_FOR_ID_2_VER_1.put(3, QUESTION_MODEL_ID_17);
        SECTION_QUESTIONS_FOR_ID_2_VER_1.put(4, QUESTION_MODEL_ID_18);
        SECTION_QUESTIONS_FOR_ID_2_VER_1.put(5, QUESTION_MODEL_ID_19);
        SECTION_QUESTIONS_FOR_ID_2_VER_1.put(6, QUESTION_MODEL_ID_20);
        SECTION_QUESTIONS_FOR_ID_2_VER_1.put(7, QUESTION_MODEL_ID_21);
        SECTION_QUESTIONS_FOR_ID_2_VER_1.put(8, QUESTION_MODEL_ID_22);

        SECTION_QUESTIONS_FOR_ID_3_VER_1 = new HashMap<>();
        SECTION_QUESTIONS_FOR_ID_3_VER_1.put(1, TestResources.QUESTION_MODEL_ID_23);
        SECTION_QUESTIONS_FOR_ID_3_VER_1.put(2, TestResources.QUESTION_MODEL_ID_24);
        SECTION_QUESTIONS_FOR_ID_3_VER_1.put(3, TestResources.QUESTION_MODEL_ID_25);

        SECTION_QUESTIONS_FOR_ID_4_VER_1 = new HashMap<>();
        SECTION_QUESTIONS_FOR_ID_4_VER_1.put(1, QUESTION_MODEL_ID_26);
        SECTION_QUESTIONS_FOR_ID_4_VER_1.put(2, QUESTION_MODEL_ID_27);

        SECTION_QUESTIONS_FOR_ID_5_VER_1 = new HashMap<>();
        SECTION_QUESTIONS_FOR_ID_5_VER_1.put(1, QUESTION_MODEL_ID_28);
        SECTION_QUESTIONS_FOR_ID_5_VER_1.put(2, QUESTION_MODEL_ID_29);

        SECTION_QUESTIONS_FOR_ID_6_VER_1 = new HashMap<>();
        SECTION_QUESTIONS_FOR_ID_6_VER_1.put(1, QUESTION_MODEL_ID_30);
        SECTION_QUESTIONS_FOR_ID_6_VER_1.put(2, QUESTION_MODEL_ID_31);

        /** SectionModel resources **/
        SECTION_MODEL_SAMPLE = new SectionModel();
        SECTION_MODEL_SAMPLE.setId(SECTION_SAMPLE.getId());
        SECTION_MODEL_SAMPLE.setVersionNumber(SECTION_VERSION_SAMPLE.getVersionNo());
        SECTION_MODEL_SAMPLE.setNoOfQuestionsToAnswer(SECTION_VERSION_SAMPLE.getNoOfQuestionsToAnswer());
        SECTION_MODEL_SAMPLE.setTimeScale(SECTION_VERSION_SAMPLE.getTimeScale());
        SECTION_MODEL_SAMPLE.setInstructionsText(SECTION_VERSION_SAMPLE.getDescription());
        SECTION_MODEL_SAMPLE.setQuestions(SECTION_QUESTIONS_FOR_ID_1_VER_1);
        SECTION_MODEL_SAMPLE.setReferenceName(SECTION_SAMPLE.getReferenceName());

        SECTION_MODEL_ID_1 = new SectionModel();
        SECTION_MODEL_ID_1.setId(1);
        SECTION_MODEL_ID_1.setVersionNumber(1);
        SECTION_MODEL_ID_1.setNoOfQuestionsToAnswer(SECTION_VERSION_ID_1_VERNO_1.getNoOfQuestionsToAnswer());
        SECTION_MODEL_ID_1.setTimeScale(SECTION_VERSION_ID_1_VERNO_1.getTimeScale());
        SECTION_MODEL_ID_1.setReferenceName(SECTION_REFERENCE_MODEL_ID_1.getReferenceName());
        SECTION_MODEL_ID_1.setQuestions(SECTION_QUESTIONS_FOR_ID_1_VER_1);

        SECTION_MODEL_ID_2 = new SectionModel();
        SECTION_MODEL_ID_2.setId(2);
        SECTION_MODEL_ID_2.setVersionNumber(1);
        SECTION_MODEL_ID_2.setReferenceName(SECTION_REFERENCE_MODEL_ID_2.getReferenceName());
        SECTION_MODEL_ID_2.setNoOfQuestionsToAnswer(8);
        SECTION_MODEL_ID_2.setTimeScale(22);
        SECTION_MODEL_ID_2.setQuestions(SECTION_QUESTIONS_FOR_ID_2_VER_1);

        SECTION_MODEL_ID_3 = new SectionModel();
        SECTION_MODEL_ID_3.setId(3);
        SECTION_MODEL_ID_3.setVersionNumber(1);
        SECTION_MODEL_ID_3.setReferenceName(SECTION_REFERENCE_MODEL_ID_3.getReferenceName());
        SECTION_MODEL_ID_3.setNoOfQuestionsToAnswer(2);
        SECTION_MODEL_ID_3.setTimeScale(10);
        SECTION_MODEL_ID_3.setQuestions(SECTION_QUESTIONS_FOR_ID_3_VER_1);


        SECTION_MODEL_ID_4 = new SectionModel();
        SECTION_MODEL_ID_4.setId(4);
        SECTION_MODEL_ID_4.setVersionNumber(1);
        SECTION_MODEL_ID_4.setReferenceName(SECTION_REFERENCE_MODEL_ID_4.getReferenceName());
        SECTION_MODEL_ID_4.setNoOfQuestionsToAnswer(1);
        SECTION_MODEL_ID_4.setTimeScale(10);
        SECTION_MODEL_ID_4.setQuestions(SECTION_QUESTIONS_FOR_ID_4_VER_1);

        SECTION_MODEL_ID_5 = new SectionModel();
        SECTION_MODEL_ID_5.setId(5);
        SECTION_MODEL_ID_5.setVersionNumber(1);
        SECTION_MODEL_ID_5.setReferenceName(SECTION_REFERENCE_MODEL_ID_5.getReferenceName());
        SECTION_MODEL_ID_5.setNoOfQuestionsToAnswer(1);
        SECTION_MODEL_ID_5.setTimeScale(8);
        SECTION_MODEL_ID_5.setQuestions(SECTION_QUESTIONS_FOR_ID_5_VER_1);

        SECTION_MODEL_ID_6 = new SectionModel();
        SECTION_MODEL_ID_6.setId(6);
        SECTION_MODEL_ID_6.setVersionNumber(1);
        SECTION_MODEL_ID_6.setReferenceName(SECTION_REFERENCE_MODEL_ID_6.getReferenceName());
        SECTION_MODEL_ID_6.setNoOfQuestionsToAnswer(1);
        SECTION_MODEL_ID_6.setTimeScale(10);
        SECTION_MODEL_ID_6.setQuestions(SECTION_QUESTIONS_FOR_ID_6_VER_1);

        /** Paper Sections **/
        PAPER_SECTIONS_FOR_PAPER_MODEL_ID_1 = new HashMap<>();
        PAPER_SECTIONS_FOR_PAPER_MODEL_ID_1.put(1, SECTION_MODEL_ID_1);
        PAPER_SECTIONS_FOR_PAPER_MODEL_ID_2 = new HashMap<>();
        PAPER_SECTIONS_FOR_PAPER_MODEL_ID_2.put(1, SECTION_MODEL_ID_2);
        PAPER_SECTIONS_FOR_PAPER_MODEL_ID_2.put(2, SECTION_MODEL_ID_3);
        PAPER_SECTIONS_FOR_PAPER_MODEL_ID_2.put(3, SECTION_MODEL_ID_4);
        PAPER_SECTIONS_FOR_PAPER_MODEL_ID_2.put(4, SECTION_MODEL_ID_5);
        PAPER_SECTIONS_FOR_PAPER_MODEL_ID_2.put(5, SECTION_MODEL_ID_6);

        /** Answer resources **/
        ANSWER_ID_1 = new Answer.Builder()
                .setQuesionVersionNo(1)
                .setQuesionId(1)
                .setTestDayEntryId(1)
                .setTestDayEntryId(1)
                .setText("Lorem ipsium dolor ...")
                .build();

        /** AnswerModel resources **/
        ANSWER_MODEL_ID_1 = new AnswerModel();
        ANSWER_MODEL_ID_1.setAssets(null);
        ANSWER_MODEL_ID_1.setText(ANSWER_ID_1.getText());

        /** AnswersMapModel resources **/
        ANSWERS_FOR_ATTEMPT_MODEL_ID_1 = new AnswersMapModel();
        ANSWERS_FOR_ATTEMPT_MODEL_ID_1.put(1, 1, ANSWER_MODEL_ID_1);

        ANSWERS_FOR_ATTEMPT_MODEL_ID_2 = new AnswersMapModel();
        /** SECTION 1 **/
        for (int i = 1; i < 9; i++) {
            ANSWERS_FOR_ATTEMPT_MODEL_ID_2.put(1, i, null);
        }
        /** SECTION 2 **/
        for (int i = 1; i < 4; i++) {
            ANSWERS_FOR_ATTEMPT_MODEL_ID_2.put(2, i, null);
        }
        /** SECTION 3 **/
        for (int i = 1; i < 3; i++) {
            ANSWERS_FOR_ATTEMPT_MODEL_ID_2.put(3, i, null);
        }
        /** SECTION 4 **/
        for (int i = 1; i < 3; i++) {
            ANSWERS_FOR_ATTEMPT_MODEL_ID_2.put(4, i, null);
        }
        /** SECTION 5 **/
        for (int i = 1; i < 3; i++) {
            ANSWERS_FOR_ATTEMPT_MODEL_ID_2.put(5, i, null);
        }

        /** CandidateModel resources **/
        CANDIDATE_MODEL_ID_1 = new CandidateModel();
        CANDIDATE_MODEL_ID_1.setId(1);
        CANDIDATE_MODEL_ID_1.setFirstName(CANDIDATE_ID_1.getName());
        CANDIDATE_MODEL_ID_1.setSurname(CANDIDATE_ID_1.getSurname());

        /** PaperReferenceModel resources **/
        PAPER_REFERENCE_MODEL_ID_1 = new PaperReferenceModel();
        PAPER_REFERENCE_MODEL_ID_1.setId(1);
        PAPER_REFERENCE_MODEL_ID_1.setVersionNo(1);
        PAPER_REFERENCE_MODEL_ID_1.setTimeAllowed(60);
        PAPER_REFERENCE_MODEL_ID_1.setReferenceName(PAPER_ID_1.getName());

        PAPER_REFERENCE_MODEL_ID_2 = new PaperReferenceModel();
        PAPER_REFERENCE_MODEL_ID_2.setId(2);
        PAPER_REFERENCE_MODEL_ID_2.setVersionNo(1);
        PAPER_REFERENCE_MODEL_ID_2.setReferenceName(PAPER_ID_2.getName());
        PAPER_REFERENCE_MODEL_ID_2.setTimeAllowed(60);

        /** PaperModel resources **/
        PAPER_MODEL_ID_1_VER_1 = new PaperModel();
        PAPER_MODEL_ID_1_VER_1.setId(1);
        PAPER_MODEL_ID_1_VER_1.setVersionNo(1);
        PAPER_MODEL_ID_1_VER_1.setInstructionsText(PAPER_VERSION_ID_1_VERNO_1.getInstructionsText());
        PAPER_MODEL_ID_1_VER_1.setReferenceName(PAPER_REFERENCE_MODEL_ID_1.getReferenceName());
        PAPER_MODEL_ID_1_VER_1.setSections(PAPER_SECTIONS_FOR_PAPER_MODEL_ID_1);
        PAPER_MODEL_ID_1_VER_1.setTimeAllowed(PAPER_REFERENCE_MODEL_ID_1.getTimeAllowed());

        PAPER_MODEL_ID_2_VER_1 = new PaperModel();
        PAPER_MODEL_ID_2_VER_1.setId(2);
        PAPER_MODEL_ID_2_VER_1.setInstructionsText(PAPER_VERSION_ID_2_VERNO_1.getInstructionsText());
        PAPER_MODEL_ID_2_VER_1.setReferenceName(PAPER_REFERENCE_MODEL_ID_2.getReferenceName());
        PAPER_MODEL_ID_2_VER_1.setVersionNo(1);
        PAPER_MODEL_ID_2_VER_1.setSections(PAPER_SECTIONS_FOR_PAPER_MODEL_ID_2);
        PAPER_MODEL_ID_2_VER_1.setTimeAllowed(PAPER_REFERENCE_MODEL_ID_2.getTimeAllowed());

        /** TestDayModel resources **/
        SAMPLE_DAY = new TestDayModel();
        SAMPLE_DAY.setLocation("Leeds Office");
        SAMPLE_DAY.setDate("2014/01/02");

        /** AttemptModel resources **/
        TEST_ATTEMPT_MODEL_ID_1 = new AttemptModel();
        TEST_ATTEMPT_MODEL_ID_1.setId(1);
        TEST_ATTEMPT_MODEL_ID_1.setTestDayModel(SAMPLE_DAY);
        TEST_ATTEMPT_MODEL_ID_1.setCandidate(CANDIDATE_MODEL_ID_1);
        TEST_ATTEMPT_MODEL_ID_1.setPaper(PAPER_MODEL_ID_1_VER_1);
        TEST_ATTEMPT_MODEL_ID_1.setAnswerMap(ANSWERS_FOR_ATTEMPT_MODEL_ID_1);
        TEST_ATTEMPT_MODEL_ID_1.setStatus(null);
        TEST_ATTEMPT_MODEL_ID_1.setTimeAllowed(60);
        TEST_ATTEMPT_MODEL_ID_1.setTermsandConditionsId(0);

        TEST_ATTEMPT_MODEL_ID_2 = new AttemptModel();
        TEST_ATTEMPT_MODEL_ID_2.setId(2);
        TEST_ATTEMPT_MODEL_ID_2.setTestDayModel(SAMPLE_DAY);
        TEST_ATTEMPT_MODEL_ID_2.setCandidate(CANDIDATE_MODEL_ID_1);
        TEST_ATTEMPT_MODEL_ID_2.setPaper(PAPER_MODEL_ID_2_VER_1);
        TEST_ATTEMPT_MODEL_ID_2.setAnswerMap(ANSWERS_FOR_ATTEMPT_MODEL_ID_2);
        TEST_ATTEMPT_MODEL_ID_2.setTimeAllowed(60);
        TEST_ATTEMPT_MODEL_ID_2.setTermsandConditionsId(0);

        /** AttemptReferenceModel resources **/
        TEST_ATTEMPT_REFERENCE_MODEL_ID_1 = new AttemptReferenceModel();
        TEST_ATTEMPT_REFERENCE_MODEL_ID_1.setId(1);
        TEST_ATTEMPT_REFERENCE_MODEL_ID_1.setCandidate(CANDIDATE_MODEL_ID_1);
        TEST_ATTEMPT_REFERENCE_MODEL_ID_1.setTestDayModel(SAMPLE_DAY);
        TEST_ATTEMPT_REFERENCE_MODEL_ID_1.setPaperRef(PAPER_REFERENCE_MODEL_ID_1);
        TEST_ATTEMPT_REFERENCE_MODEL_ID_1.setTimeAllowed(60);
        TEST_ATTEMPT_REFERENCE_MODEL_ID_1.setTermsandConditionsId(0);

        TEST_ATTEMPT_REFERENCE_MODEL_ID_2 = new AttemptReferenceModel();
        TEST_ATTEMPT_REFERENCE_MODEL_ID_2.setId(2);
        TEST_ATTEMPT_REFERENCE_MODEL_ID_2.setCandidate(CANDIDATE_MODEL_ID_1);
        TEST_ATTEMPT_REFERENCE_MODEL_ID_2.setTestDayModel(SAMPLE_DAY);
        TEST_ATTEMPT_REFERENCE_MODEL_ID_2.setPaperRef(PAPER_REFERENCE_MODEL_ID_2);
        TEST_ATTEMPT_REFERENCE_MODEL_ID_2.setTimeAllowed(60);
        TEST_ATTEMPT_REFERENCE_MODEL_ID_2.setTermsandConditionsId(0);

        /** Mark resources **/
        SAMPLE_MARK = new Mark.Builder()
                .setId(99)
                .setComment("Comment")
                .setMarkerId("Kopykta")
                .setActualMark(40)
                .build();

        /** MarkModel resources **/
        SAMPLE_MARK_MODEL = new MarkModel();
        SAMPLE_MARK_MODEL.setId(SAMPLE_MARK.getId());
        SAMPLE_MARK_MODEL.setComment(SAMPLE_MARK.getComment());
        SAMPLE_MARK_MODEL.setMark(SAMPLE_MARK.getActualMark());

    }

    private TestResources() {
        //Private constructor to stop users from instantiating this class
    }

    static List<AttemptReferenceModel> getAllAttemptReferenceModels() {
        return Arrays.asList(TestResources.TEST_ATTEMPT_REFERENCE_MODEL_ID_1, TestResources.TEST_ATTEMPT_REFERENCE_MODEL_ID_2);
    }
}
